package com.myCode.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myCode.util.PropertyUtil;
import com.myCode.util.StringUtil;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackLayout extends  PatternLayout {
	/** 要匹配的正则表达式（身份证/银行卡） */
	private Pattern patternChar;
	/** 要匹配的正则表达式（姓名） */
	private Pattern namepatternChar;
	private int keys_length_char;// 要匹配关键字的数目
	private String[] charKeys;
	private void init(){
		String str_char_keys = PropertyUtil.getStrValue("mycode.properties","log4j.char.keys", "");
		
		if(!StringUtil.isEmpty(str_char_keys)){//没有配置log4j.char.keys，不需要替换*
			charKeys = str_char_keys.split(",");// 读取配置中要加密的key
			
			// 组织正则表达式
			StringBuffer sbRegChar = new StringBuffer();
			sbRegChar.append("(");
			for (int i = 0; i < charKeys.length; i++) {
				sbRegChar.append("(");
				sbRegChar.append(charKeys[i]);
				sbRegChar.append(")");
				if (i < charKeys.length - 1) {
					sbRegChar.append("|");
				}
			}
			StringBuffer namesbRegChar = new StringBuffer(sbRegChar);
			namesbRegChar.append(")(=|:|：)([\u4e00-\u9fa5]{0,5})(;|,|\\||\\}|]|$)");
			//由于(\\s*\\w+={0,2})  key:value  中value中结尾包括==（base64导致，所以修改该写法）
			sbRegChar.append(")(=|:|：)(\\s*\\w+={0,2})(;|,|\\||\\}|]|$)");
			
			namepatternChar = Pattern.compile(namesbRegChar.toString());// 编译正则表达式
			patternChar = Pattern.compile(sbRegChar.toString());// 编译正则表达式
			keys_length_char = charKeys.length;
		}
		
	}
	public LogbackLayout(){
		super();
		init();
	}

	@Override
	public String doLayout(ILoggingEvent event) {
		String msg = super.doLayout(event);
		return dimChar(msg); //对日志关键信息模糊配置* 如果没配置返回正常信息
	}


//	public void doEncode(E event) throws IOException {
//	    String txt = super.layout.doLayout(event);
//	    txt=dimChar(txt);
//	    super.outputStream.write(convertToBytes(txt));
//	    if (super.isImmediateFlush())
//	    	super.outputStream.flush();
//	  }
//	
//	  private byte[] convertToBytes(String s) {
//		    if (super.getCharset() == null) {
//		      return s.getBytes();
//		    } else {
//		      try {
//		        return s.getBytes(super.getCharset().name());
//		      } catch (UnsupportedEncodingException e) {
//		        throw new IllegalStateException(
//		                "An existing charset cannot possibly be unsupported.");
//		      }
//		    }
//		  }
	
	/**
	 * 对银行卡以及身份证号脱敏处理
	 * 1.判断配置文件中是否已经配置需要脱敏字段
	 * 2.判断内容是否有需要脱敏的敏感信息
	 * 	2.1 没有需要脱敏信息直接返回
	 * 3.判断敏感信息内容是否需要处理
	 * 	3.1 处理银行卡和身份证敏感信息
	 * 	3.2 处理用户姓名敏感信息
	 * @param @param msg
	 * @param @return
	 * @return String 
	 * @throws
	 */
	private String dimChar(String msg) {
		try{
			//1.判断配置文件中是否已经配置需要脱敏字段
			if(keys_length_char <= 0 || patternChar == null) {
				return msg;
			}
			
			//2.判断内容是否有需要脱敏的敏感信息
			boolean flag=false;
			for (String key : charKeys) {
				if(msg.contains(key)){
					flag=true;
					break;
				}
			}
			
			//2.1 没有需要脱敏信息直接返回
			if(!flag) {
				return msg;
			}
			
			Matcher match = patternChar.matcher(msg);
			boolean nameFlag = false;
			// 处理要打印的日志信息
			//3.判断敏感信息内容是否需要处理
			StringBuffer sbMsg = new StringBuffer();
			while (match.find()) {
				//3.1 处理银行卡和身份证敏感信息
				nameFlag = true;
				// group(1)匹配key，group(keys_length+2)匹配(=|:|：)，group(keys_length+3)匹配value，group(keys_length+4)匹配(;|,|\\||]|\\)|$)
				match.appendReplacement(
						sbMsg,
						match.group(1)
								+ match.group(keys_length_char + 2)
								+ replaceChar(match.group(keys_length_char + 3))
								+ match.group(keys_length_char + 4));
			}
			
			//3.2 处理用户姓名敏感信息
			if(!nameFlag) {
				match = namepatternChar.matcher(msg);
				while (match.find()) {
					nameFlag = true;
					// group(1)匹配key，group(keys_length+2)匹配(=|:|：)，group(keys_length+3)匹配value，group(keys_length+4)匹配(;|,|\\||]|\\)|$)
					match.appendReplacement(
							sbMsg,
							match.group(1)
									+ match.group(keys_length_char + 2)
									+ replaceNameChar(match.group(keys_length_char + 3))
									+ match.group(keys_length_char + 4));
				}
			}
			match.appendTail(sbMsg);// 增加最后一个匹配项后面的值
			return sbMsg.toString();
		}catch (Exception e) {
			//如果跑出异常为了不影响流程，直接返回原信息
			return msg;
		}
	}
	
	
	/**
	 * 日志关键字用* 替换
	 * 规则：  value <= 6     替换为******
	 *        6 > value <= 12  后四位保留，其他为*
	 *        value > 12      前6位保留  中间*  后4位保留    
	 * @param value
	 * @return 
	 */
	private static String replaceChar(String value){

		if(StringUtil.isEmpty(value) || "null".equals(value)){
			return "";
		}
		if(value.length() <= 6){ // value <= 6   替换为******
			return lStr("",'*',value.length());
		}else if(value.length() > 6 && value.length() <= 12){ //后四位保留，其他为*
			int valLen = value.length();
    		String replaceHeadStr = value.substring(value.length() -4);
    		return replaceHeadStr = lStr(replaceHeadStr,'*',valLen);
		}else if(value.length() > 12){ //前6位保留  中间*  后4位保留 
	    	String replaceHeadStr = value.substring(0,6);
	    	String replaceTailStr = value.substring(value.length()-4);
	    	String dimStr = lStr("",'*',value.length()-10);
	    	return replaceHeadStr+dimStr+ replaceTailStr;
		}else{
			return value;
		}
	}
	
	/**
	 * 日志姓名关键字替换
	 * 保留第一个汉字，后边用*号表示（例如：张三--》张*）
	 * @param @param value
	 * @param @return
	 * @return String 
	 * @throws
	 */
	private static String replaceNameChar(String value){

		if(StringUtil.isEmpty(value) || "null".equals(value)){
			return "";
		}
		String replaceHeadStr = value.substring(0,1);
		return replaceHeadStr + lStr("",'*',value.length() - 1);
	}
	
	/*
	 * 左补长char
	 */
    public static String lStr(String s, char ch, int width) {
        if (s.length() < width) { // 需要前面补'0'
            while (s.length() < width)
                s = ch + s;
        } else { // 需要将高位丢弃
            s = s.substring(s.length() - width, s.length());
        }
        return s;
    }
}

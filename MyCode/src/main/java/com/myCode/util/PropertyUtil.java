package com.myCode.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;




public class PropertyUtil {
	
	
	// 静态对象初始化[在其它对象之前]
	private static Hashtable<String, Object> register = new Hashtable<String, Object>();
	
	private static Hashtable<String, Object> reverseRegister = new Hashtable<String, Object>();

	//不能实例化
	private PropertyUtil() {
		super();
	}

	/**
	 * 读取配置文件
	 * 
	 * @param java.lang.String
	 *            fileName 文件名
	 * @return Properties 
	 */
	public static Properties getProperties(String fileName) {// 传递配置文件路径
		InputStream is = null;// 定义输入流is
		Properties p = null;
		try {
			p = (Properties) register.get(fileName);// 将fileName存于一个HashTable
			/**
			 * 如果为空就尝试输入进文件
			 */
			if (p == null) {
				fileName = StringUtil.trim(fileName);
				File f = new File(fileName);
				if (f.exists()) {
					//fileName为绝对路径
					System.out.println("通过绝对路径获取配置文件：" + fileName);
					is = new FileInputStream(f);
				} else {
					//在resin的conf目录下查找该文件
					String  resinHome = System.getProperty("resin.home");
					String realPath = null;//文件的绝对路径
					if (!StringUtils.startsWith(fileName, File.separator)) {
						realPath = File.separator + fileName;
					}
					realPath = resinHome + File.separator + "conf/spEnterprise" + realPath;
				    f = new File(realPath);
					if (f.exists()) {
						System.out.println("outsite配置文件的绝对路径：" + realPath);
						is = new FileInputStream(f);
					} else {
//						try {
//							is = new FileInputStream(fileName);// 创建输入流
//						} catch (Exception e) {
//							System.out.println("通过getResourceAsStream获取配置文件：" + fileName);
//							if (fileName.startsWith("/"))
//								// 用getResourceAsStream()方法用于定位并打开外部文件。
//								is = PropertyUtil.class
//										.getResourceAsStream(fileName);
//							else
//								is = PropertyUtil.class.getResourceAsStream("/"
//										+ fileName);
//						}
						// 用getResourceAsStream()方法用于定位并打开外部文件。
						String temStr = fileName;
						if (!fileName.startsWith(File.separator)) {
							temStr = "/" + temStr;
						}
						System.out.println("通过getResourceAsStream获取配置文件：" + temStr);
						is = PropertyUtil.class.getResourceAsStream(temStr);
					}
				}
				
				p = new Properties();
				p.load(is);// 加载输入流
				register.put(fileName, p);// 将其存放于HashTable缓存
				is.close();// 关闭输入流
			}
		} catch (Exception e) {
			System.err.println(String.format("读取配置文件%s异常：", fileName));
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					System.err.println("加载配置文件" + fileName + "关闭输入流异常");
					e.printStackTrace();
				}
				is = null;
			}
		}
		return p;// 返回Properties对象
	}
	
	
	/**
	 * 读取配置文件
	 * 
	 * @param java.lang.String
	 *            fileName 文件名
	 * @return Properties 
	 */
	public static Properties getReverseProperties(String fileName) {// 传递配置文件路径
		InputStream is = null;// 定义输入流is
		Properties p = null;
		try {
			p = (Properties) reverseRegister.get(fileName);// 将fileName存于一个HashTable
			/**
			 * 如果为空就尝试输入进文件
			 */
			if (p == null) {
				fileName = StringUtil.trim(fileName);
				File f = new File(fileName);
				if (f.exists()) {
					//fileName为绝对路径
					System.out.println("通过绝对路径获取配置文件：" + fileName);
					is = new FileInputStream(f);
					System.out.println();
				} else {
					String temStr = fileName;
					if (!fileName.startsWith(File.separator)) {
						temStr = "/" + temStr;
					}
					System.out.println("通过getResourceAsStream获取配置文件：" + temStr);
					is = PropertyUtil.class.getResourceAsStream(temStr);
				}
				
				p = new Properties();
				p.load(is);// 加载输入流
				
				Properties reverseP = new Properties();
				for(java.util.Iterator<Map.Entry<Object, Object>> iter = p.entrySet().iterator(); iter.hasNext(); ) {
					Map.Entry<Object, Object> entry = iter.next();
					reverseP.put( entry.getKey(),entry.getValue());
				}
				p = reverseP;
				reverseRegister.put(fileName, reverseP);// 将其存放于HashTable缓存
				is.close();// 关闭输入流
			}
		} catch (Exception e) {
			System.err.println(String.format("读取配置文件%s异常：", fileName));
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					System.err.println("加载配置文件" + fileName + "关闭输入流异常");
					e.printStackTrace();
				}
				is = null;
			}
		}
		return p;// 返回Properties对象
	}
	
	/**
	 * <p>读取配置文件中的字串属性</p>
	 * @author Ding Zhe, Aug 12, 2013 11:25:59 AM
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @return
	 */
	public static String getStrValue(String fileName, String strKey) {
		return getStrValue(fileName, strKey, "");
	}
	
	/**
	 * <p>反转读取配置文件中的字串属性</p>
	 * @author lizhaoyang
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @return
	 */
	public static String getReverseStrValue(String fileName, String strKey) {
		return getReverseStrValue(fileName, strKey, "");
	}
	
	/**
	 * 反转读取配置文件Map
	 * @param fileName
	 * @return
	 */
	public static Map<String, String> getReverseMap(String fileName) {
		Map<String, String> retVal = new HashMap<String, String>();
		for(java.util.Iterator<Map.Entry<Object, Object>> iter = getReverseProperties(fileName).entrySet().iterator(); iter.hasNext();) {
			Map.Entry<Object, Object> entry = iter.next();
			retVal.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return retVal;
	}
	
	/**
	 * 读取配置文件中的字串属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static String getStrValue(String fileName, String strKey, String defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return StringUtil.trim(p.getProperty(strKey,defaultValue));
		} catch (Exception e) {
			System.err.println("读取配置文件中的字串属性异常，返回默认值：");
			e.printStackTrace();
			return defaultValue;
		}		
	}
	
	/**
	 * 反转读取配置文件中的字串属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static String getReverseStrValue(String fileName, String strKey, String defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return p.getProperty(strKey,defaultValue);
		} catch (Exception e) {
			System.err.println("读取配置文件中的字串属性异常，返回默认值：");
			e.printStackTrace();
			return defaultValue;
		}
	}
	
	public static String getStrValue(String path,String fileName, String strKey, String defaultValue) {
		path = StringUtil.trim(path);
		String keys = PropertyUtil.getStrValue(path + File.separator + fileName,strKey, defaultValue);
		String value = StringUtil.trim(keys);
		return value;
	}
	
	public static String getStrValueRealTime(String fileName, String strKey, String defaultValue) {
		InputStream is = null;// 定义输入流is
		Properties p = null;
		String  resinHome = System.getProperty("resin.home");
		if (StringUtil.isEmpty(fileName)) {
			return null;
		}
		fileName = StringUtil.trim(fileName);
		if (!StringUtils.startsWith(fileName, File.separator)) {
			fileName = File.separator + fileName;
		}
		fileName = resinHome + File.separator + "conf" + fileName;
		try {
			try {
				is = new FileInputStream(fileName);// 创建输入流
			} catch (Exception e) {
				if (fileName.startsWith("/"))
					// 用getResourceAsStream()方法用于定位并打开外部文件。
					is = PropertyUtil.class
							.getResourceAsStream(fileName);
				else
					is = PropertyUtil.class.getResourceAsStream("/"
							+ fileName);
			}
			p = new Properties();
			p.load(is);// 加载输入流
			is.close();// 关闭输入流
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return p.getProperty(strKey);// 返回Properties对象
	}
	/**
	 * desc：从配置文件中获取一个集合组
	 * <p>创建人：linfangjian , 2012-9-14 下午03:35:13</p>
	 * @param fileName
	 * @param proName
	 * 私有方法改为共有方法
	 */
	public static Map<String, String> getStrMap(String fileName, String strKey){
		Map<String, String> payTypeMap = new LinkedHashMap<String, String>();
		String paytype = getStrValue(fileName, strKey, "");
		if("".equals(paytype)){
			//System.out.println("在配置文件"+fileName+"中未找到该配置");
			return payTypeMap;
		}
		try {
			String [] paytypeArray = paytype.split(",");
			for(int i=0;i<paytypeArray.length;i++){
				if(!"".equals(paytypeArray[i])){
					String value = getStrValue(fileName, strKey+"."+paytypeArray[i], "");
					if(!"".endsWith(value)){
						//System.out.println("key="+paytypeArray[i]+",value="+value);
						payTypeMap.put(paytypeArray[i], value);
					}else{
						//System.out.println("找不到对应的key值"+paytypeArray[i]);
					}
				}
			}
		} catch (Exception e) {
			//System.out.println("解析配置文件出错！"+e);
			return payTypeMap;
		}
		return payTypeMap;
	}
	/**
	 * 读取配置文件中的Integer属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static Integer getIntValue(String fileName, String strKey, Integer defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return  Integer.valueOf(p.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}
	}
	/**
	 * <br>description :读取配置文件中的Integer属性
	 * @param path 路径
	 * @param fileName 文件名
	 * @param strKey 配置文件
	 * @param defaultValue 缺省值
	 * @return value值
	 * @author      王世院
	 * @version     1.0
	 * @date        May 24, 20121:19:55 PM
	 */ 
	public static Integer getIntValue(String path,String fileName, String strKey, Integer defaultValue) {
		path = StringUtil.trim(path);
		int value = PropertyUtil.getIntValue(path + File.separator + fileName,strKey, defaultValue);
		return value;
	}
	/**
	 * 
	 * @param fileName
	 * @param strKey
	 * @param defaultValue
	 * @return
	 */
	public static Long getLongValue(String fileName, String strKey, Long defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return  Long.valueOf((String) p.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}
	}
	/**
     * desc:获得配置文件所配置字段最大长度
     * <p>创建人：chengliuyun , Jun 7, 2012 2:38:44 PM</p>
     * @param key
     * @return
     */
    public static int getMaxLengthValue(String key){
    	String maxStr= getRegexValueStr("batchpay."+key);//StringUtil.trim(PropertyUtil.getStrValue("spEnterpriseWeb_config.properties","batchpay."+key, ""));
    	return "".equals(maxStr)?0:Integer.parseInt(maxStr);
    }
    /**
     * desc:mapping_keys.properties 传入键，获取键值
     * <p>创建人：chengliuyun , Jun 5, 2012 11:52:25 AM</p>
     * @param key
     * @return
     */
    public static String  getKeysValueStr(String key){//"PARAM." + Constants.BATCHPAY
    	String keyValueStr = PropertyUtil.getStrValue("resources"+ File.separator+"checkParam","mapping_keys.properties", key, "");
    	return keyValueStr;
    }
    /**
     * desc:mapping_regex.properties 传入键，获取键值
     * <p>创建人：chengliuyun , Jun 5, 2012 11:52:27 AM</p>
     * @param key
     * @return
     */
    public static String  getRegexValueStr(String key){
    	String keyValueStr = PropertyUtil.getStrValue("resources"+ File.separator+"checkParam","mapping_regex.properties", key, "");
    	return keyValueStr;
    }
    /**
     * desc:mapping_regex.properties 传入特定键，获取键含有Regex的键值
     * <p>创建人：chengliuyun , Jun 5, 2012 11:52:27 AM</p>
     * @param key
     * @return
     */
    public static String  getRegexValue(String key){
    	String keyValueStr = PropertyUtil.getStrValue("resources"+ File.separator+"checkParam","mapping_regex.properties", "Regex."+key, "");
    	return keyValueStr;
    }
    /**
     * desc:mapping_out.properties 传入键，获取键值
     * <p>创建人：chengliuyun , Jun 5, 2012 11:52:30 AM</p>
     * @param key
     * @return
     */
    public static String  getOutValueStr(String key){
    	String keyValueStr = PropertyUtil.getStrValue("resources"+ File.separator+"checkParam","mapping_out.properties", key, "");
    	return keyValueStr;
    }
    /**
     * desc:mapping_out.properties 传入键，获取键值
     * <p>创建人：chengliuyun , Jun 5, 2012 11:52:30 AM</p>
     * @param key
     * @return
     */
    public static String  getOutRegexStr(String key){
    	String keyValueStr = PropertyUtil.getStrValue("resources"+ File.separator+"checkParam","mapping_out.properties", "check."+key, "");
    	return keyValueStr;
    }
    /**
    /**
     * 获取mapping_keys配置文件中code所对应的值。以supAccType为例
     */
    public static Map<String, String> getKeyValueStringMap(String code) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String values = getKeysValueStr(code);
		if (values.indexOf(",") > -1) {
			String[] argsValue = values.split(",");
			for (String str : argsValue) {
				String strValue = getKeysValueStr(code + "."
						+ StringUtil.trim(str));
				map.put(str, strValue);
			}
		} else {
			String strValue = getKeysValueStr(code + "." + values);
			map.put(values, strValue);//map.put("01","类型1")
		}
		return map;
	}
}

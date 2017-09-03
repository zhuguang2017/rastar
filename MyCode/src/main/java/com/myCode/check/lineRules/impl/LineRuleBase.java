package com.myCode.check.lineRules.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;

import com.myCode.Override.OverRandomAccessFile;
import com.myCode.check.lineRules.LineRule;
import com.myCode.check.log.LoggerManager;
import com.myCode.check.model.AllLineCount;
import com.myCode.check.model.LineCount;
import com.myCode.check.model.RulesModel;
import com.myCode.check.vector.MapKeyComparator;
import com.myCode.check.vector.ModelMapBean;
import com.myCode.util.StringUtil;

public abstract class LineRuleBase implements LineRule {
	private RulesModel rule;
	private LineCount  lineCount;
	private AllLineCount  allLineCount;
	private String fileName;//当前文件名
	private Logger checklogger=LoggerManager.getChecklogger();
	
	abstract void prepare();
	
	public void init()  {
		lineCount = new LineCount();//行数统计
		allLineCount = AllLineCount.getInstance();//行数统计
		prepare();
	}
	
	public void worker(File file){
		 init();
		fileName=file.getName();
		OverRandomAccessFile randomFile;
		
		
		
		String lineString="";
		try {
			FileInputStream input=new FileInputStream(file);
			InputStreamReader inputsr=new InputStreamReader(input,Charset.forName("UTF8"));
			BufferedReader br=new BufferedReader(inputsr);
			
//			checklogger.info("-----【"+fileName+"】----"+rule.getRuleNote()+"----start");
			randomFile = new OverRandomAccessFile(file,"rw");
//			while (randomFile.getFilePointer()<randomFile.length()) {
			while ((lineString=br.readLine())!=null) {
				//行计数
				lineCount.addCount();
//				String lineString=randomFile.readLine();
//				String lineString=randomFile.newReadLine();
//				String lineString=br.readLine();
				if(doRuleJudic(lineString,rule)){
//					allLineCount.addCount();
					String[] ss=lineString.split(" ");
					String tip=StringUtil.trim((ss.length>2?ss[2]:""));
					String res=StringUtil.trim(tip.split(";")[0]);
//					checklogger.info("["+allLineCount.getCount()+"]"+ss[2]);
//					checklogger.info("["+allLineCount.getCount()+"]"+(ss.length>2?ss[2]:""));
//					checklogger.info(" 【"+allLineCount.getCount()+"】 "+lineString);
					ModelMapBean.INSTANCE.putValue(res, StringUtil.trim(lineString));
				}else{
					continue;
				}
			}
//			checklogger.info("-----【"+fileName+"】----"+rule.getRuleNote()+"----end\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException ee){
			System.out.println("[]"+lineString.split(" ").length+"[]"+lineString);
		}
		
		
		return ;
		
	}
	
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
    
	public RulesModel getRule() {
		return rule;
	}
	public void setRule(RulesModel rule) {
		this.rule = rule;
	}

}

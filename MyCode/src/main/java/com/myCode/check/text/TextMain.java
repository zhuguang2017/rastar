package com.myCode.check.text;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myCode.check.ergodic.ErgodicFile;
import com.myCode.check.model.AllLineCount;
import com.myCode.check.vector.MapKeyComparator;
import com.myCode.check.vector.ModelMapBean;
import com.myCode.util.StringUtil;

public class TextMain {
	private static final Logger checkLogger = LoggerFactory.getLogger("checkLogger");
	public static void main(String[] args) {
		String path="E:\\worlspace\\MyWorkspace1\\OperationPlat_BSX00001958\\OperationPlatIF\\src\\main\\java\\com\\umpay\\operation\\model";
		String sperpath="E:\\worlspace\\MyWorkspace1\\spEnterpriseNew_KSX00001959\\spEnterpriseIF\\src\\main\\java\\com\\umpay\\sp\\model";
		search(path);
		search(sperpath);
		
		
		AllLineCount allLineCount = AllLineCount.getInstance();//行数统计
		
		for (String key :ModelMapBean.INSTANCE.getSortKeyMap().keySet()) {
			allLineCount.addCount();
//			checklogger.info(" 【"+allLineCount.getCount()+"】 "+key+"   "+ModelMapBean.INSTANCE.getValue(key));
			int len=StringUtil.trim(key).length();
			String tip="";
			if(len>0&&len<8){
				tip="\t\t\t";
			}else if(len>=8&&len<16){
				tip="\t\t";
			}else if(len>=16&&len<24){
				tip="\t";
			}
			checkLogger.info(" 【"+allLineCount.getCount()+"】"+(allLineCount.getCount()<100?"\t\t":"\t")+key+tip+ModelMapBean.INSTANCE.getSortKeyMap().get(key));
//			System.err.println(" 【"+allLineCount.getCount()+"】"+(allLineCount.getCount()<100?"\t\t":"\t")+key);
		}
		System.err.println("end");
	}

	public static void search(String path) {
		ErgodicFile ergodicFile=new ErgodicFile();
		ergodicFile.traverseFolder2(path);
		
		
		
		
	}
}

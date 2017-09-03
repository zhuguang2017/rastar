package com.myCode.check.worker;

import java.io.File;

import com.myCode.check.lineRules.LineRule;
import com.myCode.check.lineRules.impl.LineRuleBase;
import com.myCode.check.lineRules.impl.SearchModelImpl;

public class SearchModelExecutor extends ExecutorBase {
	private static SearchModelExecutor instance ;
	private SearchModelExecutor (){}
	static {
	    instance = new SearchModelExecutor();
	}
	public static SearchModelExecutor getInstance() {
    	return instance;
    }
	
	public void dowork(File file) {
		SearchModelImpl search=new SearchModelImpl();
		super.work(search, file);
	}
	
	
}

package com.myCode.check.lineRules;

import java.io.File;

import com.myCode.check.model.RulesModel;

public interface LineRule {
	
	abstract public boolean doRuleJudic(String lineStr,RulesModel rule);
	
	abstract public void doWorker(File file);
	
	
}

package com.myCode.check.lineRules.impl;

import java.io.File;

import com.myCode.check.model.RulesModel;
import com.myCode.util.StringUtil;

public class SearchModelImpl extends LineRuleBase {

	@Override
	public boolean doRuleJudic(String lineStr, RulesModel rule) {
		if(lineStr.contains("private")
				&&(!lineStr.contains("("))
				&&(StringUtil.isNotEmpty(lineStr.split("//")[0]))
				&&(!lineStr.contains("serialVersionUID"))){
			return true;
		}
		return false;
	}

	@Override
	void prepare() {
		super.setRule(new RulesModel("SearchMode","筛选所有model属性值"));
	}

	@Override
	public void doWorker(File file) {
		// TODO Auto-generated method stub
		super.worker(file);
	}

}

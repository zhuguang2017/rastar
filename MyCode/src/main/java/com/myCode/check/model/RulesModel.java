package com.myCode.check.model;

public class RulesModel {
	private  String rule;//规则
	private  String ruleNote ;//规则描述
	
	
	public RulesModel(String rule,String ruleNote){
		this.rule=rule;
		this.ruleNote=ruleNote;
	}
	public String getRuleNote() {
		return ruleNote;
	}
	public void setRuleNote(String ruleNote) {
		this.ruleNote = ruleNote;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
}

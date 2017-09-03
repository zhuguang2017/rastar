package com.myCode.check.model;


public class AllLineCount  {
	private static AllLineCount instance = null;
	private  int count = 0;//计数变量
	
	static {
    	instance = new AllLineCount();
    }
	private AllLineCount(){}
	public static AllLineCount getInstance() {
	    	return instance;
	}
	public  void addCount() {
		count=count+1;
	}
	public  void subCount() {
		count=count-1;
	}
	
	public  int getCount() {
		return count;
	}
	public  void setCount(int count) {
		this.count = count;
	}
	
	
}

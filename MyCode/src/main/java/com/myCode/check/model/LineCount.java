package com.myCode.check.model;


public class LineCount {
	private  int count = 0;//计数变量
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

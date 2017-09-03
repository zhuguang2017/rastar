package com.myCode.check.worker;

import java.io.File;

import com.myCode.check.lineRules.impl.LineRuleBase;

public  class ExecutorBase {
	 public void work(LineRuleBase worker,File file ){
		 worker.doWorker(file);
	 }
}

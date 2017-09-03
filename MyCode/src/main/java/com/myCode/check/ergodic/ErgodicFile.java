package com.myCode.check.ergodic;

import java.io.File;

import com.myCode.check.worker.SearchModelExecutor;


public class ErgodicFile {
	
	
	private SearchModelExecutor Executor;
	
	public ErgodicFile() {
		Executor=SearchModelExecutor.getInstance();
	}
	
	
	public void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
//                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
//                    	System.err.println(file2.getName());
                    	Executor.dowork(file2);
                    }
                }
            }
        } else {
//            System.out.println("文件不存在!");
        }
    }
}

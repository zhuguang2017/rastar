package com.myCode.log;

import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;

import com.myCode.check.log.LoggerManager;

public class LogLayout {
	
	private static final Logger logger = LoggerFactory.getLogger(LogLayout.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger sslog=LoggerManager.getDetaillogger();
		Logger mpsplog=LoggerManager.getSearchlogger();
		System.err.println("sss");
		logger.info("identityCode=11111222223333344444");
		sslog.info("wwwww");
		mpsplog.info("eeee");
	}

}

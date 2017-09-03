package com.myCode.check.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myCode.log.LogLayout;

/**         
 * @author： 
 * @description： 日志管理    
 * @version    
 */
public class LoggerManager {
	/**简要日志*/
	private static final Logger SearchLogger = LoggerFactory.getLogger("mpspLogger");
	/**详细日志**/
	private static final Logger detaillogger = LoggerFactory.getLogger("ssLogger");
	/**校验日志**/
	private static final Logger checkLogger = LoggerFactory.getLogger("checkLogger");
	
	public static Logger getSearchlogger() {
		return SearchLogger;
	}
	public static Logger getDetaillogger() {
		return detaillogger;
	}
	public static Logger getChecklogger() {
		return checkLogger;
	}

	
	
}

package com.myCode.check.vector;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum ModelMapBean {
	INSTANCE;								//单例模式设计，避免多线程同步以及防止反序列化重新创建新的对象。
	private Map<String,String> propMap;		//存放配置key-value的map容器
	/**
	 * 构造方法，用以初始化时候读取配置文件中参数信息
	 */
	ModelMapBean() {   
//		System.setProperty("resin.home", "D:\\devSoft\\resin\\resin-4.0.48");				//自测时候需要手动设置resin根路径，用以读取配置分离的文件
		propMap=new HashMap<String,String>();
		
	}  
	/**
	 * 获取配置文件中配置值的方法
	 * @param key
	 * @return
	 */
	 public String getValue(String key) {
		 return null==propMap?"":propMap.get(key);
	 }
	 /**
	 * 获取配置文件中配置值的方法
	 * @param key
	 * @return
	 */
	 public void putValue(String key,String value) {
//		 if(propMap.get(key)!=null){
		 propMap.put(key, value);
//		 }
	 }
	 
	 /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
    
    public Map<String, String> getSortKeyMap() {
    	Map<String, String> resultMap = sortMapByKey(ModelMapBean.INSTANCE.getPropMap());  //按Key进行排序
		return resultMap;
	}
    
    public Map<String, String> getPropMap() {
    	return propMap;
    }
}

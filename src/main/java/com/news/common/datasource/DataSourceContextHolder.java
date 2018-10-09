package com.news.common.datasource;

public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();      
    
    public static void setDBType(DataSourceType dbType) {
        contextHolder.set(dbType.toString());      
    }      
  
    public static String getDBType() {      
        return ((String) contextHolder.get());      
    }      
  
    public static void clearDBType() {      
        contextHolder.remove();      
    }
}

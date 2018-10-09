package com.news.common.base;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 定时任务基础类
 * @Description:
 * @author wanghz
 * @date 2016年5月24日
 */
public class BaseQuartzJob {
	
	protected WebApplicationContext webContext;
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public BaseQuartzJob() {
		
	}
	
	/**
	 * 实例化WebApplicationContext
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月25日
	 * @return
	 */
	protected WebApplicationContext getWebContext() {
		if(webContext==null){
			webContext = ContextLoader.getCurrentWebApplicationContext();
		}
		return webContext;
	}
	
	/**
	 * 获取Bean实例
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月25日
	 * @param requiredType
	 * @return 
	 * @return
	 */
	protected <T> T getBean(Class<T> requiredType){
		webContext = getWebContext();
		return webContext.getBean(requiredType);
	}
	
	protected Object getBean(String bean){
		webContext = getWebContext();
		return webContext.getBean(bean);
	}
}

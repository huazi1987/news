package com.news.common.taglib.base;

import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


/**
 * 自定义标签基础类
 * 封装了公用的方法
 * @author whz
 *
 */
public class BaseTaglib extends TagSupport{
	
	protected WebApplicationContext webContext;
	protected Logger log = LoggerFactory.getLogger(getClass());
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseTaglib() {
		webContext = ContextLoader.getCurrentWebApplicationContext();
	}
	
	protected Object evaluate(String attrName,Object obj){
//		try {
//			return ExpressionEvaluatorManager.evaluate(attrName, obj.toString(), Object.class, this, pageContext);
//		} catch (JspException e) {
//			Log.error("初始化标签数据出错:"+e.getLocalizedMessage());
//		}
		return null;
	}
}

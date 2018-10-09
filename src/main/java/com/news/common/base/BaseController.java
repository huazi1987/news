package com.news.common.base;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.news.common.json.JacksonMapper;
import com.news.common.util.CommonStatic;
import com.news.common.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;


/**
 * 基础Controller类，要求所有Controller继承此类
 * @Description:
 * @author wanghz
 * @date 2015年1月9日
 */
public class BaseController {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected JacksonMapper jacksonMapper;
	@Autowired
	protected MessageSource messageSource;
	
	protected int rows = 15;	//默认每页15条数据
	
	/**
	 * 获得起始行数
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-5-4
	 * @return
	 */
	protected int startRow(int page,int rows) {
		return (page - 1) * rows;
	}
	
	/**
	 * 获得总页数
	 * @Description: 
	 * @author wanghz
	 * @date 2016年3月14日
	 * @param total
	 * @param rows
	 * @return
	 */
	protected int totalPage(int total,int rows){
		return (total  +  rows  - 1) / rows;
	}
	
	/**
	 * 获得真实工程地址
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param request
	 * @return
	 */
	protected String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 获得当前国际化语言
	 * @Description: 
	 * @author wanghz
	 * @date 2017年12月19日
	 * @param request
	 * @return
	 */
	protected static Locale getLocale(HttpServletRequest request)
	{
		Cookie cookie = CookieUtil.getCookieByName(request, CommonStatic.W_LANG);
		if(cookie!=null) {
			String[] lang = cookie.getValue().split("_");
			if(lang.length==2) {
				return new Locale(lang[0],lang[1]); 
			}
		}
		return request.getLocale();
	}
}

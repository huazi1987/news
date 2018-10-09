package com.news.common.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.common.util.CookieUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LocaleChangeInterceptor implements HandlerInterceptor {
	
	private final static String W_LANG = "W_LANG";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		Cookie cookie = CookieUtil.getCookieByName(request, W_LANG);
		System.out.println("Cookie:"+cookie);
		if(cookie!=null) {
			System.out.println("val:"+cookie.getValue());
		}
		return true;
	}
}

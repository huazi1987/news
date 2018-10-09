package com.news.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.news.common.util.LoginUtil;

public class SessionFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			LoginUtil.setSession(((HttpServletRequest)request).getSession(true));//往ThreadLocal放置session信息
			chain.doFilter(request,response);
		}catch (Exception e) {
			LoginUtil.clearSession();//清空ThreadLocal中session;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}

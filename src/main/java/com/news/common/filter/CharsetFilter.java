package com.news.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class CharsetFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse srsp,
			FilterChain chain) throws IOException, ServletException {
		srsp.setCharacterEncoding("UTF-8");
		sreq.setAttribute("rootPath", this.getRootPath(sreq));
		chain.doFilter(sreq, srsp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private StringBuffer getRootPath(ServletRequest req) {
		HttpServletRequest request=(HttpServletRequest)req;
		StringBuffer rootPath=new StringBuffer();
		if(request!=null){
			rootPath.append(request.getContextPath());
		}
		return rootPath;
	}
}
package com.news.common.util;

import javax.servlet.http.HttpSession;

import com.news.cms.sys.user.model.SysUser;

public class LoginUtil {
	
	private static ThreadLocal<HttpSession> sessions = new ThreadLocal<HttpSession>();//存放session
	private static final String USER_KEY = "CT_USER";//session属性：存入session的用户信息
	
	/**
	 * 设置session到ThreadLocal中
	 * @param session
	 */
	public static void setSession(HttpSession session){
//		session.setMaxInactiveInterval(10);//session有效期为10秒
		sessions.set(session);
	}

	/**
	 * 清空ThreadLocal中的session
	 */
	public static void clearSession(){
		sessions.set(null);
	}
	
	/**
	 * 获取当前登录用户编号
	 * @return
	 */
	public static String getCurrentUserId(){
		SysUser sysUser = getCurrentUser();
		if(sysUser == null){
			return null;
		}
		return sysUser.getUserId();
	}
	
	public static String getCurrentUserLogin(){
		SysUser sysUser = getCurrentUser();
		if(sysUser == null){
			return null;
		}
		return sysUser.getUserLogin();
	}
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static SysUser getCurrentUser(){
		HttpSession session = sessions.get();
		if(session == null){
			return null;
		} 
		return (SysUser) session.getAttribute(USER_KEY);
	}
	
	/**
	 * 将当前用户传入session
	 * @Description: 
	 * @param obj
	 */
	public static void setCurrentUser(Object obj){
		HttpSession session = sessions.get();
		if(session == null){
			return;
		}
		session.setAttribute(USER_KEY, obj);
	}
}

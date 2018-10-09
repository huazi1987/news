package com.news.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 新增Cookie
	 * @param response
	 * @param name
	 * @param value
	 * @author whz
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge)
	{
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge*24*60*60);
		response.addCookie(cookie);
	}
	
	/**
	 * 新增Cookie，有效期为7天
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月10日
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response,String name,String value)
	{
		addCookie(response, name, value, 7);
	}
	
	/**
     * 去除Cookie
     * @param request
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name){
    	Cookie[] cookies = request.getCookies();
    	if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
               if (cookies[i].getName().equals(name)){
            	   cookies[i].setPath("/");
            	   cookies[i].setMaxAge(0);
            	   response.addCookie(cookies[i]);
            	   break;
               }
            }
        }
    }
    
	/**
	 * 获得cookie
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月10日
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		if(cookieMap.containsKey(name)){
			Cookie cookie = cookieMap.get(name);
			return cookie;
		}else{
			return null;
		} 
	}
	
	/**
	 * 读取cookie
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月10日
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
		Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){
			for(Cookie cookie : cookies){
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}

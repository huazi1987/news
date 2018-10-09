package com.news.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类(List、Map)
 * @Description:
 * @author wanghz
 * @date 2015年1月9日
 */
public class CollectionUtil {
	
	private static final String DEFAULT_REGEX = ",";
	
	/**
	 * 判断Map数组是否为空
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<String,Object> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * 判断List是否为空
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		return (list == null || list.isEmpty());
	}
	
	/**
	 * 判断Map数组是否不为空
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<String,Object> map) {
		return !isEmpty(map);
	}
	
	/**
	 * 判断List是否为不空
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * 按照指定字符转换为数组
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param value
	 * @param regex
	 * @return
	 */
	public static List<String> replaceList(String value,String regex) {
		List<String> listStr = new ArrayList<String>();
		if (!StringUtil.isEmpty(value)) {
			String[] str = value.split(regex);
			for (String sv : str) {
				listStr.add(sv);
			}
		}
		return listStr;
	}
	
	/**
	 * 按照逗号分割字符串并转换为数组
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param value
	 * @return
	 */
	public static List<String> replaceList(String value) {
		return replaceList(value, DEFAULT_REGEX);
	}
	
	/**
	 * 将数组转换为字符串
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param list
	 * @param regex
	 * @return
	 */
	public static String replaceString(List<String> list,String regex) {
		StringBuffer sbResult = new StringBuffer();
		if (!CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				sbResult.append(list.get(i));
				if(i+1!=list.size()){
					sbResult.append(regex);
				}
			}
		}
		return sbResult.toString();
	}
	
	/**
	 * 将List<String>数组转换为以冒号，逗号间隔的字符串，多用于IN语句
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月30日
	 * @param list
	 * @return
	 */
	public static String replaceStringColon(List<String> list) {
		StringBuffer sbResult = new StringBuffer();
		if (!CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				sbResult.append("'"+list.get(i)+"'");
				if(i+1!=list.size()){
					sbResult.append(DEFAULT_REGEX);
				}
			}
		}
		return sbResult.toString();
	}
	
	/**
	 * 将数组转换为字符串，默认逗号间隔
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param list
	 * @return
	 */
	public static String replaceString(List<String> list) {
		return replaceString(list, DEFAULT_REGEX);
	}

	/**
	 * 移除List<String>中的重复项
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param listStr
	 * @return
	 */
	public static List<String> removeRepeatList(List<String> listStr) {
		List<String> listStrNew = new ArrayList<String>();
		if (!CollectionUtil.isEmpty(listStr)) {
			for (String str : listStr) {
				if (!listStrNew.contains(str)) {
					listStrNew.add(str);
				}
			}
		}
		return listStrNew;
	}

	/**
	 * 如果将listStr2中在listStr1中存在，就删除listStr1中的元素删除
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param listStr1
	 * @param listStr2
	 * @return
	 */
	public static List<String> deleteSurplus(List<String> listStr1, List<String> listStr2) {
		if (!CollectionUtil.isEmpty(listStr1) && !CollectionUtil.isEmpty(listStr2)) {
			for (String str2 : listStr2) {
				if (listStr1.contains(str2)) {
					listStr1.remove(str2);
					if (listStr1.contains(str2)) {
						deleteSurplus(listStr1, listStr2);
					}
				}
			}
		}
		return listStr1;
	}
	
	/**
	 * 两个数组交叉合并为一个数组
	 * @Description: 
	 * @author wanghz
	 * @date 2017年2月7日
	 * @param startList
	 * @param endList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> crossIterList(List<?> startList,List<?> endList)
	{
		List lstTmp = new ArrayList();
		for (int i = 0; i < startList.size(); i++) {
			lstTmp.add(startList.get(i));
			if(i < endList.size()){
				lstTmp.add(endList.get(i));
			}
		}
		
		if(startList.size() < endList.size()){
			lstTmp.addAll(endList.subList(startList.size(), endList.size()));
		}
		return lstTmp;
	}
}

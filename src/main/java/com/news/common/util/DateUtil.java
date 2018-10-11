package com.news.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 日期格式:yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 支付格式：yyyyMMddHHmmss
	 */
	public static final String DATE_FORMAT_WXPAY = "yyyyMMddHHmmss";
	/**
	 * 日期格式:yyyy-MM-dd HH:mm:ss:SSSS
	 */
	public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss:SSSS";
	/**
	 * 日期格式:yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

	/**
	 * 转换日期格式
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param date
	 * @return
	 */
	public static String formatShortDate(Date date){
		return formatDate(date, DATE_FORMAT_SHORT);
	}
	
	/**
	 * 转换日期格式
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param date
	 * @return
	 */
	public static String formatLongDate(Date date){
		return formatDate(date, DATE_FORMAT_LONG);
	}
	
	/**
	 * 转换日期格式
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return formatDate(date, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 根据指定格式转换日期
	 * @param dateTime
	 * @param pattern(为空则返回默认)
	 * @return
	 * @author wanghz
	 */
	public static String formatDate(Date dateTime, String pattern){
		if(StringUtil.isEmpty(pattern)){
			pattern = DATE_FORMAT_DEFAULT;
		}
		SimpleDateFormat dateFmt = new SimpleDateFormat(pattern);
		return dateFmt.format(dateTime);
	}
	
	/**
	 * 根据字符串转换日期
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date parseShortDate(String strDate) throws ParseException {
		return parseDate(strDate, DATE_FORMAT_SHORT);
	}
	
	/**
	 * 根据字符串转换日期
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date parseLongDate(String strDate) throws ParseException {
		return parseDate(strDate, DATE_FORMAT_LONG);
	}
	
	/**
	 * 根据字符串转换日期
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String strDate) throws ParseException {
		return parseDate(strDate, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 根据字符串转换日期
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String strDate,String pattern) throws ParseException {
		if(!StringUtil.isEmpty(strDate) && !StringUtil.isEmpty(pattern)){
			return new SimpleDateFormat(pattern).parse(strDate);
		}
		return null;
	}
	
	/**
	 * 根据出生日期计算年龄
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param birthDay
	 * @return
	 */
	public static int getAge(Date birthDay){   
		Calendar cal = Calendar.getInstance();   
		if (cal.before(birthDay)) {   
			return 0;   
		}   
		int yearNow = cal.get(Calendar.YEAR);   
		int monthNow = cal.get(Calendar.MONTH);   
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);   
		cal.setTime(birthDay);   
		int yearBirth = cal.get(Calendar.YEAR);   
		int monthBirth = cal.get(Calendar.MONTH);   
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);   
		int age = yearNow - yearBirth;   
		if (monthNow <= monthBirth) {   
			if (monthNow == monthBirth) {   
				if (dayOfMonthNow < dayOfMonthBirth) {   
					age--;   
				}   
			} else {   
				age--;   
			}   
		}
		return age<0?0:age;   
	}

	/**
	 * 根据日期获得星期 
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) { 
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }; 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date); 
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
		return weekDaysName[intWeek]; 
	} 

	/**
	 * 计算两个日期相隔天数
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDateCount(Date startDate, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		long ls = c.getTimeInMillis();
		c.setTime(endDate);
		long le = c.getTimeInMillis();
		int getCnt = (int) ((le - ls) / (24 * 3600 * 1000));
		return getCnt;
	}
	
	/**
	 * 获得系统当前时间
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @return
	 */
	public static Date getDateTime()
	{
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 获得系统当前时间
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @return
	 */
	public static String getCurrentTime()
	{
		return formatDate(getDateTime());
	}
	
	/**
	 * 增加天数
	 * @Description: 
	 * @author wanghz
	 * @date 2016年7月27日
	 * @param day
	 * @return
	 */
	public static long getAfterDay(int day){
		return ((System.currentTimeMillis() / 1000) + 60 * 60 * 24 * day) * 1000;
	}
	
	/**
	 * 判断是否是同一天
	 * @Description: 
	 * @author wanghz
	 * @date 2017年3月2日
	 * @param data
	 * @return
	 */
	public static boolean isToday(Date data){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String d1 = sdf.format(data);
	    String d2 = sdf.format(new Date());
	    return d1.equals(d2);
	}
	
	public static void main(String[] args){
		
//		System.out.println(WX_SING);
//		System.out.println(MessageFormat.format(WX_SING, "dwqdwqdwq","dwqdeqw"));
		
//		Date d1 = DateUtil.parseDate("2015-01-01 10:00:00");
//		Date d2 = DateUtil.parseDate("2015-01-05 09:00:00");
//		long time1=System.nanoTime();
//		for(int ii=0;ii<10000;ii++){
//			System.out.println(DateUtil.getDateCount(d1, d2));
//		}
//		long time2=System.nanoTime();
//		System.out.println((time2-time1)/1000000d +"ms");
		
//		for (int i = 0; i < 10; i++) {
//			long c = System.currentTimeMillis();
//			System.out.println(c);
//			System.out.println(DateUtil.formatDate(new Date(c)));
//			Thread.sleep(2000);
//			if(i+1 == 10){
//				System.out.println(DateUtil.formatDate(new Date(c + 7200 * 1000)));//增加两个小时
//			}
//		}
		
//		System.out.println(System.currentTimeMillis());
//		System.out.println(1468392825);
//		Long c = System.currentTimeMillis();
//		String s = c.toString();
//		System.out.println(c);
//		System.out.println(c.intValue());
//		System.out.println(Integer.valueOf(s));
//		System.out.println(DateUtil.formatDate(new Date(((System.currentTimeMillis() / 1000) + 60*60*24*365)*1000)));
//		System.out.println(DateUtil.formatDate(DateUtil.getDateTime(),"yyyy-MMMM-dd"));
		System.out.println(DateUtil.formatDate(new Date(1485052480326L)));
	}
}

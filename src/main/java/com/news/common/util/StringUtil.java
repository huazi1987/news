package com.news.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @Description:
 * @author wanghz
 * @date 2015年1月9日
 */
public class StringUtil {
	
	private static final String REG_DOMAIN = "(?<=//|)((\\w)+\\.)+\\w+";
	
	/**
	 * 判断值是否为空
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月8日
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		return (value == null || "".equals(value));
	}
	
	/**
	 * 判断值是否为不为空
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月8日
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}

	/**
	 * 截取字符 剩余...代替
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月8日
	 * @param value
	 * @param length
	 * @return
	 */
	public static String cutOutString(String value, int length) {
		if (isEmpty(value)) {
			return "";
		}
		if (value.length() <= length) {
			return value;
		} else {
			return value.substring(0, length) + "...";
		}
	}
	
	/**
	 * 金额小写转大写
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月8日
	 * @param n
	 * @return
	 */
	public static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };
		String head = n < 0 ? "负" : "";
		n = Math.abs(n);
		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);
		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零")
						.replaceAll("^整$", "零元整");
	}
	
	/**
	 * 生成不重复的ID
	 * @return
	 * @author whz
	 */
	public static String GenerateIdentifier() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date().getTime()) + new Double(Math.random() * 100000).intValue();
	}
	
	/**
	 * 生成UUID,并转换为大写
	 * @return
	 * @author whz
	 */
	public static String GenerateUUID(){
		return String.valueOf(UUID.randomUUID()).toUpperCase();
	}
	
	/**
	 * 生成随机数
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月8日
	 * @param length
	 * @return
	 */
	public static String getStochastic(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int number;
		for (int i = 0; i < length; i++) {
			number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 生成随机数(默认长度8位)
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月8日
	 * @return
	 */
	public static String getStochastic() {
		return getStochastic(8);
	}
	
	/**
	 * 获得域名
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月27日
	 * @param url
	 * @return
	 */
	public static String getDomain(String url){
		Pattern p = Pattern.compile(REG_DOMAIN);  
        Matcher m = p.matcher(url);  
        if(m.find()){  
              return m.group();  
        }
        return null;
	}
	
	/**
	 * 获得根域名
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月27日
	 * @param url
	 * @return
	 */
	public static String getRootDomain(String url){
		Pattern p = Pattern.compile(REG_DOMAIN);  
        Matcher m = p.matcher(url);  
        if(m.find()){
              String d = m.group();
              String [] tmp = d.split("\\.");
              if(tmp.length==3){
            	  return "www."+tmp[1]+"."+tmp[2];
              }else{
            	  return d;
              }
        }
        return null;
	}
	
	/**
	 * 将字符串格式化为驼峰命名格式
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param str
	 * @param large
	 * @return
	 */
	private static String formatForCamelCase(String str,boolean large){
		String [] tmpStr = str.toLowerCase().split("_");
		StringBuffer sbStr = new StringBuffer();
		for (int i = 0; i < tmpStr.length; i++) {
			if(i==0 && !large){
				sbStr.append(tmpStr[i].substring(0, 1).toLowerCase()+tmpStr[i].substring(1));
			}else{
				sbStr.append(tmpStr[i].substring(0, 1).toUpperCase()+tmpStr[i].substring(1));
			}
		}
		return sbStr.toString();
	}
	
	/**
	 * 将字符串格式化为大驼峰命名格式
	 * 例：sys_user转换为SysUser
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param str
	 * @return
	 */
	public static String formatForGreatCamelCase(String value){
		return formatForCamelCase(value, true);
	}
	
	/**
	 * 将字符串格式化为小驼峰命名格式
	 * 例：sys_user转换为sysUser
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param str
	 * @return
	 */
	public static String formatForSmallCamelCase(String value){
		return formatForCamelCase(value, false);
	}
	
	/**
	 * 将数组转成SQL认识的字符串     123,432,2312     id in('123','432','2312')
	 * @param ids
	 * @return
	 */
	public static String formatArrayToStr(String[] ids) {
		StringBuffer str=new StringBuffer();
		for(int i=0;i<ids.length;i++){
			str.append("'"+ids[i]+"',");
		}
		if(ids.length>0){
			str.deleteCharAt(str.length()-1);
		}
		return str.toString();
	}
	
	/**
	 * 将前后两端以逗号分隔的字符串转成sql认识的字符串
	 * 例如： ,123,,232,,12, 转换 '123','232','12'
	 * @Description: 
	 * @author wanghz
	 * @date 2015年3月31日
	 * @param ids
	 * @return
	 */
	public static String formatStrToStr(String ids) {
		if(ids==null)return null;
		String [] tmpStr = ids.split(",");
		StringBuffer str=new StringBuffer();
		for (int i = 0; i < tmpStr.length; i++) {
			if(tmpStr[i]!=null && !"".equals(tmpStr[i])){
				if(i+1==tmpStr.length){
					str.append("'"+tmpStr[i]+"'");
				}else{
					str.append("'"+tmpStr[i]+"',");
				}
			}
		}
		return str.toString();
	}

	
	/**
	 * 实体类转换为Map<String,String>
	 * @Description: 
	 * @author wanghz
	 * @date 2018年7月20日
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> convertBean(Object bean) throws Exception {
		Map<String,String> returnMap = new HashMap<String,String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean);
				if (result != null) {
					returnMap.put(propertyName, result.toString());
				}
			}
		}
		return returnMap;
	}
}

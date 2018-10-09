package com.news.common.json;


public class DataGridTool {
	
	/**
	 * 生成带分页的表格数据格式
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-5-4
	 * @param total
	 * @param array
	 * @return
	 */
	@Deprecated
	public static String formatDataGridPage(int total,String array) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":"+total+",\"rows\":");
		sb.append((array==null?"[]":array));
		sb.append(",\"page1\":"+array.length()+"");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 生成带分页的表格数据格式(easyui和ace通用)
	 * @Description: 
	 * @author wanghz
	 * @date 2016年4月6日
	 * @param total
	 * @param totalPage
	 * @param array
	 * @return
	 */
	public static String formatDataGridPage(int total,int totalPage,String array) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":"+total+",\"totalPage\":"+totalPage+",\"rows\":");
		sb.append((array==null?"[]":array));
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 
	 * @Description: 
	 * @author wanghz
	 * @date 2018年2月7日
	 * @param total(页总数)
	 * @param records(行总数 )
	 * @param array
	 * @return
	 */
	public static String formatJGridPage(int total,long records,String array) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":"+total+",\"records\":"+records+",\"rows\":");
		sb.append((array==null?"[]":array));
		sb.append("}");
		return sb.toString();
	}
}

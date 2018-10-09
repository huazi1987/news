package com.news.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FtlUtil {
	
	private static final String DEFAULT_ENCODING = "utf-8";
	
	/**
	 * 加载模板并输出字符串
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param param
	 * @param ftlPath
	 * @param ftlName
	 * @return
	 */
	public static String loadFtlOutStr(Map<String,Object> param,String ftlPath,String ftlName){
		StringWriter sw = new StringWriter();
		try {
			loadFtl(param, ftlPath, ftlName, sw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeWriter(sw);
		}
		return sw.toString();
	}
	
	/**
	 * 加载模板并创建文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param targetFilePath
	 * @param targetFileName
	 * @param param
	 * @param ftlPath
	 * @param ftlName
	 * @return
	 */
	public static boolean loadFtlOutFile(String targetFilePath,String targetFileName,Map<String,Object> param,String ftlPath,String ftlName){
		FileWriter fileWriter = null;
		try {
			File targetFileFloder = new File(targetFilePath);
			if(!targetFileFloder.exists()){//创建目录
				targetFileFloder.mkdirs();
			}
			fileWriter = new FileWriter(new File(targetFilePath + targetFileName));
			loadFtl(param, ftlPath, ftlName, fileWriter);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeWriter(fileWriter);
		}
		return false;
	}
	
	/**
	 * 加载模板并按照指定格式输出
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param dataModel
	 * @param ftlPath
	 * @param ftlName
	 * @param out
	 */
	private static void loadFtl(Map<String,Object> dataModel,String ftlPath,String ftlName,Writer out){
		Configuration configuration = new Configuration();
		try {
			configuration.setDefaultEncoding(DEFAULT_ENCODING);
			configuration.setDirectoryForTemplateLoading(new File(ftlPath));
			Template t = configuration.getTemplate(ftlName);
			t.process(dataModel, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			configuration.clearTemplateCache();
		}
	}
	
	/**
	 * 关闭StringWriter字符流
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月20日
	 * @param sw
	 */
	private static void closeWriter(Writer writer){
		try {
			if(writer!=null){
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

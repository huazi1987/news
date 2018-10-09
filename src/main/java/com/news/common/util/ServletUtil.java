package com.news.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
	
	/**
	 * 下载文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月22日
	 * @param response
	 * @param fileName
	 * @param filePath
	 */
	public static void downFile(HttpServletResponse response, String fileName,String filePath)
	{
		downFile(response, fileName, new File(filePath));
	}
	
	/**
	 * 下载文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月27日
	 * @param response
	 * @param fileName
	 * @param file
	 */
	public static void downFile(HttpServletResponse response, String fileName,File file)
	{
		try {
			InputStream is = new FileInputStream(file);
			downFile(response, fileName, is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月22日
	 * @param response
	 * @param fileName
	 * @param is
	 */
	public static void downFile(HttpServletResponse response, String fileName,InputStream is)
	{
		OutputStream os = null;
		response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        try {
        	os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	try {
        		if(os!=null){os.close();}
        		if(is!=null){is.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}

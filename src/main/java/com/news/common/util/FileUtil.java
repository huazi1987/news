package com.news.common.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;


import sun.misc.BASE64Decoder;

public class FileUtil {
	
	private static final int IMAGE_SIZE_MIN = 50 * 1024;
	private static final int IMAGE_SIZE_MAX = 200 * 1024;
	
	/**
	 * 获得文件扩展名
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param fileName
	 * @return
	 */
	public static String getFileNameSuffix(String fileName) {
		if (StringUtil.isEmpty(fileName) || fileName.indexOf(".") < 1) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * 获得该目录下所有文件名
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param path
	 * @return
	 */
	public static String[] getAllFileName(String path){
		File dir = new File(path);
		return dir.list();
	}
	
	/**
	 * 获得该目录下所有文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月9日
	 * @param path
	 * @return
	 */
	public static File[] getAllFile(String path){
		File dir = new File(path);
		return dir.listFiles();
	}
	
	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年7月22日
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
	
	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年7月22日
	 * @return
	 */
	public static boolean deleteDir(String filePath) {
		File dir = new File(filePath);
		return deleteDir(dir);
	}
	
	/**
	 * 根据文件路径及base64编码的文件内容生成文件
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月26日
	 * @param filePath(格式：文件路径+完整文件名)
	 * @param strFile(BASE64编译的字符流)
	 * @throws IOException
	 */
	public static void createFile(String filePath,String strFile){
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			byte[] b = decoder.decodeBuffer(strFile);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			out = new FileOutputStream(filePath);
			out.write(b);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 下载网络资源
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月27日
	 * @param filePath(保存路径)
	 * @param urlPath(URL地址)
	 */
	public static void downloadFile(String filePath,String urlPath) {
        int byteread = 0;
        FileOutputStream fs = null;
        try {
        	URL url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
             fs = new FileOutputStream(filePath);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
			if (fs!=null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static byte[] compressPic(InputStream img, long size) throws IOException {
		ImageWriter imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageWriteParam imgWriteParams = new JPEGImageWriteParam(null);

		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imgWriteParams.setCompressionQuality(getQuality(size));
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

		BufferedImage image = ImageIO.read(img);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		ColorModel colorModel = image.getColorModel();
		imgWriteParams
				.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

		imgWrier.reset();
		imgWrier.setOutput(ImageIO.createImageOutputStream(out));
		imgWrier.write(null, new IIOImage(image, null, null), imgWriteParams);

		return out.toByteArray();
	}

	private static float getQuality(long size) {
		if (size >= IMAGE_SIZE_MIN && size <= IMAGE_SIZE_MAX) {
			return 0.75f;
		}
		if (size > IMAGE_SIZE_MAX) {
			return 0.65f;
		}
		return 0.95f;
	}
}

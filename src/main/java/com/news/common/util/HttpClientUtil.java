package com.news.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private final static String CHARSET = "utf-8";
	
	public static String doPostJson(String url, String json, Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            if (header != null) {
                for (String key : header.keySet()) {
                    httpPost.setHeader(key, (String) header.get(key));
                }
            }
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

	/**
	 * POST请求
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月12日
	 * @param url
	 * @param map
	 * @return
	 */
	public static String doPost(String url,Map<String,String> param){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> iterator = param.entrySet().iterator();  
		while(iterator.hasNext()){  
			Entry<String,String> elem = (Entry<String, String>) iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
		}
		return doPost(url, list, null); 
	}
	
	public static String doPost(String url,Map<String,String> param,Map<String,String> headMap){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> iterator = param.entrySet().iterator();  
		while(iterator.hasNext()){  
			Entry<String,String> elem = (Entry<String, String>) iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
		}
		return doPost(url, list, headMap); 
	}

	/**
	 * POST请求
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月13日
	 * @param url
	 * @param list
	 * @return
	 */
	private static String doPost(String url,List<NameValuePair> list,Map<String,String> headMap){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String result = null;  
		try{  
			httpClient = HttpClients.createDefault();
//			httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888)
			HttpPost httpPost = new HttpPost(url);
			if(headMap != null && !headMap.isEmpty()){
				Iterator<Entry<String, String>> iterator = headMap.entrySet().iterator();  
				while(iterator.hasNext()){
					Entry<String,String> elem = (Entry<String, String>) iterator.next();
					httpPost.addHeader(elem.getKey(), elem.getValue());
				}
			}
			if(list != null && list.size() > 0){  
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,CHARSET);  
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			if(response != null){  
				HttpEntity resEntity = response.getEntity();  
				if(resEntity != null){  
					result = EntityUtils.toString(resEntity,CHARSET);  
				}  
			}  
		}catch(Exception ex){  
			ex.printStackTrace();  
		} finally {
			closeClient(response, httpClient);
		}
		return result;  
	}
	
	/**
	 * 以POST方式向指定地址上传文件
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月12日
	 * @param url
	 * @param paramKey
	 * @param stream
	 * @param fileName
	 * @return
	 */
	public static String doPostUpload(String url,String paramKey,InputStream stream,String fileName) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {  
			httpClient = HttpClients.createDefault();
			HttpPost uploadFile = new HttpPost(url);
			//模拟form提交表单
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody(paramKey, stream, ContentType.APPLICATION_OCTET_STREAM, fileName);
			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			//获得响应数据
			response = httpClient.execute(uploadFile);
			HttpEntity responseEntity = response.getEntity();
			return EntityUtils.toString(responseEntity, CHARSET);
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			closeClient(response, httpClient);
		}
		return null;
	}

	/**
	 * GET请求
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月12日
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);  
			response = httpClient.execute(httpget);  
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) { 
			e.printStackTrace();  
		} finally {  
			closeClient(response, httpClient);
		}
		return null;
	}

	/**
	 * 关闭连接,释放资源
	 * @Description: 
	 * @author wanghz
	 * @date 2016年5月12日
	 * @param response
	 * @param httpClient
	 */
	private static void closeClient(CloseableHttpResponse response,CloseableHttpClient httpClient)
	{
		if(response!=null){
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(httpClient!=null){
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		String url = "http://localhost:8090/answer/questionBank/list";
		String result = HttpClientUtil.doGet(url);
		System.out.println(result);
	}
}
package com.news.controller.cms;

import com.alibaba.fastjson.JSONObject;
import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.page.Pagination;
import com.news.common.util.StringUtil;
import com.news.model.NFile;
import com.news.model.News;
import com.news.service.FileService;
import com.news.service.FileUploadService;
import com.news.service.NewsService;
import com.news.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/news")
public class NewsCMSController extends BaseController{

	@Autowired
	private NewsService newsService;

	@Autowired
	private FileUploadService fileUploadService;


	private final static String TMP_PATH = "/tmp/";


	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "news/newsList";
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, int page,String sidx,String sord) throws Exception{
		Map<String, String> orderBy = new HashMap<>();
		if(!StringUtil.isEmpty(sidx) && !StringUtil.isEmpty(sord)) {
			orderBy.put(sidx, sord);
		}

		Page<News> result = newsService.queryNewsList2(new Pagination(page, rows));
		String jsonList = jacksonMapper.writeValueAsString(result.getContent());
		return DataGridTool.formatJGridPage(result.getTotalPages(),result.getTotalElements(),jsonList);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,String id) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(id)){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}
			newsService.delete(id);
			result.put("status", "true");
			return result.toJSONString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		result.put("status", "false");
		result.put("msg","系统错误");
		return result.toJSONString();
	}


	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,String id, String title, String content, String thumbnailUrl) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(id) || StringUtil.isEmpty(title) || StringUtil.isEmpty(content)){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}
			News news = new News();
			news.setId(Integer.parseInt(id));
			news.setTitle(title);
			news.setContent(content);
			news.setThumbnailUrl(thumbnailUrl);
			newsService.update(news);
			result.put("status", "true");

			uploadNewsHtml(id, content);

			return result.toJSONString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		result.put("status", "false");
		result.put("msg","系统错误");
		return result.toJSONString();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,String title, String content, String thumbnailUrl) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(thumbnailUrl) || StringUtil.isEmpty(title) || StringUtil.isEmpty(content)){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}
			News news = new News();
			news.setThumbnailUrl(thumbnailUrl);
			news.setTitle(title);
			news.setContent(content);
			newsService.insert(news);
			result.put("status", "true");

			uploadNewsHtml(String.valueOf(news.getId()), content);

			return result.toJSONString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		result.put("status", "false");
		result.put("msg","系统错误");
		return result.toJSONString();
	}

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadFile(HttpServletRequest request, String userId, String key) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("newsImage");
		JSONObject json = new JSONObject();
		try {
			json.put("url", fileUploadService.uploadFile(file));
			json.put("status", "true");
		} catch (Exception e) {
			json.put("status", "false");
			e.printStackTrace();
		}

		return json.toJSONString();
	}


	@RequestMapping(value = "/uploadKindEditorImage", method = RequestMethod.POST)
	public @ResponseBody String uploadKindEditorImage(HttpServletRequest request, String userId, String key) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("imgFile");
		JSONObject json = new JSONObject();
		try {
			json.put("url", fileUploadService.uploadFile(file));
			json.put("error", 0);
		} catch (Exception e) {
			json.put("status", "false");
			e.printStackTrace();
		}

		return json.toJSONString();
	}

	@RequestMapping(value = "/imageUploadJson", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadFileManage(HttpServletRequest request) {

		JSONObject json = new JSONObject();

		return json.toJSONString();
	}


	private void uploadNewsHtml(String id, String content){
		String html = buildHtml(content);
		String filename = "news-"+id+".html";
		File file = new File(TMP_PATH+filename);

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			bufferedReader = new BufferedReader(new StringReader(html));
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			char buf[] = new char[1024];         //字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			fileUploadService.uploadHtml(file, filename);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private String buildHtml(String content){


//		System.out.println(content.replaceAll("\r\n",""));
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"loading\">");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\" />");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=0.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\" />");
		sb.append("<meta name=\"description\" content=\"通讯帝\"\">");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://communication-emperor.oss-cn-beijing.aliyuncs.com/html/css/style.css\" />");
		sb.append("<meta content=\"yes\" name=\"apple-mobile-web-app-capable\">");
		sb.append("<meta content=\"black\" name=\"apple-mobile-web-app-status-bar-style\">");
		sb.append("<meta content=\"telephone=no\" name=\"format-detection\">");
		sb.append("<title>通讯帝</title>");
		sb.append("<style type=\"text/css\">");
		sb.append("img{width:100%;}");
		sb.append("video{width:100%; height:auto;}");
		sb.append("</style>");
//		sb.append("");
//		sb.append("");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div id=\"container\">");
		sb.append(content.replaceAll("\r\n",""));
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

}

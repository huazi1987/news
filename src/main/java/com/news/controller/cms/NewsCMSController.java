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
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/news")
public class NewsCMSController extends BaseController{

	@Autowired
	private NewsService newsService;

	@Autowired
	private FileUploadService fileUploadService;


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


}

package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.page.Pagination;
import com.news.common.util.StringUtil;
import com.news.model.News;
import com.news.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/news")
public class NewsController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private final static int PAGE_SIZE = 10;

	@Autowired
	private NewsService newsService;

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request, int pageNo) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		
		try {
			Pagination pagination = new Pagination(pageNo, PAGE_SIZE);
			Page<News> data = newsService.queryNewsList(pagination);
			result.put("status", true);
			result.put("data", data);
			return result.toJSONString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toJSONString();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, String id) {
		JSONObject result = new JSONObject();
		result.put("status", false);

		try {
			if (StringUtil.isEmpty(id)){
				result.put("msg","参数错误");
				return result.toJSONString();
			}
			News news = newsService.getNewsById(id);
			result.put("status", true);
			result.put("msg","ok");
			result.put("data", news);
			return result.toJSONString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toJSONString();
	}


}

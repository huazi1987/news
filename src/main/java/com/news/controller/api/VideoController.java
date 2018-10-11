package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.page.Pagination;
import com.news.model.Video;
import com.news.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/video")
public class VideoController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private final static int PAGE_SIZE = 10;

	@Autowired
	private VideoService videoService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request, int pageNo) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		Pagination pagination = new Pagination(pageNo, PAGE_SIZE);

		Page<Video> data = videoService.queryVideoList(pagination);

		try {
			result.put("status", true);
			result.put("msg","ok");
			result.put("data", data);
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toJSONString();
	}
}

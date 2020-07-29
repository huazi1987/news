package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/image")
public class ImageController {
	
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("image");
		JSONObject json = new JSONObject();
		try {
			if (files.size() <= 0){
				json.put("msg","ok");
				json.put("status", false);
				return json.toJSONString();
			}
			List<String> urls = new ArrayList<>(files.size());
			files.forEach(file -> {
				try {
					String url = fileUploadService.uploadImage(file);
					urls.add(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			json.put("data", urls);
			json.put("msg","ok");
			json.put("status", true);
		} catch (Exception e) {
			json.put("status", false);
			e.printStackTrace();
		}

		return json.toJSONString();
	}
}

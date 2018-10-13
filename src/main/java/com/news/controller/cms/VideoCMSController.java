package com.news.controller.cms;

import com.alibaba.fastjson.JSONObject;
import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.page.Pagination;
import com.news.common.util.StringUtil;
import com.news.model.Video;
import com.news.service.FileService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/video")
public class VideoCMSController extends BaseController{

	@Autowired
	private VideoService videoService;

	@Autowired
	private FileService showFileService;

	private final static String DOWNLOAD_EXCEL_FILE_PATH = "/tmp/";


	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		System.out.println("===================");
		return "/WEB-INF/view/video/videoList.html";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, int page,String sidx,String sord) throws Exception{
		Map<String, String> orderBy = new HashMap<>();
		if(!StringUtil.isEmpty(sidx) && !StringUtil.isEmpty(sord)) {
			orderBy.put(sidx, sord);
		}

		List<String> filterItem = null;

		Page<Video> result = videoService.queryVideoList(new Pagination(page, rows));
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

			videoService.delete(id);

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
	public String modify(HttpServletRequest request,Video video) {
		JSONObject result = new JSONObject();
		try {

			videoService.update(video);

			result.put("status", "true");
			return result.toJSONString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		result.put("status", "false");
		result.put("msg","系统错误");
		return result.toJSONString();
	}

	@RequestMapping(value = "/uploadVideoThumbnail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadVideoThumbnail(HttpServletRequest request, String userId, String key) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("itemVideoThumb");
		JSONObject json = new JSONObject();

		if (StringUtils.isEmpty(userId)) {
			json.put("msg", "未选择用户");
			json.put("status", "false");
			return json.toJSONString();
		}
		if (StringUtils.isEmpty(key)) {
			json.put("msg", "请先上传视频");
			json.put("status", "false");
			return json.toJSONString();
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
			BufferedImage image = ImageIO.read(bis);

			json.put("imageWidth", image.getWidth(null));
			json.put("imageHeight", image.getHeight(null));
//			json.put("url", showFileService.uploadVideoThumbnail(file, userId, key));
			json.put("status", "true");
		} catch (Exception e) {
			json.put("status", "false");
			e.printStackTrace();
		}

		return json.toJSONString();
	}

	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadVideo(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("itemVideo");
		JSONObject json = new JSONObject();

		try {
			BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
			BufferedImage image = ImageIO.read(bis);

			json.put("imageWidth", image.getWidth(null));
			json.put("imageHeight", image.getHeight(null));
//			json.put("url", showFileService.uploadVideoThumbnail(file, userId, key));
			json.put("status", "true");
		} catch (Exception e) {
			json.put("status", "false");
			e.printStackTrace();
		}

		return json.toJSONString();
	}


}

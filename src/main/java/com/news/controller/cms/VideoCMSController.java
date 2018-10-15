package com.news.controller.cms;

import com.alibaba.fastjson.JSONObject;
import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.page.Pagination;
import com.news.common.util.StringUtil;
import com.news.model.Video;
import com.news.service.FileUploadService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/video")
public class VideoCMSController extends BaseController{

	@Autowired
	private VideoService videoService;

	@Autowired
	private FileUploadService fileUploadService;


	private static final String TMP_VIDEO_PATH = "/tmp/";


	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "video/videoList";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, int page,String sidx,String sord) throws Exception{
		Map<String, String> orderBy = new HashMap<>();
		if(!StringUtil.isEmpty(sidx) && !StringUtil.isEmpty(sord)) {
			orderBy.put(sidx, sord);
		}

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
	public String modify(HttpServletRequest request,String id, String title, String description, String thumbnailUrl) {
		JSONObject result = new JSONObject();
		try {
			Video video = new Video();
			video.setId(Integer.parseInt(id));
			video.setDesc(description);
			video.setTitle(title);
			video.setThumbnailUrl(thumbnailUrl);
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

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request, String title, String description, String thumbnailUrl,
					  String contentUrl, int height, int width, int duration, int size) {
		JSONObject result = new JSONObject();
		try {
			Video video = new Video();
			video.setDesc(description);
			video.setTitle(title);
			video.setThumbnailUrl(thumbnailUrl);
			video.setContentUrl(contentUrl);
			video.setHeight(height);
			video.setWidth(width);
			video.setDuration(duration);
			video.setSize(size);
			videoService.insert(video);

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
	public @ResponseBody String uploadVideoThumbnail(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("itemVideoThumb");
		JSONObject json = new JSONObject();

		try {

			String url = fileUploadService.uploadImage(file);
			System.out.println(url);
			json.put("url", url);
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
//			String tmpFilePath = TMP_VIDEO_PATH+file.getOriginalFilename();
//			File tmpFile= new File(tmpFilePath);
//			file.transferTo(tmpFile);
//			File localFile = new File(tmpFilePath);
//			Encoder encoder = new Encoder();
//			MultimediaInfo multimediaInfo = encoder.getInfo(localFile);
			json.put("url", fileUploadService.uploadVideo(file));
			json.put("size", file.getSize());
			json.put("height", 0);
			json.put("width", 0);
			json.put("duration", 0);
			json.put("status", "true");
		} catch (Exception e) {
			json.put("status", "false");
			e.printStackTrace();
		}

		return json.toJSONString();
	}


}

package com.news.controller.cms;

import com.alibaba.fastjson.JSONObject;
import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.page.Pagination;
import com.news.model.Version;
import com.news.service.VersionService;
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

@Controller
@RequestMapping(value = "/cms/version")
public class VersionCMSController extends BaseController{

	@Autowired
	private VersionService versionService;

	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "/WEB-INF/view/version/versionList.html";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request) throws Exception{


		Page<Version> result = versionService.queryVersionList(new Pagination(1, rows));
		String jsonList = jacksonMapper.writeValueAsString(result.getContent());
		return DataGridTool.formatJGridPage(result.getTotalPages(),result.getTotalElements(),jsonList);
	}


	/**
	 * 逻辑删除内容
	 * @Description: 
	 * @param request
	 * @param itemId
	 * @param createTime
	 * @param deleteFlag
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteItem(HttpServletRequest request,String itemId,String createTime, String deleteFlag) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(itemId) || StringUtils.isEmpty(createTime) || StringUtils.isEmpty(deleteFlag)){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}
			
//			showItemService.deleteItem(itemId, createTime, deleteFlag);

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


}

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
		return "version/versionList";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request) throws Exception{
		Page<Version> result = versionService.queryVersionList(new Pagination(1, rows));
		String jsonList = jacksonMapper.writeValueAsString(result.getContent());
		return DataGridTool.formatJGridPage(result.getTotalPages(),result.getTotalElements(),jsonList);
	}



	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,String id) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(id) ){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}

			versionService.delete(id);
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
	public String modify(HttpServletRequest request,String id, String version) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(id) || StringUtils.isEmpty(version) ){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}

			Version v = new Version();
			v.setId(Integer.parseInt(id));
			v.setVersion(Long.parseLong(version));

			versionService.update(v);
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
	public String add(HttpServletRequest request,String osType, String version) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isEmpty(osType) || StringUtils.isEmpty(version) ){
				String resultMsg = messageSource.getMessage("error.missing.required", null, getLocale(request));
				result.put("status", "false");
				result.put("msg",resultMsg);
				return result.toJSONString();
			}

			Version v = new Version();
			v.setOsType(osType);
			v.setVersion(Long.parseLong(version));

			versionService.insert(v);
			result.put("status", "true");
			return result.toJSONString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		result.put("status", "false");
		result.put("msg","系统错误");
		return result.toJSONString();
	}


}

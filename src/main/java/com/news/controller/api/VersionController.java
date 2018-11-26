package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.util.StringUtil;
import com.news.model.Version;
import com.news.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/version")
public class VersionController {

	@Autowired
	private VersionService versionService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, String osType) {
		JSONObject result = new JSONObject();
		result.put("status", false);

		try {
			if (StringUtil.isEmpty(osType)){
				result.put("msg", "设备操作");
				return result.toJSONString();
			}
			Version version = versionService.findVersionByOSType(osType);
			String config = version.getConfig();
			if(!StringUtil.isEmpty(config)){
				try {
					JSONObject configJson = JSONObject.parseObject(config);
					if (null != configJson.get("isPass")){
						version.setPass(configJson.getBoolean("isPass"));
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			result.put("status", true);
			result.put("msg","ok");
			result.put("data", version);
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toJSONString();
	}
}

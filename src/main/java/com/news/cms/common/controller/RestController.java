package com.news.cms.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.news.cms.sys.user.model.SysUser;
import com.news.cms.sys.user.service.SysUserService;
import com.news.common.util.CommonStatic;
import com.news.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/rest")
public class RestController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value = "/syncUser", method = RequestMethod.POST)
	@ResponseBody
	public String syncUser(HttpServletRequest request,@RequestBody Map<String,Object> param) {
		JSONObject result = new JSONObject();
		result.put("status", "false");
		String account = param.get("account").toString();
		if(StringUtil.isEmpty(account))
		{
			result.put("msg","没有获取到参数");
			return result.toJSONString();
		}
		
		try {
			
			Thread.sleep(10000);

			
			SysUser sysUser = sysUserService.findByUserLogin(account);
			
			if(StringUtil.isEmpty(sysUser)){
				sysUser = new SysUser();
				sysUser.setUserLogin(account);
				sysUser.setUserName(account);
				sysUser.setRank(1);
				sysUser.setStatus(CommonStatic.TRUE);
//				sysUser.setCreateTime(DateUtil.getDateTime());
				if(account.startsWith("intern.")) {
					sysUser.setRoleIds("2018072502221690795813");
				}else {
					sysUser.setRoleIds("2018012402213923213702");
				}
				sysUserService.insert(sysUser);
				log.info(MarkerFactory.getMarker("DB"), String.format("ldap同步账户完成：%s", account), account);
				result.put("status", "true");
				result.put("msg","ok");
				return result.toJSONString();
			}
		} catch (Exception e) {
			log.error(MarkerFactory.getMarker("DB"), String.format("ldap同步账户出现异常：%s", e.getLocalizedMessage()), account);
			e.printStackTrace();
		}
		return result.toJSONString();
	}
}

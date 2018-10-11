package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.util.EncryptUtil;
import com.news.common.util.StringUtil;
import com.news.model.User;
import com.news.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@RequestBody Map<String, String> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		String loginName = params.get("loginName");
		String password = params.get("password");
		String nickname = params.get("nickname");
		try {
			if (StringUtil.isEmpty(loginName)){
				result.put("status", false);
				result.put("msg","登陆名不能为空");
				return  result.toJSONString();
			}
			if (StringUtil.isEmpty(password)){
				result.put("status", false);
				result.put("msg","密码不能为空");
				return  result.toJSONString();
			}
			User user = userService.findUserByLoginName(loginName);
			if (null != user){
				result.put("msg","该登陆名已存在");
				return result.toJSONString();
			}
			user = new User();
			user.setLoginName(loginName);
			if(!StringUtil.isEmpty(nickname)){
				user.setNickname(nickname);
			}
			user.setPassword(EncryptUtil.encodeMD5(password));

			int ret = userService.insert(user);
			if (ret <= 0){
				result.put("msg","注册失败");
				return result.toJSONString();
			}
			result.put("status", true);
			result.put("msg","注册成功");
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg","系统错误");
		}
		return result.toJSONString();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login( @RequestBody Map<String, String> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		String loginName = params.get("loginName");
		String password = params.get("password");
		try {
			if (StringUtil.isEmpty(loginName)){
				result.put("status", false);
				result.put("msg","登陆名不能为空");
				return  result.toJSONString();
			}
			if (StringUtil.isEmpty(password)){
				result.put("status", false);
				result.put("msg","密码不能为空");
				return  result.toJSONString();
			}
			User user = userService.findUserByLoginNameAndPWD(loginName, EncryptUtil.encodeMD5(password));
			if (null == user){
				result.put("msg","账号或密码错误");
				return result.toJSONString();
			}

			result.put("status", true);
			result.put("msg","登陆成功");
			result.put("data", user);
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg","系统错误");
		}
		return result.toJSONString();
	}

	@RequestMapping(value = "/profile-set", method = RequestMethod.POST)
	@ResponseBody
	public String setProfile(HttpServletRequest request, @RequestBody Map<String, String> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);

		try {
			String nickname = params.get("nickname");
			String address = params.get("address");
			String phone = params.get("phone");
			String id = params.get("id");
			User user = new User();
			if (StringUtil.isEmpty(id)){
				result.put("msg", "修改失败");
				return result.toJSONString();
			}
			user.setId(Integer.parseInt(id));
			if (!StringUtil.isEmpty(nickname)){
				user.setNickname(nickname);
			}
			if (!StringUtil.isEmpty(address)){
				user.setAddress(address);
			}
			if (!StringUtil.isEmpty(phone)){
				user.setPhone(Integer.parseInt(phone));
			}
			int ret = userService.update(user);
			if (ret <= 0){
				result.put("msg", "修改失败");
				return result.toJSONString();
			}
			result.put("status", true);
			result.put("msg","修改成功");
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg","系统错误");
		}
		return result.toJSONString();
	}
}

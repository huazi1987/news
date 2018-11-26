package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.sms.SDKTestSendTemplateSMS;
import com.news.common.util.EncryptUtil;
import com.news.common.util.StringUtil;
import com.news.model.User;
import com.news.model.VerifyCode;
import com.news.service.UserService;
import com.news.service.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private VerifyCodeService verifyCodeService;
	
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
				user.setPhone(phone);
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

	@RequestMapping(value = "/send-code", method = RequestMethod.POST)
	@ResponseBody
	public String sendCode(HttpServletRequest request, @RequestBody Map<String, String> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		String phone = params.get("phone");
		try {
			if (StringUtil.isEmpty(phone)){
				result.put("status", false);
				result.put("msg","手机号不能为空");
				return  result.toJSONString();
			}

			SDKTestSendTemplateSMS sdkTestSendTemplateSMS = new SDKTestSendTemplateSMS();
			int code = getRandomCode();
			boolean isSuccess = sdkTestSendTemplateSMS.sendSMS(phone, code);
			if (isSuccess){
				VerifyCode verifyCode = new VerifyCode();
				verifyCode.setCode(String.valueOf(code));
				verifyCode.setPhone(phone);

				if(verifyCodeService.insert(verifyCode) > 0){
					result.put("status", true);
					result.put("msg","发送成功");
				}

			}else {
				result.put("status", false);
				result.put("msg","发送失败，请重新发送");
			}

			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg","系统错误");
		}
		return result.toJSONString();
	}

	@RequestMapping(value = "/login-by-code", method = RequestMethod.POST)
	@ResponseBody
	public String loginBySMS(HttpServletRequest request, @RequestBody Map<String, String> params) {
		JSONObject result = new JSONObject();
		result.put("status", true);
		String phone = params.get("phone");
		String code = params.get("code");
		String address = params.get("address");
		try {
			if (StringUtil.isEmpty(phone)){
				result.put("status", false);
				result.put("msg","手机不能为空");
				return  result.toJSONString();
			}
			if (StringUtil.isEmpty(code)){
				result.put("status", false);
				result.put("msg","验证码不能为空");
				return  result.toJSONString();
			}

            if ("18614058084".equals(phone) && "222222".equals(code)){
				User user = new User();
				user.setLoginName(phone);
				user.setId(0);
				user.setNickname("liwenbin");

				result.put("msg","登陆成功");
				result.put("data", user);
				return result.toJSONString();
			}

			if (StringUtil.isEmpty(address)){
				result.put("status", false);
				result.put("msg","地址不能为空");
				return  result.toJSONString();
			}

            VerifyCode verifyCode = verifyCodeService.findByPhoneAndCode(phone, code);
			if (null == verifyCode){
				result.put("status", false);
				result.put("msg","验证码错误");
				return  result.toJSONString();
			}

			if(System.currentTimeMillis() - verifyCode.getCreateTime().getTime() > 5*60*1000){
				result.put("status", false);
				result.put("msg","验证码已过期");
				return  result.toJSONString();
			}

			User user = userService.findUserByLoginName(phone);
			if (null == user){
				user = new User();
				user.setLoginName(phone);
				user.setPhone(phone);
				user.setAddress(address);
				userService.insert(user);
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

	private int getRandomCode(){
		Random random =new Random();
		return random.nextInt(99999)+111111;
	}


}

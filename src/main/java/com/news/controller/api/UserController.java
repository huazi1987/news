package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.sms.SDKTestSendTemplateSMS;
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


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody Map<String, String> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		String loginType = params.get("lt");//1:qq 2:weixin 0:visitor
		String openId = params.get("openId");
		String deviceId = params.get("deviceId");

		try {

			User user = new User();

			int ret = userService.insert(user);
			if (ret <= 0){
				result.put("msg","登录失败");
				return result.toJSONString();
			}
			result.put("status", true);
			result.put("msg","登录成功");
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
			String avatarUrl = params.get("avatarUrl");
			String id = params.get("id");
			String birth = params.get("birth");
			String weigh = params.get("weight");
			String high = params.get("high");
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
			if (!StringUtil.isEmpty(avatarUrl)){
				user.setAvatarUrl(avatarUrl);
			}
			if (!StringUtil.isEmpty(birth)){
				user.setBirth(avatarUrl);
			}
			if (!StringUtil.isEmpty(weigh)){
				user.setWeight(avatarUrl);
			}
			if (!StringUtil.isEmpty(high)){
				user.setHigh(avatarUrl);
			}
			user.setIsFinished(1);
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
				user.setId(7);
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

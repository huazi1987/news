package com.news.controller.cms;

import com.news.common.base.BaseController;
import com.news.common.json.model.JSONResult;
import com.news.common.util.CommonStatic;
import com.news.common.util.EncryptUtil;
import com.news.common.util.LoginUtil;
import com.news.common.util.StringUtil;
import com.news.model.SysUser;
import com.news.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SysUserService sysUserService;

	
	/**
	 * 主页跳转与数据初始化
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-11-5
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model,HttpServletResponse response) {
		if(StringUtil.isEmpty(LoginUtil.getCurrentUserId())){
			return "login";
		}else{
			if(StringUtil.isNotEmpty(LoginUtil.getCurrentUser().getUserName())){
				model.addAttribute("userName",LoginUtil.getCurrentUser().getUserName());
			}else{
				model.addAttribute("userName",LoginUtil.getCurrentUser().getEmail());
			}
			return "index";
		}
	}
	
	/**
	 * 用户登录
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-8
	 * @param request
	 * @param model
	 * @param userLogin
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,Model model,String userLogin,String password){
		String resultMsg = messageSource.getMessage("error.user.password", null, getLocale(request));
		try {
			if(StringUtil.isNotEmpty(userLogin) && StringUtil.isNotEmpty(password)){
				SysUser sysUser = sysUserService.findByUserLogin(userLogin);
				if(!sysUser.getPassword().equals(EncryptUtil.encodeMD5(password+sysUser.getSalt())))
				{
					return jacksonMapper.writeValueAsString(JSONResult.Failure(resultMsg));
				}

				//将指定用户信息存入session
				saveSession(sysUser);
				log.info(String.format("帐号:%s,登录成功,IP:%s", userLogin,request.getRemoteHost()));
				return jacksonMapper.writeValueAsString(JSONResult.Success());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return jacksonMapper.writeValueAsString(JSONResult.Failure(resultMsg));
		}
		return "login";
	}
	
	/**
	 * 注销
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月18日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logoff", method = RequestMethod.GET)
	public String logoff(HttpServletRequest request,HttpServletResponse response,Model model) {
		LoginUtil.clearSession();
		return "login";
	}
	
	/**
	 * 将用户信息存入session
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月14日
	 * @param sysUser
	 */
	private void saveSession(SysUser sysUser)
	{
		SysUser sessionUser = new SysUser();
		sessionUser.setUserId(sysUser.getUserId());
		sessionUser.setEmail(sysUser.getEmail());
		sessionUser.setUserLogin(sysUser.getUserLogin());
		sessionUser.setUserName(sysUser.getUserName());
		LoginUtil.setCurrentUser(sessionUser);
	}

}

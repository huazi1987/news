package com.news.controller.cms;

import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.json.model.JSONResult;
import com.news.common.util.CommonStatic;
import com.news.common.util.StringUtil;
import com.news.model.SysUser;
import com.news.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/sysUser")
@Controller
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 跳转列表
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @return
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "sysUser/sysUserList";
	}
	
	/**
	 * 系统用户列表
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request,int page,int rows,SysUser sysUser){
		sysUser.setStatus(CommonStatic.TRUE);
		int count = sysUserService.findAllCount(sysUser);
		String jsonList = null;
		if(count>0){
			List<SysUser> lstUser = sysUserService.findSysUserPage(startRow(page,rows), rows, sysUser);
			jsonList = jacksonMapper.writeValueAsString(lstUser);
		}
		return DataGridTool.formatJGridPage(totalPage(count,rows),count,jsonList);
	}
	
	/**
	 * 新增系统用户
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param request
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request,SysUser sysUser){
		if(StringUtil.isEmpty(sysUser.getUserId())) {
			sysUserService.insert(sysUser);
		}else {
			sysUserService.update(sysUser);
		}
		
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 删除系统用户
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,String userId){
		if(StringUtil.isNotEmpty(userId)){
			sysUserService.delete(userId);
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 验证用户帐号是否重复
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-11-16
	 * @param request
	 * @param userLogin
	 * @return
	 */
	@RequestMapping(value = "/verifyAccount", method = RequestMethod.POST)
	@ResponseBody
	public boolean verifyAccount(HttpServletRequest request,String userLogin){
		if(StringUtil.isNotEmpty(userLogin.trim())){
			SysUser user = sysUserService.findByUserLogin(userLogin.trim());
			if(StringUtil.isEmpty(user))//如果user为空
			{
				return true;
			}
		}
		return false;
	}
}

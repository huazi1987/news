package com.news.cms.sys.role.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.news.cms.sys.role.model.SysRoleUser;
import com.news.cms.sys.role.service.SysRoleService;
import com.news.common.util.CommonStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.cms.sys.menu.service.SysMenuService;
import com.news.cms.sys.role.model.SysRole;
import com.news.cms.sys.role.model.SysRoleMenu;
import com.news.cms.sys.role.model.SysRoleUser;
import com.news.cms.sys.role.service.SysRoleService;
import com.news.cms.sys.user.model.SysUser;
import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.json.TreeJson;
import com.news.common.json.model.JSONResult;
import com.news.common.json.model.TreeNode;
import com.news.common.util.CommonStatic;
import com.news.common.util.StringUtil;

@RequestMapping("/sysRole")
@Controller
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 跳转列表
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "sysRole/sysRoleList";
	}
	
	/**
	 * 查询列表
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param request
	 * @param page
	 * @param rows
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request,int page,int rows,SysRole sysRole){
		int count = sysRoleService.findAllCount(sysRole);
		String jsonList = null;
		if(count>0){
			List<SysRole> lstSysRole = sysRoleService.findSysRoleByPage(startRow(page,rows), rows, sysRole);
			jsonList = jacksonMapper.writeValueAsString(lstSysRole);
		}
		return DataGridTool.formatDataGridPage(count,totalPage(count, rows),jsonList);
	}
	
	/**
	 * 新增
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param request
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,SysRole sysRole,String menuIds){
		sysRole.setRoleId(StringUtil.GenerateIdentifier());
		sysRole.setStatus(CommonStatic.TRUE);
		sysRole.setCreateTime(DateUtil.getDateTime());
		List<SysRoleMenu> lstMenu = new ArrayList<SysRoleMenu>();
		if(StringUtil.isNotEmpty(menuIds)){
			String[] menuTmp = menuIds.split(",");
			for (String menuId : menuTmp) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setId(StringUtil.GenerateIdentifier());
				roleMenu.setRoleId(sysRole.getRoleId());
				roleMenu.setMenuId(menuId);
				lstMenu.add(roleMenu);
			}
		}
		sysRoleService.insert(sysRole,lstMenu);
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 编辑
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param request
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,SysRole sysRole,String menuIds){
		if(StringUtil.isNotEmpty(sysRole.getRoleId())){
			List<SysRoleMenu> lstMenu = new ArrayList<SysRoleMenu>();
			if(StringUtil.isNotEmpty(menuIds)){
				String[] menuTmp = menuIds.split(",");
				for (String menuId : menuTmp) {
					SysRoleMenu roleMenu = new SysRoleMenu();
					roleMenu.setId(StringUtil.GenerateIdentifier());
					roleMenu.setRoleId(sysRole.getRoleId());
					roleMenu.setMenuId(menuId);
					lstMenu.add(roleMenu);
				}
			}
			sysRoleService.update(sysRole,lstMenu);
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 删除
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,String id){
		if(StringUtil.isNotEmpty(id)){
			String [] ids = id.split(",");
			for (String idStr : ids) {
				sysRoleService.delete(idStr);
			}
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 下拉框异步调用
	 * @Description: 
	 * @author wanghz
	 * @date 2017年11月20日
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public String select(HttpServletRequest request){
		List<SysRole> lstRole = sysRoleService.findSysRoleByPage(0, Integer.MAX_VALUE, new SysRole());
		return jacksonMapper.writeValueAsString(lstRole);
	}
	
	/**
	 * 根据用户ID获取角色列表
	 * @Description: 
	 * @author wanghz
	 * @date 2018年1月29日
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/userRole", method = RequestMethod.GET)
	@ResponseBody
	public String userRole(HttpServletRequest request,String userId){
		String result = "";
		if(StringUtil.isNotEmpty(userId)) {
			result =  sysRoleService.findRoleIdsByUserId(userId);
		}
		return result;
	}
	
	/**
	 * 根据角色编号查询角色功能菜单树形列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/roleMenulist", method = RequestMethod.GET)
	@ResponseBody
	public String roleMenulist(HttpServletRequest request,String roleId){
		List<TreeNode> lstNode = sysMenuService.findTreeList(CommonStatic.MENU_ROOT);
		if(lstNode.size()>0){
			List<TreeNode> chkList = new ArrayList<TreeNode>();
			if(StringUtil.isNotEmpty(roleId)){//查询已选菜单
				chkList = sysMenuService.findMenuByRoleId(roleId);
			}
			List<TreeNode> lstTreeNode = TreeJson.formatTree(lstNode, chkList);
			return jacksonMapper.writeValueAsString(lstTreeNode);
		}
		return null;
	}
	
	/**
	 * 根据角色编号查询角色用户列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月24日
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/roleUserlist", method = RequestMethod.GET)
	@ResponseBody
	public String roleUserlist(HttpServletRequest request,int page,int rows,String roleId){
		int count = sysRoleService.findRoleUserCountByRoleId(roleId);
		String jsonList = null;
		if(count>0){
			List<SysUser> lstSysRole = sysRoleService.findRoleUserByRoleId(startRow(page,rows), rows, roleId);
			jsonList = jacksonMapper.writeValueAsString(lstSysRole);
		}
		return DataGridTool.formatDataGridPage(count,jsonList);
	}
	
	/**
	 * 根据角色编号查询待选用户列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/optUserList", method = RequestMethod.GET)
	@ResponseBody
	public String optUserList(HttpServletRequest request,String roleId){
		if(StringUtil.isNotEmpty(roleId)){
			List<SysUser> lstSysRole = new ArrayList<SysUser>();
			lstSysRole = sysRoleService.findOptUserByRoleId(roleId);
			String jsonList = jacksonMapper.writeValueAsString(lstSysRole);
			return DataGridTool.formatDataGridPage(lstSysRole.size(),jsonList);
		}
		return null;
	}
	
	/**
	 * 添加角色用户
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param request
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "/addRoleUser", method = RequestMethod.POST)
	@ResponseBody
	public String addRoleUser(HttpServletRequest request,String roleId,String userIds){
		if(StringUtil.isNotEmpty(roleId) && StringUtil.isNotEmpty(userIds)){
			List<SysRoleUser> lstUser = new ArrayList<SysRoleUser>();
			String[] userTmp = userIds.split(",");
			for (String userId : userTmp) {
				SysRoleUser roleUser = new SysRoleUser();
				roleUser.setId(StringUtil.GenerateIdentifier());
				roleUser.setRoleId(roleId);
				roleUser.setUserId(userId);
				lstUser.add(roleUser);
			}
			sysRoleService.batchSaveRoleUser(lstUser);
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
}

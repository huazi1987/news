package com.news.cms.sys.menu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.news.cms.sys.menu.service.SysMenuService;
import com.news.common.base.BaseController;
import com.news.common.json.TreeJson;
import com.news.common.json.model.JSONResult;
import com.news.common.json.model.TreeNode;
import com.news.common.util.CollectionUtil;
import com.news.common.util.CommonStatic;
import com.news.common.util.LoginUtil;
import com.news.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ushareit.news.cms.sys.menu.model.SysMenu;
import com.ushareit.news.cms.sys.menu.service.SysMenuService;
import com.ushareit.news.common.base.BaseController;
import com.ushareit.news.common.json.TreeJson;
import com.ushareit.news.common.json.model.JSONResult;
import com.ushareit.news.common.json.model.TreeNode;
import com.ushareit.news.common.util.CollectionUtil;
import com.ushareit.news.common.util.CommonStatic;
import com.ushareit.news.common.util.LoginUtil;
import com.ushareit.news.common.util.StringUtil;

@RequestMapping("/sysMenu")
@Controller
public class SysMenuController extends BaseController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 列表跳转
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @return
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "sysMenu/sysMenuList";
	}
	
	/**
	 * 菜单管理列表数据加载
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-5-4
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request){
		List<SysMenu> lstNode = sysMenuService.findChildAll(CommonStatic.MENU_ROOT);
		if(lstNode.size()>0){
//			List<SysMenu> lstTreeNode = TreeGridJson.formatTreeGrid(lstNode,CommonStatic.MENU_ROOT);
			return jacksonMapper.writeValueAsString(lstNode);
		}
		return null;
	}
	
	/**
	 * 新增菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param request
	 * @param sysMenu
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,SysMenu sysMenu){
		if(StringUtil.isNotEmpty(sysMenu)){
			sysMenu.setMenuId(StringUtil.GenerateIdentifier());
			if(StringUtil.isNotEmpty(sysMenu.getMenuPid())){
				SysMenu parent = sysMenuService.findById(sysMenu.getMenuPid());
				sysMenu.setMenuPath(parent.getMenuPath()+","+sysMenu.getMenuId());
				sysMenu.setLevel(parent.getLevel()+1);
			}else{
				sysMenu.setMenuPid(CommonStatic.MENU_ROOT);
				sysMenu.setMenuPath(sysMenu.getMenuPid()+","+sysMenu.getMenuId());
				sysMenu.setLevel(1);
			}
			sysMenu.setStatus(CommonStatic.TRUE);
			sysMenu.setCreateTime(DateUtil.getDateTime());
			sysMenuService.insert(sysMenu);
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 编辑菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param request
	 * @param sysMenu
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,SysMenu sysMenu){
		if(StringUtil.isNotEmpty(sysMenu)){
			if(StringUtil.isNotEmpty(sysMenu.getMenuPid())){
				SysMenu parent = sysMenuService.findById(sysMenu.getMenuPid());
				sysMenu.setMenuPath(parent.getMenuPath()+","+sysMenu.getMenuId());
				sysMenu.setLevel(parent.getLevel()+1);
			}else{
				sysMenu.setMenuPid(CommonStatic.MENU_ROOT);
				sysMenu.setMenuPath(sysMenu.getMenuPid()+","+sysMenu.getMenuId());
				sysMenu.setLevel(1);
			}
			sysMenuService.update(sysMenu);
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 删除当前菜单及子菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,String id){
		if(StringUtil.isNotEmpty(id)){
			sysMenuService.delete(id);
		}
		return jacksonMapper.writeValueAsString(JSONResult.Success("操作成功"));
	}
	
	/**
	 * 左侧导航树
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-5-4
	 * @param treeId
	 * @return
	 */
	@RequestMapping(value = "/menuTreeList", method = RequestMethod.GET)
	@ResponseBody
	public String menuTreeList(HttpServletRequest request,String treeId){
		List<TreeNode> lstNode = sysMenuService.findRoleMenuChildByUserId(treeId, LoginUtil.getCurrentUserId());
		if(CollectionUtil.isNotEmpty(lstNode)){
			List<TreeNode> lstTreeNode = TreeJson.formatTree(lstNode);
			return jacksonMapper.writeValueAsString(lstTreeNode);
		}
		return null;
	}
	
	/**
	 * 下拉框异步调用
	 * @Description: 
	 * @author wanghz
	 * @date 2017年11月20日
	 * @param request
	 * @param treeId
	 * @return
	 */
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public String select(HttpServletRequest request,String treeId){
		if(StringUtil.isEmpty(treeId)) {
			treeId = CommonStatic.MENU_ROOT;
		}
		List<TreeNode> lstNode = sysMenuService.findSysMenuByPid(treeId);
		
		return jacksonMapper.writeValueAsString(lstNode);
	}
}

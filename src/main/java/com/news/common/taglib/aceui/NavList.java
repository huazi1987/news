package com.news.common.taglib.aceui;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.news.cms.sys.menu.service.SysMenuService;
import com.news.common.json.model.TreeNode;
import com.news.common.taglib.base.BaseTaglib;
import com.news.common.util.CommonStatic;
import com.news.common.util.LoginUtil;

public class NavList extends BaseTaglib {
	
	/**
	 * 基于ace的封装菜单导航
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			SysMenuService sysMenuService = (SysMenuService)webContext.getBean("sysMenuService");
			List<TreeNode> lstMenu = sysMenuService.findRoleMenuByUserId(CommonStatic.MENU_ROOT, LoginUtil.getCurrentUserId());
			out.print(navList(lstMenu));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
	private String navList(List<TreeNode> lstMenu)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<li class=\"\"><a href=\"#\" class=\"dropdown-toggle\"><i class=\"menu-icon fa fa-desktop\"></i><span class=\"menu-text\">UI &amp; Elements</span><b class=\"arrow fa fa-angle-down\"></b></a><b class=\"arrow\"></b><ul class=\"submenu\"><li class=\"\"><a href=\"nestable-list.html\"><i class=\"menu-icon fa fa-caret-right\"></i>Nestable Lists</a><b class=\"arrow\"></b></li><li class=\"\"><a href=\"#\" class=\"dropdown-toggle\"><i class=\"menu-icon fa fa-caret-right\"></i>Three Level Menu<b class=\"arrow fa fa-angle-down\"></b></a><b class=\"arrow\"></b><ul class=\"submenu\"><li class=\"\"><a href=\"#\"><i class=\"menu-icon fa fa-leaf green\"></i>Item #1</a><b class=\"arrow\"></b></li><li class=\"\"><a href=\"#\" class=\"dropdown-toggle\"><i class=\"menu-icon fa fa-pencil orange\"></i>4th level<b class=\"arrow fa fa-angle-down\"></b></a><b class=\"arrow\"></b><ul class=\"submenu\"><li class=\"\"><a href=\"#\"><i class=\"menu-icon fa fa-plus purple\"></i>Add Product</a><b class=\"arrow\"></b></li><li class=\"\"><a href=\"#\"><i class=\"menu-icon fa fa-eye pink\"></i>View Products</a><b class=\"arrow\"></b></li></ul></li></ul></li></ul></li>");
//		if(lstMenu!=null){
//			for (int i = 0; i < lstMenu.size(); i++) {
//				TreeNode menu = lstMenu.get(i);
//				sb.append("<div class=\"easyui-panel\" data-options=\"title:'"+menu.getText()+"',collapsible:true\">");
//				sb.append("<ul id=\"menuTree"+(i+1)+"\" class=\"easyui-tree\" data-options=\"url:'menu/menuTreeList?treeId="+menu.getId()+"',method:'get',animate:true\"></ul>");
//				sb.append("</div>");
//				if((i+1)!=lstMenu.size()){
//					sb.append("<br>");
//				}
//			}
//		}
		log.info(sb.toString());
		return sb.toString();
	}
}

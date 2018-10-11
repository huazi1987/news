package com.news.model;

import com.news.common.base.BaseModel;

/**
 * @Description: 角色菜单关联
 * @author 王慧智
 * @date 2015-09-19
 */
public class SysRoleMenu extends BaseModel<SysRoleMenu> {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String roleId;
	private String menuId;
	
	public SysRoleMenu() {
	}

	/**
	 * @Description: 
	 */
	public String getId() {
		return id;
	}

	/**
	 * @Description: 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @Description: 
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @Description: 
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @Description: 
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @Description: 
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}

package com.news.cms.sys.role.model;

import com.ushareit.news.common.base.BaseModel;

/**
 * @Description: 角色用户关联
 * @author 王慧智
 * @date 2015-09-19
 */
public class SysRoleUser extends BaseModel<SysRoleUser> {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String roleId;
	private String userId;
	
	public SysRoleUser() {
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
	public String getUserId() {
		return userId;
	}

	/**
	 * @Description: 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

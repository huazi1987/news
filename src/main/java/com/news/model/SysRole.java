package com.news.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.news.common.base.BaseModel;
import com.news.common.json.serializer.JsonDateSerializer;

import java.util.Date;

/**
 * @Description: 角色信息
 * @author 王慧智
 * @date 2016-04-08
 */
public class SysRole extends BaseModel<SysRole> {

	private static final long serialVersionUID = 1L;
	
	private String roleId;
	private String roleName;
	private Integer status;
	private Date createTime;
	
	public SysRole() {
	}

	/**
	 * @Description: 主键ID
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @Description: 主键ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @Description: 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @Description: 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @Description: 角色状态(1启用，0禁用)
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @Description: 角色状态(1启用，0禁用)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @Description: 创建日期
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @Description: 创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

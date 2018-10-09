package com.news.cms.sys.user.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.news.common.base.BaseModel;
import com.news.common.json.serializer.JsonDateSerializer;
import com.ushareit.news.common.base.BaseModel;
import com.ushareit.news.common.json.serializer.JsonDateSerializer;

/**
 * @Description: 系统用户
 * @author 王慧智
 * @date 2016-04-08
 */
public class SysUser extends BaseModel<SysUser> {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String userName;
	private String userLogin;
	private String password;
	private String salt;
	private String email;
	private Integer rank;
	private Integer status;
	private Date createTime;
	
	//逻辑变量
	private String roleIds;		//角色编号
	
	public SysUser() {
	}

	/**
	 * @Description: 用户ID号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @Description: 用户ID号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @Description: 用户姓名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @Description: 用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @Description: 登录帐号
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @Description: 登录帐号
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	/**
	 * @Description: 用户密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @Description: 用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @Description: 密码盐
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @Description: 密码盐
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * @Description: 邮箱地址
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @Description: 邮箱地址
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @Description: 排序
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @Description: 排序
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	/**
	 * @Description: 状态(1启用，0禁用)
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @Description: 状态(1启用，0禁用)
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

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}

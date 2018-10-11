package com.news.mapper;

import com.news.common.base.BaseMapper;
import com.news.model.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends BaseMapper<SysUser> {
	
	/**
	 * 根据用户登录帐号查询用户信息
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月22日
	 * @param userLogin
	 * @return
	 */
	SysUser findByUserLogin(String userLogin);
	/**
	 * 根据角色编号查询待选用户列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月24日
	 * @param roleId
	 * @return
	 */
	List<SysUser> findOptUserByRoleId(String roleId);
	/**
	 * 根据角色编号查询角色用户列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param param
	 * @return
	 */
	List<SysUser> findRoleUserByRoleId(Map<String, Object> param);
	/**
	 * 根据角色编号查询角色用户总数
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param roleId
	 * @return
	 */
	int findRoleUserCountByRoleId(String roleId);
	
}

package com.news.mapper;

import com.news.common.base.BaseMapper;
import com.news.model.SysRole;
import com.news.model.SysRoleUser;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
	
	/**
	 * 批量保存角色用户
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param list
	 */
	void batchSaveRoleUser(List<SysRoleUser> list);
	/**
	 * 根据角色编号删除角色用户
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param roleId
	 */
	void deleteRoleUser(String roleId);
	/**
	 * 根据用户编号删除角色用户
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param param
	 */
	void deleteRoleUserByUserId(String userId);
	
	/**
	 * 根据用户编号获得所有角色ID
	 * @Description: 
	 * @author wanghz
	 * @date 2018年1月29日
	 * @param userId
	 * @return
	 */
	String findRoleIdsByUserId(String userId);
}

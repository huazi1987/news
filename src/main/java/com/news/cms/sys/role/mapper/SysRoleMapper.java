package com.news.cms.sys.role.mapper;

import java.util.List;

import com.news.cms.sys.role.model.SysRoleUser;
import com.ushareit.news.cms.sys.role.model.SysRole;
import com.ushareit.news.cms.sys.role.model.SysRoleUser;
import com.ushareit.news.common.base.BaseMapper;

public interface SysRoleMapper extends BaseMapper<SysRole>{
	
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

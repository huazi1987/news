package com.news.cms.sys.role.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.news.cms.sys.role.mapper.SysRoleMapper;
import com.news.cms.sys.role.model.SysRoleUser;
import com.news.common.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ushareit.news.cms.sys.menu.mapper.SysMenuMapper;
import com.ushareit.news.cms.sys.role.mapper.SysRoleMapper;
import com.ushareit.news.cms.sys.role.model.SysRole;
import com.ushareit.news.cms.sys.role.model.SysRoleMenu;
import com.ushareit.news.cms.sys.role.model.SysRoleUser;
import com.ushareit.news.cms.sys.user.mapper.SysUserMapper;
import com.ushareit.news.cms.sys.user.model.SysUser;
import com.ushareit.news.common.base.BaseService;

@Service
public class SysRoleService extends BaseService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 新增角色信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-08
	 * @param sysRole
	 * @return
	 */
	@Transactional
	public int insert(SysRole sysRole,List<SysRoleMenu> lstMenu)
	{
		if(lstMenu!=null && lstMenu.size()>0){
			sysMenuMapper.batchSaveRoleMenu(lstMenu);
		}
		return sysRoleMapper.insert(sysRole);
	}
	
	/**
	 * 更新角色信息，值为null的不会更新
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param sysRole
	 * @return
	 */
	@Transactional
	public int update(SysRole sysRole,List<SysRoleMenu> lstMenu)
	{
		sysMenuMapper.deleteRoleMenu(sysRole.getRoleId());
		if(lstMenu!=null && lstMenu.size()>0){
			sysMenuMapper.batchSaveRoleMenu(lstMenu);
		}
		return sysRoleMapper.update(sysRole);
	}
	
	/**
	 * 根据ID删除指定角色信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param sysRole
	 * @return
	 */
	@Transactional
	public int delete(String id)
	{
		sysMenuMapper.deleteRoleMenu(id);
		sysRoleMapper.deleteRoleUser(id);
		return sysRoleMapper.delete(id);
	}
	
	/**
	 * 根据ID查询指定角色信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param id
	 * @return
	 */
	public SysRole findById(String id)
	{
		return sysRoleMapper.findById(id);
	}
	
	/**
	 * 根据条件查询角色信息分页列表
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param startRow
	 * @param pageSize
	 * @param sysRole
	 * @return
	 */
	public List<SysRole> findSysRoleByPage(int startRow,int pageSize,SysRole sysRole)
	{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("startRow", startRow);
		param.put("pageSize", pageSize);
		param.put("sysRole", sysRole);
		return sysRoleMapper.findAll(param);
	}
	
	/**
	 * 根据条件查询角色信息列表总数
	 * @Description: 
	 * @author 王慧智
	 * @date 2015-09-19
	 * @param sysRole
	 * @return
	 */
	public int findAllCount(SysRole sysRole)
	{
		return sysRoleMapper.findAllCount(sysRole);
	}
	
	/**
	 * 根据角色编号查询待选用户列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月24日
	 * @param roleId
	 * @return
	 */
	public List<SysUser> findOptUserByRoleId(String roleId)
	{
		return sysUserMapper.findOptUserByRoleId(roleId);
	}
	
	/**
	 * 根据角色编号查询角色用户总数
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param roleId
	 * @return
	 */
	public int findRoleUserCountByRoleId(String roleId)
	{
		return sysUserMapper.findRoleUserCountByRoleId(roleId);
	}
	
	/**
	 * 根据角色编号查询角色用户列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月25日
	 * @param startRow
	 * @param pageSize
	 * @param roleId
	 * @return
	 */
	public List<SysUser> findRoleUserByRoleId(Integer startRow,Integer pageSize,String roleId)
	{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("startRow", startRow);
		param.put("pageSize", pageSize);
		param.put("roleId", roleId);
		return sysUserMapper.findRoleUserByRoleId(param);
	}
	
	/**
	 * 批量保存角色用户
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param list
	 */
	public void batchSaveRoleUser(List<SysRoleUser> list)
	{
		sysRoleMapper.batchSaveRoleUser(list);
	}
	
	/**
	 * 根据用户编号删除角色用户
	 * @Description: 
	 * @author wanghz
	 * @date 2018年1月29日
	 * @param userId
	 */
	public void deleteRoleUserByUserId(String userId)
	{
		sysRoleMapper.deleteRoleUserByUserId(userId);
	}
	
	/**
	 * 根据用户编号获得所有角色ID
	 * @Description: 
	 * @author wanghz
	 * @date 2018年1月29日
	 * @param userId
	 * @return
	 */
	public String findRoleIdsByUserId(String userId)
	{
		return sysRoleMapper.findRoleIdsByUserId(userId);
	}
}

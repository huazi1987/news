package com.news.mapper;

import com.news.common.base.BaseMapper;
import com.news.common.json.model.TreeNode;
import com.news.model.SysMenu;
import com.news.model.SysRoleMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 查询所有菜单信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-12-13
	 * @return
	 */
	List<SysMenu> findSysMenuAll();
	/**
	 * 根据菜单父ID查询所有状态为启用的菜单列表并返回树形结构
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-12-13
	 * @param menuPid
	 * @return
	 */
	List<TreeNode> findTreeList(String menuPid);
	/**
	 * 根据菜单父ID查询所有子菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-12-13
	 * @param menuPid
	 * @return
	 */
	List<SysMenu> findChildAll(String menuPid);
	/**
	 * 批量保存角色功能菜单
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param list
	 */
	void batchSaveRoleMenu(List<SysRoleMenu> list);
	/**
	 * 根据角色编号删除角色功能菜单
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param roleId
	 */
	void deleteRoleMenu(String roleId);
	/**
	 * 根据角色编号查询功能菜单列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param roleId
	 * @return
	 */
	List<TreeNode> findMenuByRoleId(String roleId);
	/**
	 * 根据菜单编号和用户编号查询菜单列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月26日
	 * @param param
	 * @return
	 */
	List<TreeNode> findRoleMenuByUserId(Map<String, Object> param);
	/**
	 * 根据菜单编号和用户编号查询所有子菜单
	 * @Description:
	 * @author wanghz
	 * @date 2015年9月28日
	 * @param param
	 * @return
	 */
	List<TreeNode> findRoleMenuChildByUserId(Map<String, Object> param);
	
	/**
	 * 查询所有菜单信息
	 * @Description: 
	 * @author wanghz
	 * @date 2017年11月20日
	 * @param menuPid
	 * @return
	 */
	List<TreeNode> findSysMenuByPid(String menuPid);
}

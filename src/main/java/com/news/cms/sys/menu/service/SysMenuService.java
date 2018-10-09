package com.news.cms.sys.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.news.common.base.BaseService;
import com.news.common.json.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ushareit.news.cms.sys.menu.mapper.SysMenuMapper;
import com.ushareit.news.cms.sys.menu.model.SysMenu;
import com.ushareit.news.common.base.BaseService;
import com.ushareit.news.common.json.model.TreeNode;

@Service
public class SysMenuService extends BaseService {

	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	/**
	 * 新增系统菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-11
	 * @param sysMenu
	 * @return
	 */
	public int insert(SysMenu sysMenu)
	{
		return sysMenuMapper.insert(sysMenu);
	}
	
	/**
	 * 更新系统菜单，值为null的不会更新
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-11
	 * @param sysMenu
	 * @return
	 */
	public int update(SysMenu sysMenu)
	{
		return sysMenuMapper.update(sysMenu);
	}
	
	/**
	 * 根据ID删除指定系统菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-11
	 * @param sysMenu
	 * @return
	 */
	public int delete(String id)
	{
		return sysMenuMapper.delete(id);
	}
	
	/**
	 * 根据ID查询指定系统菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-11
	 * @param id
	 * @return
	 */
	public SysMenu findById(String id)
	{
		return sysMenuMapper.findById(id);
	}
	
	/**
	 * 根据条件查询系统菜单分页列表
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-11
	 * @param startRow
	 * @param pageSize
	 * @param sysMenu
	 * @return
	 */
	public List<SysMenu> findSysMenuByPage(Integer startRow,Integer pageSize,SysMenu sysMenu)
	{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("startRow", startRow);
		param.put("pageSize", pageSize);
		param.put("sysMenu", sysMenu);
		return sysMenuMapper.findAll(param);
	}
	
	/**
	 * 根据条件查询系统菜单列表总数
	 * @Description: 
	 * @author 王慧智
	 * @date 2016-04-11
	 * @param sysMenu
	 * @return
	 */
	public int findAllCount(SysMenu sysMenu)
	{
		return sysMenuMapper.findAllCount(sysMenu);
	}
	
	/**
	 * 查询所有菜单信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-12-13
	 * @return
	 */
	public List<SysMenu> findSysMenuAll()
	{
		return sysMenuMapper.findSysMenuAll();
	}
	/**
	 * 根据菜单父ID查询所有状态为启用的菜单列表并返回树形结构
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-12-13
	 * @param menuPid
	 * @return
	 */
	public List<TreeNode> findTreeList(String menuPid)
	{
		return sysMenuMapper.findTreeList(menuPid);
	}
	/**
	 * 根据菜单父ID查询所有子菜单
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-12-13
	 * @param menuPid
	 * @return
	 */
	public List<SysMenu> findChildAll(String menuPid)
	{
		return sysMenuMapper.findChildAll(menuPid);
	}
	
	/**
	 * 根据角色编号查询功能菜单列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param roleId
	 * @return
	 */
	public List<TreeNode> findMenuByRoleId(String roleId)
	{
		return sysMenuMapper.findMenuByRoleId(roleId);
	}
	/**
	 * 根据菜单编号和用户编号查询菜单列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月26日
	 * @param menuPid
	 * @param userId
	 * @return
	 */
	public List<TreeNode> findRoleMenuByUserId(String menuPid,String userId)
	{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("menuPid", menuPid);
		param.put("userId", userId);
		return sysMenuMapper.findRoleMenuByUserId(param);
	}
	/**
	 * 根据菜单编号和用户编号查询所有子菜单
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月28日
	 * @param param
	 * @return
	 */
	public List<TreeNode> findRoleMenuChildByUserId(String menuPid,String userId)
	{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("menuPid", menuPid);
		param.put("userId", userId);
		return sysMenuMapper.findRoleMenuChildByUserId(param);
	}
	
	/**
	 * 查询所有菜单信息
	 * @Description: 
	 * @author wanghz
	 * @date 2017年11月20日
	 * @param menuPid
	 * @return
	 */
	public List<TreeNode> findSysMenuByPid(String menuPid)
	{
		return sysMenuMapper.findSysMenuByPid(menuPid);
	}
}

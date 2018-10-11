package com.news.service;

import com.news.common.base.BaseService;
import com.news.common.util.CommonStatic;
import com.news.common.util.DateUtil;
import com.news.common.util.EncryptUtil;
import com.news.common.util.StringUtil;
import com.news.mapper.SysUserMapper;
import com.news.model.SysRoleUser;
import com.news.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserService extends BaseService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 新增系统用户
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param sysUser
	 */
	@Transactional
	public void insert(SysUser sysUser)
	{
		sysUser.setUserId(StringUtil.GenerateIdentifier());
		sysUser.setStatus(CommonStatic.TRUE);
		sysUser.setCreateTime(DateUtil.getDateTime());
		sysUser.setSalt(StringUtil.getStochastic());
		sysUser.setPassword(EncryptUtil.encodeMD5(sysUser.getPassword()+sysUser.getSalt()));
		
		if(StringUtil.isNotEmpty(sysUser.getRoleIds())) {
			String[] roles = sysUser.getRoleIds().split(",");
			List<SysRoleUser> lstRoleUser = new ArrayList<>();
			SysRoleUser roleUser = null;
			for (int i = 0; i < roles.length; i++) {
				roleUser = new SysRoleUser();
				roleUser.setId(StringUtil.GenerateIdentifier());
				roleUser.setUserId(sysUser.getUserId());
				roleUser.setRoleId(roles[i]);
				lstRoleUser.add(roleUser);
			}
			if(lstRoleUser.size()>0) {
				sysRoleService.batchSaveRoleUser(lstRoleUser);
			}
		}
		sysUserMapper.insert(sysUser);
	}
	
	/**
	 * 更新系统用户信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param sysUser
	 */
	@Transactional
	public void update(SysUser sysUser)
	{
		if(StringUtil.isNotEmpty(sysUser.getRoleIds())) {
			String[] roles = sysUser.getRoleIds().split(",");
			List<SysRoleUser> lstRoleUser = new ArrayList<>();
			SysRoleUser roleUser = null;
			for (int i = 0; i < roles.length; i++) {
				roleUser = new SysRoleUser();
				roleUser.setId(StringUtil.GenerateIdentifier());
				roleUser.setUserId(sysUser.getUserId());
				roleUser.setRoleId(roles[i]);
				lstRoleUser.add(roleUser);
			}
			if(lstRoleUser.size()>0) {
				sysRoleService.batchSaveRoleUser(lstRoleUser);
			}
		}
		sysUserMapper.update(sysUser);
	}
	
	@Transactional
	public void delete(String userId)
	{
		if(StringUtil.isNotEmpty(userId)) {
			sysRoleService.deleteRoleUserByUserId(userId);
			sysUserMapper.delete(userId);
		}
	}
	
	/**
	 * 根据ID获得系统用户信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-5
	 * @param userId
	 * @return
	 */
	public SysUser findById(String userId)
	{
		return sysUserMapper.findById(userId);
	}
	
	/**
	 * 获得系统用户分页列表
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月22日
	 * @param startRow
	 * @param pageSize
	 * @param sysUser
	 * @return
	 */
	public List<SysUser> findSysUserPage(Integer startRow,Integer pageSize,SysUser sysUser)
	{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("startRow", startRow);
		param.put("pageSize", pageSize);
		param.put("sysUser", sysUser);
		return sysUserMapper.findAll(param);
	}
	
	/**
	 * 根据查询条件返回用户列表总数
	 * @Description: 
	 * @author wanghz
	 * @date 2015年1月22日
	 * @param sysUser
	 * @return
	 */
	public int findAllCount(SysUser sysUser)
	{
		return sysUserMapper.findAllCount(sysUser);
	}
	
	/**
	 * 根据系统用户登录帐号获得该用户信息
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-6-7
	 * @param userLogin
	 * @return
	 */
	public SysUser findByUserLogin(String userLogin)
	{
		return sysUserMapper.findByUserLogin(userLogin);
	}
}

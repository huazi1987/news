package com.news.cms.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ushareit.news.cms.common.mapper.InitDataMapper;
import com.ushareit.news.cms.common.model.InviteDetail;

@Service
public class InitDataService {
	
	@Autowired
	private InitDataMapper initDataMapper;
	
	/**
     * 获取某天绑定记录
     * @Description: 
     * @author wanghz
     * @date 2018年8月15日
     * @param date
     * @return
     */
    public List<InviteDetail> getInviteDetailByDate(String date)
    {
    	return initDataMapper.getInviteDetailByDate(date);
    }
    
    /**
     * 按照北京时间查询绑定记录
     * @Description: 
     * @author wanghz
     * @date 2018年8月27日
     * @param date
     * @return
     */
    public List<InviteDetail> getInviteDetailByCSTDate(String date)
    {
    	return initDataMapper.getInviteDetailByCSTDate(date);
    }
    
    /**
     * 根据日期查询新用户参与答题记录
     * @Description: 
     * @author wanghz
     * @date 2018年8月24日
     * @param date
     * @return
     */
    public List<String> getNewUserAnswerByDate(String date)
    {
    	return initDataMapper.getNewUserAnswerByDate(date);
    }
    
    /**
     * 查询PK视频新增视频
     * @Description: 
     * @author wanghz
     * @date 2018年8月31日
     * @param pkDate
     * @return
     */
    public List<String> getVoteAddVideo(String pkDate)
    {
    	return initDataMapper.getVoteAddVideo(pkDate);
    }
    
    /**
     * 查询PK视频新增投票
     * @Description: 
     * @author wanghz
     * @date 2018年9月3日
     * @param param
     * @return
     */
    public Integer getVoteAddVote(Map<String,Object> param)
    {
    	return initDataMapper.getVoteAddVote(param);
    }
    
    /**
     * 查询PK视频新增投票人数
     * @Description: 
     * @author wanghz
     * @date 2018年9月3日
     * @param param
     * @return
     */
    public Set<String> getVoteAddVoteUser(Map<String,Object> param)
    {
    	return initDataMapper.getVoteAddVoteUser(param);
    }
    
    /**
     * 查询首次上传视频用户列表
     * @Description: 
     * @author wanghz
     * @date 2018年9月3日
     * @param date
     * @return
     */
    public List<String> getFirstVideoUser(String date)
    {
    	return initDataMapper.getFirstVideoUser(date);
    }
    
    /**
     * 查询首次投票用户列表
     * @Description: 
     * @author wanghz
     * @date 2018年9月3日
     * @param param
     * @return
     */
    public Set<String> getFirstVoteUser(Map<String,Object> param)
    {
    	return initDataMapper.getFirstVoteUser(param);
    }
    
    /**
     * 根据日期导出当日生成的邀请码
     * @Description: 
     * @author wanghz
     * @date 2018年9月5日
     * @param date
     * @return
     */
    public List<String> getInviteInfoByDate(String date)
    {
    	return initDataMapper.getInviteInfoByDate(date);
    }
    
    /**
     * 根据参数查询投票总数
     * @Description: 
     * @author wanghz
     * @date 2018年9月13日
     * @param param
     * @return
     */
    public Integer getVoteCountByThemeId(Map<String,Object> param)
    {
    	return initDataMapper.getVoteCountByThemeId(param);
    }
}

package com.news.cms.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ushareit.news.cms.common.mapper.LoggingMapper;

@Service
public class LoggingService {
	
	@Autowired
	private LoggingMapper loggingMapper;
	
	/**
	 * 根据视频编号查询审核人
	 * @Description: 
	 * @author wanghz
	 * @date 2018年9月10日
	 * @param videoId
	 * @return
	 */
	public String findAuditUserByVideoId(String videoId)
	{
		return loggingMapper.findAuditUserByVideoId(videoId);
	}
}

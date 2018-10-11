package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.Video;

import java.util.List;
import java.util.Map;


public interface VideoMapper extends BaseMapper<Video> {
	
	List<Video> findVideoList(Map<String, Object> params);

	int findVideoCount(Map<String, Object> params);
}

package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.News;

import java.util.List;
import java.util.Map;


public interface NewsMapper extends BaseMapper<News> {
	
	List<News> findNewsList(Map<String, Object> params);

	int findNewsCount(Map<String, Object> params);
}

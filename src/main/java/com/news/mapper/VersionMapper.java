package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.Version;

import java.util.List;
import java.util.Map;


public interface VersionMapper extends BaseMapper<Version> {
	
	Version findVersionByOSType(String osType);

	List<Version> findVideoList(Map<String, Object> params);

	int findVideoCount(Map<String, Object> params);

}

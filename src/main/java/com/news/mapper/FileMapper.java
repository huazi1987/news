package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.NFile;

import java.util.List;
import java.util.Map;


public interface FileMapper extends BaseMapper<NFile> {
	
	List<NFile> findFileList(Map<String, Object> params);

	int findFileCount(Map<String, Object> params);
}

package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.Version;


public interface VersionMapper extends BaseMapper<Version> {
	
	Version findVersionByOSType(String osType);

}

package com.news.service;

import com.news.common.base.BaseService;
import com.news.mapper.VersionMapper;
import com.news.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class VersionService extends BaseService {

	@Autowired
	private VersionMapper versionMapper;


	@Transactional
	public int insert(Version version){
		try {
			return versionMapper.insert(version);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Transactional
	public int update(Version version){
		try {
			return versionMapper.update(version);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Version findVersionByOSType(String osType){

		return versionMapper.findVersionByOSType(osType);
	}


}

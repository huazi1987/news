package com.news.service;

import com.news.common.base.BaseService;
import com.news.common.page.PageRecord;
import com.news.common.page.Pagination;
import com.news.mapper.VersionMapper;
import com.news.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VersionService extends BaseService {

	@Autowired
	private VersionMapper versionMapper;



	public Page<Version> queryVersionList(Pagination pagination){
		PageRecord<Version> result = null;
		try{
			Map<String, Object> page = new HashMap<>(2);
			page.put("start", pagination.getStart());
			page.put("pageSize", pagination.getPageSize());

			Map<String, Object> params = new HashMap<>();
			params.put("page", page);

			int count = versionMapper.findVersionCount(params);

			List<Version> list = versionMapper.findVersionList(params);
			result = new PageRecord<>(list,pagination,count);


		}catch (Exception e){
			e.printStackTrace();
		}
		return result;

	}

	public int insert(Version version){
		try {
			return versionMapper.insert(version);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int update(Version version){
		try {
			return versionMapper.update(version);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int delete(String id){
		try {
			return versionMapper.delete(id);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Version findVersionByOSType(String osType){

		return versionMapper.findVersionByOSType(osType);
	}


}

package com.news.service;

import com.news.common.base.BaseService;
import com.news.mapper.VerifyCodeMapper;
import com.news.model.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
public class VerifyCodeService extends BaseService {

	@Autowired
	private VerifyCodeMapper verifyCodeMapper;


	@Transactional
	public int insert(VerifyCode verifyCode){
		try {
			verifyCodeMapper.insert(verifyCode);
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public VerifyCode findByPhoneAndCode(String phone, String code){
		try {
			Map<String, String> params = new HashMap<>(2);
			params.put("phone", phone);
			params.put("code", code);
			return verifyCodeMapper.findByPhoneAndCode(params);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}

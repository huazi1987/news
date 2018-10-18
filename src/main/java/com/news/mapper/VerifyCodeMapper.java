package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.VerifyCode;

import java.util.Map;


public interface VerifyCodeMapper extends BaseMapper<VerifyCode> {

	VerifyCode findByPhoneAndCode(Map<String, String> params);
}

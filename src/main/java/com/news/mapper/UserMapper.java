package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.User;

import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
	
	User findUserByLoginNameAndPWD(Map<String, Object> params);

	User findUserByLoginName(String loginName);

	User findUserByPhone(String phone);


}

package com.news.service;

import com.news.common.base.BaseService;
import com.news.common.page.PageRecord;
import com.news.common.page.Pagination;
import com.news.mapper.UserMapper;
import com.news.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService extends BaseService {

	@Autowired
	private UserMapper userMapper;

	public Page<User> queryUserList(Pagination pagination){
		PageRecord<User> result = null;
		try{
			Map<String, Object> page = new HashMap<>(2);
			page.put("start", pagination.getStart());
			page.put("pageSize", pagination.getPageSize());

			Map<String, Object> params = new HashMap<>();
			params.put("page", page);

			int count = userMapper.findUserCount(params);

			List<User> list = userMapper.findUserList(params);
			result = new PageRecord<>(list,pagination,count);


		}catch (Exception e){
			e.printStackTrace();
		}
		return result;

	}

	@Transactional
	public int insert(User user){
		try {
			return userMapper.insert(user);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Transactional
	public int update(User user){
		try {
			return userMapper.update(user);
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public User findById(String userId)
	{
		return userMapper.findById(userId);
	}
	

	public User findUserByLoginNameAndPWD(String loginName, String password){
		Map<String, Object> params = new HashMap<>(2);
		params.put("loginName", loginName);
		params.put("password", password);

		return userMapper.findUserByLoginNameAndPWD(params);
	}

	public User findUserByLoginName(String loginName){
		try {
			return userMapper.findUserByLoginName(loginName);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public User findUserByPhone(String phone){
		try {
			return userMapper.findUserByPhone(phone);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}

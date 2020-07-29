package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.Friend;

import java.util.List;
import java.util.Map;

public interface FriendMapper extends BaseMapper<Friend> {
	
	List<Friend> findFeiendList(Map<String, Object> params);

	int findFriendCount(Map<String, Object> params);

	List<Friend> findApplyingList(Map<String, Object> params);

	int findApplyingCount(Map<String, Object> params);

	List<Friend> findPendingList(Map<String, Object> params);

	int findPendingCount(Map<String, Object> params);

	int updateFriend(Map<String, Object> params);
}

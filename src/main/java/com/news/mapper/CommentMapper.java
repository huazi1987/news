package com.news.mapper;


import com.news.common.base.BaseMapper;
import com.news.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentMapper extends BaseMapper<Comment> {
	
	List<Comment> findCommentList(Map<String, Object> params);

	int findCommentCount(Map<String, Object> params);
}

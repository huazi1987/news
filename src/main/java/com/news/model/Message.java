package com.news.model;

import com.news.common.base.BaseModel;

import java.util.Date;


public class Message extends BaseModel<Message> {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private String nickname;
	private String content;
	private int receiveUserId;
	private String replyUserNickname="";
	private Date createTime;
}

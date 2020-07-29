package com.news.model;

import com.news.common.base.BaseModel;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class Comment extends BaseModel<Comment> {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private String nickname="";
	private String avatarUrl="";
	private String content;
	private int replyId;
	private int replyUserId;
	private String replyUserNickname="";
	private Date createTime;
	private String groupId;
	private int groupType;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNickname() {
		if (StringUtils.isEmpty(nickname)){
			return "用户"+(userId << 8);
		}

		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(int replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyUserNickname() {
		if (StringUtils.isEmpty(replyUserNickname)){
			return "用户"+(replyUserId << 8);
		}

		return replyUserNickname;
	}

	public void setReplyUserNickname(String replyUserNickname) {
		this.replyUserNickname = replyUserNickname;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}

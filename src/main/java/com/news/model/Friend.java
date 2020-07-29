package com.news.model;

import com.news.common.base.BaseModel;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class Friend extends BaseModel<Friend> {

	private static final long serialVersionUID = 1L;

	private int userId;
	private int applyUserId;
	private boolean isAgree;
	private Date createTime;
	private String nickname;
	private String avatarUrl;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}

	public boolean isAgree() {
		return isAgree;
	}

	public void setAgree(boolean agree) {
		isAgree = agree;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}

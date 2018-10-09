package com.news.cms.common.model;

import java.util.Date;

public class AnswerUser {
	
	private String userId;
	private Integer source;
	private String inviteCode;
	private Integer inviteNo;
	private Long balance;
	private Integer isInvited;
	private Date createTime;
	
	public AnswerUser() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(Integer inviteNo) {
		this.inviteNo = inviteNo;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Integer getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(Integer isInvited) {
		this.isInvited = isInvited;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
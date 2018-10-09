package com.news.cms.common.model;

public class AnswerLuckdraw {
	
	private Integer bankId;
	private String userId;
	private Integer source;
	private Integer type;
	private String amount; 
	private Long createTime;
	private String detailsNo;
	
	public AnswerLuckdraw() {
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getDetailsNo() {
		return detailsNo;
	}

	public void setDetailsNo(String detailsNo) {
		this.detailsNo = detailsNo;
	}
}
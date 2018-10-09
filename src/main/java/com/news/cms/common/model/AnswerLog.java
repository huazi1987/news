package com.news.cms.common.model;

public class AnswerLog {
	
	private String bankId;
	private String userId;
	private Integer source;
	private Integer questionId;
	private String answer;
	private Integer language;
	private Integer isUseRevivalCard;
	private Long logTime;
	private Integer type;//答题日志类型： 1:H5，2：APP
	
	public AnswerLog() {
	}
	
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
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
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getLanguage() {
		return language;
	}
	public void setLanguage(Integer language) {
		this.language = language;
	}
	public Integer getIsUseRevivalCard() {
		return isUseRevivalCard;
	}
	public void setIsUseRevivalCard(Integer isUseRevivalCard) {
		this.isUseRevivalCard = isUseRevivalCard;
	}

	public Long getLogTime() {
		return logTime;
	}

	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}

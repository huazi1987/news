package com.news.model;

import com.news.common.base.BaseModel;

import java.util.Date;

public class VerifyCode extends BaseModel<VerifyCode> {

	private static final long serialVersionUID = 1L;
	
	private String phone;
	private String code;
	private Date createTime;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

package com.news.model;

import com.news.common.base.BaseModel;

import java.util.Date;


public class Version extends BaseModel<Version> {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private long version;
	private String osType;
	private Date createTime;
	private String config;
	private boolean isPass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean pass) {
		isPass = pass;
	}
}

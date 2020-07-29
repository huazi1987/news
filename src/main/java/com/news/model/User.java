package com.news.model;

import com.news.common.base.BaseModel;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class User extends BaseModel<User> {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String loginName;
	private String nickname="";
	private String address="";
	private String phone="";
	private Date createTime;
	private String avatarUrl="";
	private String weight;
	private String high;
	private String birth;
	private int isFinished;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickname() {
		if (StringUtils.isEmpty(nickname)){
			return "用户"+(id << 8);
		}
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(int isFinished) {
		this.isFinished = isFinished;
	}
}

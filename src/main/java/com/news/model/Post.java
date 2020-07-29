package com.news.model;

import com.news.common.base.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 帖子
 */
@Document
public class Post {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String title;
	private String content;
	private List<String> thumbnailUrls;
	private String theme;
	private int userId;
	private String nickname;
	private String avatarUrl;
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<String> getThumbnailUrls() {
		return thumbnailUrls;
	}

	public void setThumbnailUrls(List<String> thumbnailUrls) {
		this.thumbnailUrls = thumbnailUrls;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
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

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}

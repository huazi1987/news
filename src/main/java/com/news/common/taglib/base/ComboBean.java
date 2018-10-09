package com.news.common.taglib.base;

import java.util.List;

public class ComboBean {
	
	private String id;
	private String text;
	private String state;
	private String icon;
	private List<ComboBean> children;
	
	public ComboBean() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ComboBean> getChildren() {
		return children;
	}

	public void setChildren(List<ComboBean> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}

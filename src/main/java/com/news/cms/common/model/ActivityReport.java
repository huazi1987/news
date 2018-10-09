package com.news.cms.common.model;

public class ActivityReport {
	
	private String date;
	private String type;
	private String json;
	
	//业务变量
	private Integer total;	//总数
	private Integer stay;	//留存
	private Integer source;	//来源(1:茄子，2：Weshow)
	
	public ActivityReport() {
	}
	
	public ActivityReport(String date, String type, String json) {
		super();
		this.date = date;
		this.type = type;
		this.json = json;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStay() {
		return stay;
	}

	public void setStay(Integer stay) {
		this.stay = stay;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
}
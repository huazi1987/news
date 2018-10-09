package com.news.common.json.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.news.common.util.CommonStatic;

@JsonInclude(Include.NON_NULL)
public class JSONResult implements Serializable {

	/**
	 * @Description:
	 * @author 王慧智
	 * @date 2014-5-4
	*/
	private static final long serialVersionUID = 1L;
	
	private Integer resultCode;	//返回编码 1成功，0失败
	private Object result;		//返回信息
	private String resultMsg;	//返回信息
	
	public JSONResult() {
	}
	
	private JSONResult(Integer resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public JSONResult(Integer resultCode, Object result, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.result = result;
		this.resultMsg = resultMsg;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public static JSONResult Success() {
		return new JSONResult(CommonStatic.TRUE, "ok");
	}
	
	public static JSONResult Success(String resultMsg) {
		return new JSONResult(CommonStatic.TRUE, resultMsg);
	}
	
	public static JSONResult Failure(String resultMsg){
		return new JSONResult(CommonStatic.FALSE, resultMsg);
	}
	
	public static JSONResult Common(Integer resultCode,String resultMsg){
		return new JSONResult(resultCode, resultMsg);
	}
}

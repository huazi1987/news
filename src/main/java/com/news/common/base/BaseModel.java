package com.news.common.base;

import java.io.Serializable;

import com.news.common.json.JacksonMapper;

/**
 * POJO模型类
 * @Description:
 * @author wanghz
 * @date 2015年1月9日
 */
public class BaseModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return JacksonMapper.getInstance().writeValueAsString(this);
	}
}

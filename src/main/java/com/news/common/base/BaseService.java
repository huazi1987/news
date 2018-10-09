package com.news.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础的Service访问接口，要求所有Service接口继承
 * @Description:
 * @author wanghz
 * @date 2015年1月9日
 */
public abstract class BaseService{
	protected Logger log = LoggerFactory.getLogger(getClass());
}

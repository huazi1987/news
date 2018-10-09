package com.news.common.base;

import java.util.List;
import java.util.Map;

public interface BaseMapper <T extends BaseModel<T>> {

	/**
	 * 新增数据
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月19日
	 * @param t
	 * @return
	 */
	int insert(T t);

	/**
	 * 更新数据，值为null的不会更新
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月19日
	 * @param t
	 * @return
	 */
	int update(T t);

	/**
	 * 根据ID删除数据
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月19日
	 * @param id
	 * @return
	 */
	int delete(String id);

	/**
	 * 根据ID查询对象
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月19日
	 * @param id
	 * @return
	 */
	T findById(String id);
	
	/**
	 * 根据条件查询所有
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月19日
	 * @param param
	 * @return
	 */
	List<T> findAll(Map<String,Object> param);
	
	/**
	 * 根据条件查询总行数
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月19日
	 * @param t
	 * @return
	 */
	int findAllCount(T t);
}

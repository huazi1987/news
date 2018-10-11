package com.news.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBExt {
	
	private Logger log = LoggerFactory.getLogger(DBExt.class);
	
	public DBExt() {
	}
	
	/**
	 * 获取数据源
	 * @Description: 
	 * @author wanghz
	 * @date 2015年4月29日
	 * @param alias
	 * @param workConn
	 * @return
	 */
	public ComboPooledDataSource getDataSource() {  
		ComboPooledDataSource ds = null;
		try{
			ds = new ComboPooledDataSource();  
			ds.setDriverClass("com.mysql.jdbc.Driver");
//			ds.setJdbcUrl("jdbc:mysql://35.154.207.19:3306/weshow_cms?useUnicode=true&characterEncoding=UTF-8");
//			ds.setUser("cms_user");
//			ds.setPassword("a5rs3@1d3bn3");
			ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/news?useUnicode=true&characterEncoding=UTF-8");
			ds.setUser("root");
			ds.setPassword("111111");
			ds.setAcquireRetryAttempts(1);			//只尝试一次,默认会尝试30次
			ds.setMaxIdleTime(0);					//30秒内未使用则连接被丢弃。若为0则永不丢弃
			ds.setInitialPoolSize(1);				//每次初始化一个连接
			ds.setMinPoolSize(1);
			ds.setMaxPoolSize(1);
		} catch(Exception e) {
			log.error("创建数据源发生异常:"+e.getMessage());
		}
		return ds;
	}
	
	/**
	 * 关闭连接
	 * @Description: 
	 * @author wanghz
	 * @date 2015年4月28日
	 * @param conn
	 */
	public void closeConn(Connection conn){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			log.error("closeConn:"+e.getMessage());
		}
	}
	
	/**
	 * 关闭数据源
	 * @Description: 
	 * @author wanghz
	 * @date 2015年4月29日
	 * @param dataSource
	 * @param key
	 */
	public void closeDataSource(ComboPooledDataSource dataSource,String key){
		if(dataSource != null){
			dataSource.close();
		}
	}
	
	/**
	 * 关闭数据源
	 * @Description: 
	 * @author wanghz
	 * @date 2015年4月29日
	 * @param dataSource
	 */
	public void closeDataSource(ComboPooledDataSource dataSource){
		if(dataSource != null){
			dataSource.close();
		}
	}

}

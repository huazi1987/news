package com.news.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

public class RedisClient {

	private Logger logger = LoggerFactory.getLogger(RedisClient.class);

	private JedisCluster jedisCluster;
	private String host;
	private Integer port;
	
	public void init() {
        if (jedisCluster == null) {
        	jedisCluster = new JedisCluster(new HostAndPort(host,port));
        }
    }
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String set(String key, String value) {
		try {
			return jedisCluster.set(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return "";
	}

	public String get(String key) {
		try {
			return jedisCluster.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return "";
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 */
	public long del(String key) {
		try {
			return jedisCluster.del(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public long expire(String key, int seconds) {
		try {
			return jedisCluster.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 向集合添加元素
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long sadd(String key, String... value) {
		try {
			return jedisCluster.sadd(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 判断元素是否在集合中
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key, String member) {
		try {
			return jedisCluster.sismember(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return false;
	}

	/**
	 * 从集合中移除指定元素
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public long srem(String key, String... members) {
		try {
			return jedisCluster.srem(key, members);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 集合元素总个数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long scard(String key) {
		try {
			return jedisCluster.scard(key);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 集合随机取
	 * 
	 * @param key
	 * @param count
	 * @return
	 */
	public List<String> srandmember(String key, int count) {
		try {
			return jedisCluster.srandmember(key, count);
			
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return null;
	}
	
	public Set<String> smembers(String key) {
		try {
			return jedisCluster.smembers(key);
			
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return null;
	}
	
	

	/**
	 * 有序集合增加多值
	 * 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public long zadd(String key, Map<String, Double> scoreMembers) {
		try {
			return jedisCluster.zadd(key, scoreMembers);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 有序集合增加单值
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public long zadd(String key, Double score, String member) {
		try {
			return jedisCluster.zadd(key, score, member);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 有序集合移除指定值
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public long zrem(String key, String... members) {
		try {
			return jedisCluster.zrem(key, members);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	/**
	 * 范围内的正序有序集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key, long start, long end) {
		try {
			return jedisCluster.zrange(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	/**
	 * 分页查询有序集合
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		try {
			return jedisCluster.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	/**
	 * 范围内的倒序有序集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end) {
		try {
			return jedisCluster.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return new HashSet<String>();
	}

	/**
	 * 获取集合元素个数
	 * 
	 * @param key
	 * @return
	 */
	public Long zcard(String key) {
		return jedisCluster.zcard(key);
	}

	/**
	 * 获取分数
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		Double score = jedisCluster.zscore(key, member);
		if (score == null) {
			return Double.valueOf(0);
		}
		return score;
	}

	/**
	 * 获取排名
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) {
		Long rank = jedisCluster.zrevrank(key, member);
		if (rank == null) {
			return Long.valueOf(0);
		}
		return rank + 1;
	}

	/**
	 * 获取数据和score
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> zrevrangeByScores(String key, double max, double min, int offset, int count) {
		try {
			return jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	/**
	 * 倒序分页查询
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<String> zrevrangeByScore(String key, double min, double max, int offset, int count) {
		try {
			return jedisCluster.zrevrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	/**
	 * 对有序集合中指定成员的分数加上增量
	 * 
	 * @Description:
	 * @param key
	 * @param score(增量或减量值)
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Double zincrby(String key, double score, String member) {
		try {
			return jedisCluster.zincrby(key, score, member);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	/**
	 * 给指定map增加键值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long hset(String key, String field, String value) {
		try {
			return jedisCluster.hset(key, field, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	public Long hincrBy(String key, String field, long value) {
		try {
			return jedisCluster.hincrBy(key, field, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0L;
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		try {

			return jedisCluster.hget(key, field);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return "";
	}

	/**
	 * 指定key写入map
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public String hmset(String key, Map<String, String> hash) {
		try {
			return jedisCluster.hmset(key, hash);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return "";
	}

	public List<String> hmget(String key, String... fields) {
		try {
			return jedisCluster.hmget(key, fields);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	public long hdel(String key, String... fields) {
		try {
			return jedisCluster.hdel(key, fields);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0l;
	}

	public Map<String, String> hgetAll(String key){
		try {
			return jedisCluster.hgetAll(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return null;
	}

	public long publish(String channel, String message) {
		try {
			return jedisCluster.publish(channel, message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// jedisCluster.close();
		}
		return 0;
	}
	
	
	public List<String>  lrange(String key, int start, int end) {
		return jedisCluster.lrange(key, start, end);
	}
	
	
	public String ltrim(String key, int start, int end) {
		return jedisCluster.ltrim(key, start, end);
	}
	
	public static void main(String[] args) {
		JedisCluster jedisCluster = new JedisCluster(new HostAndPort("52.66.61.225",6379));
		System.out.println(jedisCluster.get("ss"));
	}
}

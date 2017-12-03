package com.oigbuy.jeesite.common.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
	private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	private static JedisPool jedisPool = JedisPools.getPool();
	
	private static  RedisUtils redisUtils=null;
	private RedisUtils(){
		
	}
	public synchronized static RedisUtils newRedisUtils(){
		if(null==redisUtils){
			redisUtils = new RedisUtils();
		}
		return redisUtils;
	}
    private  <T> T execute(Function<T, Jedis> fun) {
    	Jedis jedis = null;
        try {
        	jedis = jedisPool.getResource();
            return fun.callback(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
            	jedisPool.returnResource(jedis);
            }
        }
        return null;
    }
    
	/**
	 * 获取byte[]类型Key
	 * @param key
	 * @return
	 */
	public static byte[] getBytesKey(Object object){
		if(object instanceof String){
    		return StringUtils.getBytes((String)object);
    	}else{
    		return ObjectUtils.serialize(object);
    	}
	}
	
	/**
	 * byte[]型转换Object
	 * @param key
	 * @return
	 */
	public static Object toObject(byte[] bytes){
		return ObjectUtils.unserialize(bytes);
	}
	
	/**
	 * Object转换byte[]类型
	 * @param key
	 * @return
	 */
	public static byte[] toBytes(Object object){
    	return ObjectUtils.serialize(object);
	}
    
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public static Object getObject(String key) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = toObject(jedis.get(getBytesKey(key)));
				logger.debug("getObject {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObject {} = {}", key, value, e);
//			throw new RuntimeException(e.getMessage(),e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String setObject(String key, Object value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObject {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObject {} = {}", key, value, e);
			throw new RuntimeException(e.getMessage(),e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
    
	
	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	public static String delObject(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
			logger.debug("delObject {}", key);
		} catch (Exception e) {
			logger.warn("delObject {}", key);
			throw new RuntimeException(e.getMessage(),e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
    

    /**
     * 执行SET操作
     * 
     * @param key
     * @param value
     * @return
     */
    public  String set(final String key, final String value) {
        return this.execute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis e) {
                return e.set(key, value);
            }
        });
    }

    /**
     * 执行SET操作,并且设置生存时间
     * 
     * @param key
     * @param value
     * @return
     */
    public  String set(final String key, final String value, final Integer seconds) {
        return this.execute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis e) {
                String result = e.set(key, value);
                e.expire(key, seconds);
                return result;
            }
        });
    }

    /**
     * 设置生存时间
     * 
     * @param key
     * @param seconds
     * @return
     */
    public  Long expire(final String key, final Integer seconds) {
        return this.execute(new Function<Long, Jedis>() {
            @Override
            public Long callback(Jedis e) {
                return e.expire(key, seconds);
            }
        });
    }

    /**
     * 执行GET操作
     * 
     * @param key
     * @return
     */
    public String get(final String key) {
        return this.execute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis e) {
                return e.get(key);
            }
        });
    }

    /**
     * 执行DEL操作
     * 
     * @param key
     * @return
     */
    public Long del(final String key) {
        return this.execute(new Function<Long, Jedis>() {
            @Override
            public Long callback(Jedis e) {
                return e.del(key);
            }
        });
    }
    /**
     * 执行散列的存储
     * 
     * @param key
     * @param value
     * @return
     */
    public Long hset(final String key,final String field, final String value) {
        return this.execute(new Function<Long, Jedis>() {
            @Override
            public Long callback(Jedis e) {
                return e.hset(key, field, value);
            }
        });
    }
    
    /**
     * 获取散列中的值，传入key,和字段
     * 
     * @param key
     * @param value
     * @return
     */
    public String hget(final String key,final String field) {
        return this.execute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis e) {
                return e.hget(key, field);
            }
        });
    }
    /**
     * 执行散列的存储(可一次存入多个值)
     * 
     * @param key是唯一标识
     * @param kv（其中key是字段，value是值）
     * @return
     */
    public String hmset(final String key,final Map<String,String> kv) {
        return this.execute(new Function<String, Jedis>() {
            @Override
            public String callback(Jedis e) {
                return e.hmset(key, kv);
            }
        });
    }
    
    /**
     * 获取散列中的值，传入key,和字段列表
     * 
     * @param key是唯一表示
     * @param fields是要获取key下的字段的列表
     * @return 指定字段列表所对应的值的列表
     */
    public List<String> hmget(final String key,final String... fields) {
        return (List<String>) this.execute(new Function<List<String>, Jedis>() {
            @Override
            public List<String> callback(Jedis e) {
                return e.hmget(key, fields);
            }
        });
    }
    
    
    
    /**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static long setList(String key, List<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			int size = value.size();  
			String[] arr = (String[])value.toArray(new String[size]);
			result = jedis.rpush(key, arr);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static long listAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.rpush(key, value);
			logger.debug("listAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("listAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	

	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public static List<String> getList(String key) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(key)) {
				value = jedis.lrange(key, 0, -1);
				logger.debug("getList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getList {} = {}", key, value, e);
			throw new RuntimeException(e.getMessage());
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 释放资源
	 * @param jedis
	 * @param isBroken
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
    
    

	/**
	 * 获取递增数据
	 * 
	 * @param key
	 *            键
	 * @return Long
	 * */
    public Long incr(final String key) {
        return this.execute(new Function<Long, Jedis>() {
            @Override
            public Long callback(Jedis e) {
                return e.incr(key);
            }
        });
    }
}

class JedisPools{
	//读取配置文件
	private static final PropertiesLoader prop = new PropertiesLoader("redis.properties");
	private static JedisPool jedisPool=null;
	private static JedisPoolConfig config = null;
	static{
		if(null==config){
			config =new JedisPoolConfig();
		}
		config.setBlockWhenExhausted(Boolean.parseBoolean(prop.getProperty("redis.blockWhenExhausted")));
		 
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName(prop.getProperty("redis.evictionPolicyClassName"));
		 
		//是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(Boolean.parseBoolean(prop.getProperty("redis.jmxEnabled")));
		 
		//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
		config.setJmxNamePrefix(prop.getProperty("redis.jmxNamePrefix"));
		 
		//是否启用后进先出, 默认true
		config.setLifo(Boolean.parseBoolean(prop.getProperty("redis.lifo")));
		 
		//最大空闲连接数, 默认8个
		config.setMaxIdle(Integer.parseInt(prop.getProperty("redis.maxIdle")));
		 
		//最大连接数, 默认8个
		config.setMaxTotal(Integer.parseInt(prop.getProperty("redis.maxTotal")));
		 
		//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
		config.setMaxWaitMillis(Integer.parseInt(prop.getProperty("redis.maxWait")));
		 
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(Integer.parseInt(prop.getProperty("redis.minEvictableIdleTimeMillis")));
		 
		//最小空闲连接数, 默认0
		config.setMinIdle(Integer.parseInt(prop.getProperty("redis.minIdle")));
		 
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(Integer.parseInt(prop.getProperty("redis.numTestsPerEvictionRun")));
		 
		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		config.setSoftMinEvictableIdleTimeMillis(Integer.parseInt(prop.getProperty("redis.softMinEvictableIdleTimeMillis")));
		 
		//在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(Boolean.parseBoolean(prop.getProperty("redis.testOnBorrow")));
		 
		//在空闲时检查有效性, 默认false
		config.setTestWhileIdle(Boolean.parseBoolean(prop.getProperty("redis.testWhileIdle")));
		
		
	}
	public static JedisPool getPool(){
		if(null ==jedisPool){
			jedisPool= new JedisPool(config,prop.getProperty("redis.host"),Integer.parseInt(prop.getProperty("redis.port")),Integer.parseInt(prop.getProperty("redis.timeout")),prop.getProperty("redis.password"));
		}
		return jedisPool;
	}
	
}
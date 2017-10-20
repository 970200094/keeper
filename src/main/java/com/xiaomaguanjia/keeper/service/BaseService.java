package com.xiaomaguanjia.keeper.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.api.format.SerializationType;

/**
 * 基础服务类<br/>
 * 提供缓存获取等封装
 *
 * @author 王昌龙
 *
 */
public class BaseService {
	@Autowired
	private CacheFactory cacheFactory;

	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

	/**
	 * 获取memcache中的值
	 *
	 * @param key
	 *            缓存key
	 * @param clazz
	 *            转换类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		try {
			return (T) cacheFactory.getCache().get(key, SerializationType.PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存数据
	 *
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param exp
	 *            The exp value is passed along to memcached exactly as given,
	 *            and will be processed per the memcached protocol
	 *            specification: The actual value sent may either be Unix time
	 *            (number of seconds since January 1, 1970, as a 32-bit value),
	 *            or a number of seconds starting from current time. In the
	 *            latter case, this number of seconds may not exceed 60*60*24*30
	 *            (number of seconds in 30 days); if the number sent by a client
	 *            is larger than that, the server will consider it to be real
	 *            Unix time value rather than an offset from current time. (Also
	 *            note: a value of 0 means the given value should never expire.
	 *            The value is still susceptible to purging by memcached for
	 *            space and LRU (least recently used) considerations.)
	 */
	public void set(String key, Object value, int exp) {
		try {
			cacheFactory.getCache().set(key, exp, value, SerializationType.PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

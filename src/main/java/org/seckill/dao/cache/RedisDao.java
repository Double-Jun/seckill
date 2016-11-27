package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @auth mingjun chen
 * @date 2016/11/27
 */
public class RedisDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

	private JedisPool jedisPool;

	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	public Seckill getSeckill(long seckillId) {
		// reids操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				// 并没有实现内部序列化操作
				// get->byte[]->反序列化->Object<Seckill>
				// 采用第三方序列化方式 使用protostuff：pojo
				byte[] bytes = jedis.get(key.getBytes());
				// 从缓存中获取到
				if (bytes != null) {
					Seckill seckill = schema.newMessage();
					// seckill 反序列化
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public String putSeckill(Seckill seckill) {
		// set Object<Seckill> -> 序列化 ->byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout = 60 * 60; // 缓存一小时
				return jedis.setex(key.getBytes(), timeout, bytes);
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

}

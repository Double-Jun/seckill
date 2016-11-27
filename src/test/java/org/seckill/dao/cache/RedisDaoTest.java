package org.seckill.dao.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @auth mingjun chen
 * @date 2016/11/27
 */
// 配置spring和junit整合，junit启动是加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class RedisDaoTest {

	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void getSeckill() throws Exception {
		// get and put
		Long id = 1001L;
		Seckill seckill = redisDao.getSeckill(id);
		if (seckill == null) {
			seckill = seckillDao.queryById(id);
			if (seckill != null) {
				String s = redisDao.putSeckill(seckill);
				System.out.println(s);
				seckill = redisDao.getSeckill(id);
				System.out.println(seckill);
			}
		}
	}

	@Test
	public void putSeckill() throws Exception {

	}

}
package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
// 配置spring和junit整合，junit启动是加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SeckillDaoTest {

	@Resource
	private SeckillDao seckillDao;

	@Test
	public void reduceNumber() throws Exception {
		Date killTime = new Date();
		int reduceCount = seckillDao.reduceNumber(1000L, killTime);
		System.out.println(reduceCount);
	}

	@Test
	public void queryById() throws Exception {
		long id = 1000L;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}

	@Test
	public void queryList() throws Exception {
		List<Seckill> seckillList = seckillDao.queryList(0,100);
		for (Seckill seckill : seckillList) {
			System.out.println(seckill);
		}
	}

}
package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SuccessKilledDaoTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuccessKilledDaoTest.class);

	@Autowired
	private SuccessKilledDao successKilledDao;

	@Test
	public void insert() throws Exception {

	}

	@Test
	public void queryByIdWithSeckill() throws Exception {
		long seckillId = 1000L;
		long userPhone = 12345678900L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		LOGGER.info("successKilled={}", successKilled);
	}

}
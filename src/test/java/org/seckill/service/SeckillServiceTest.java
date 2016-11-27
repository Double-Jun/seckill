package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:spring/spring-dao.xml",
		"classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeckillServiceTest.class);

	@Autowired
	private SeckillService seckillService;

	@Test
	public void getSeckillList() throws Exception {
		List<Seckill> seckillList = seckillService.getSeckillList();
		LOGGER.info("list={}", seckillList);
	}

	@Test
	public void getById() throws Exception {
		long id = 1000L;
		Seckill seckill = seckillService.getById(id);
		LOGGER.info("seckill={}", seckill);
	}

	@Test
	public void testSeckillLogic() throws Exception {
		long id = 1001L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if (exposer.isExposed()) {
			LOGGER.info("exposer={}", exposer);
			long userPhone = 15178923659L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
				LOGGER.info("result={}", seckillExecution);
			} catch (RepeatKillException e1) {
				LOGGER.error(e1.getMessage());
			} catch (SeckillCloseException e2) {
				LOGGER.error(e2.getMessage());
			}
		} else {
			// 秒杀为开始
			LOGGER.warn("exposer={}", exposer);
		}

	}

}
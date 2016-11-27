package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;
	// md5盐值
	private final String slat = "abcdefghijklmnopqrstuvwxyz1234567890";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryList(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		// 优化点：缓存优化

		// 1.访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			// 2.缓存没有，访问数据库
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				// 3.放入redis
				redisDao.putSeckill(seckill);
			}
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		// 转化特定字符串
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill Data rewrite");
		}
		// 执行秒杀逻辑:1.减库存 2.插记录
		Date nowTime = new Date();
		try {
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				// 没有更新到记录
				throw new SeckillCloseException("seckill is closed");
			} else {
				// 记录购买行为
				SuccessKilled successKilled = new SuccessKilled(seckillId, userPhone);
				int insertCount = successKilledDao.insert(successKilled);
				// 唯一：seckillId, userPhone
				if (insertCount <= 0) {
					throw new RepeatKillException("seckill repeated");
				} else {
					SuccessKilled successKilled1 = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled1);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
}

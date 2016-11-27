package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”角度设计接口
 * 1）方法定义力度
 * 2）参数简介明了
 * 3）返回友好类型 return 类型/异常
 * @auth mingjun chen
 * @date 2016/11/19
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀商品记录
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 查询单个秒杀商品记录
	 * @param seckillId 秒杀商品id
	 * @return
	 */
	Seckill getById(long seckillId);

	/**
	 * 开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
	 * @param seckillId
	 * return Exposer
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀
	 * @param seckillId 商品id
	 * @param userPhone 用户手机号
	 * @param md5 校验
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException;
}

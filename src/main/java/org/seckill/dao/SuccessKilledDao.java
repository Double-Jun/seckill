package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
public interface SuccessKilledDao {

	/**
	 * 插入成功秒杀明细记录，可过滤重复
	 * @param successKilled 秒杀成功记录id
	 * @return 插入的行数
	 */
	int insert(SuccessKilled successKilled);

	/**
	 * 根据主键查询SuccessKilled,并携带Seckill信息
	 * @param seckillId 商品id
	 * @param userPhone 用户认证信息（手机号）
	 * @return 秒杀成功记录和秒杀商品信息
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}

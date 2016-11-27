package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId 商品id
	 * @param killTime 减库存时间
	 * @return 减少的库存记录
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

	/**
	 * 根据秒杀商品id查询商品
	 * @param seckillId 商品id
	 * @return 秒杀商品Seckill
	 */
	Seckill queryById(long seckillId);

	/**
	 * 根据偏移量查询秒杀商品列表
	 * @param offet 偏移量
	 * @param limit 期望获取记录条数
	 * @return 秒杀商品集合
	 */
	List<Seckill> queryList(@Param("offet") int offet,@Param("limit") int limit);
}

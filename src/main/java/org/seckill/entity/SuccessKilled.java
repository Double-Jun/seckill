package org.seckill.entity;

import java.util.Date;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
public class SuccessKilled {
	private long seckillId;
	private long userPhone;
	private short state;
	private Date createTime;
	// 变更
	private Seckill seckill;

	public SuccessKilled() {
	}

	public SuccessKilled(long seckillId, long userPhone) {
		this.seckillId = seckillId;
		this.userPhone = userPhone;
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override public String toString() {
		return "SuccessKilled{" +
				"seckillId=" + seckillId +
				", userPhone=" + userPhone +
				", state=" + state +
				", createTime=" + createTime +
				'}';
	}
}

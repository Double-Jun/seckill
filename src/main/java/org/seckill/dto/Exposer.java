package org.seckill.dto;

/**
 * 暴露秒杀地址dto
 * @auth mingjun chen
 * @date 2016/11/19
 */
public class Exposer {

	private boolean exposed; // 是否开启秒杀
	private String md5;     // 一种加密措施
	private long seckillId; // 秒杀商品id
	private long now;       // 系统当前时间（毫秒）
	private long start;     // 秒杀开始时间
	private long end;       // 秒杀结束时间

	public Exposer(boolean exposed, String md5, long seckillId) {
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean exposed, long seckillId) {
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override public String toString() {
		return "Exposer{" +
				"exposed=" + exposed +
				", md5='" + md5 + '\'' +
				", seckillId=" + seckillId +
				", now=" + now +
				", start=" + start +
				", end=" + end +
				'}';
	}
}

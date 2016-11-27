package org.seckill.exception;

/**
 * 秒杀关闭异常
 * @auth mingjun chen
 * @date 2016/11/19
 */
public class SeckillCloseException extends SeckillException {

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}
}

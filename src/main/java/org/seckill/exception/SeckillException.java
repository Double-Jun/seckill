package org.seckill.exception;

/**
 * @auth mingjun chen
 * @date 2016/11/19
 */
public class SeckillException extends RuntimeException {

	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}
}

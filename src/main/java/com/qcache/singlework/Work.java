package com.qcache.singlework;

import java.util.concurrent.CountDownLatch;

/**
 * @author guofeng.qin
 */
public class Work implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private CountDownLatch latch;
	private Object value;
	private Throwable exception;

	public Work(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	/**
	 * @return the latch
	 */
	public CountDownLatch getLatch() {
		return latch;
	}

	/**
	 * @param latch
	 *            the latch to set
	 */
	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the exception
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

}

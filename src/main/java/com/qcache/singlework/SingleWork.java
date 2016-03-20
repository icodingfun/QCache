package com.qcache.singlework;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author guofeng.qin
 */
public class SingleWork {
	private Lock lock = new ReentrantLock();
	private Map<Object, Work> group = new HashMap<>();

	public SingleWork() {

	}

	public Object doWork(Object key, Callable<Object> worker) throws RuntimeException {
		lock.lock();
		Work working = group.get(key);
		while (working != null) { // work with same is doing right now.
			lock.unlock();
			try {
				working.getLatch().await();
			} catch (InterruptedException e) {
				e.printStackTrace();
				working = group.get(key);
				continue;
			}
			Throwable exception = working.getException();
			if (exception != null) {
				throw new RuntimeException(exception);
			}
			return working.getValue();
		}

		working = new Work(new CountDownLatch(1));
		group.put(key, working);
		lock.unlock();

		Object result = null;
		Throwable exception = null;
		try {
			result = worker.call();
		} catch (Throwable e) {
			exception = e;
		} finally {
			working.setValue(result);
			working.setException(exception);
			working.getLatch().countDown();
		}
		lock.lock();
		group.remove(key);
		lock.unlock();

		if (exception != null) {
			throw new RuntimeException(exception);
		}

		return result;
	}
}

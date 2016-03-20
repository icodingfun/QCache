package com.qcache.example;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author guofeng.qin
 */
public class LogicThread {

	private static Executor executor = Executors.newSingleThreadExecutor();

	public static Executor getExecutor() {
		return executor;
	}
}

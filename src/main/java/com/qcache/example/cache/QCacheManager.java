package com.qcache.example.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qcache.QCache;
import com.qcache.example.LogicThread;

/**
 * @author guofeng.qin
 */
@Configuration
public class QCacheManager {

	private static QCache cache = new QCache(LogicThread.getExecutor(), true);

	@Bean
	public QCache getQCache() {
		return cache;
	}
}

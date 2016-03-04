package com.qcache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import com.qcache.ehcache.EhCacheSupport;
import com.qcache.entity.Entity;

/**
 * Created by hunter on 3/2/16.
 */
public class QCache {
	private boolean autoSave;
	private Executor bgsaver = null;
	private ICache cache = null;
	private boolean saving = false;

	private Executor callbackExecutor = null;
	private Executor cacheExecutor = Executors.newWorkStealingPool(20);

	public QCache(Executor callbackExecutor, boolean autoSave) {
		this.callbackExecutor = callbackExecutor;
		this.autoSave = autoSave;
		cache = EhCacheSupport.getInstance();
		startBGSaver();
	}

	private void startBGSaver() {
		if (autoSave) {
			bgsaver = Executors.newSingleThreadExecutor();
			bgsaver.execute(() -> {
				Thread.currentThread().setName("DB-BGSAVER");
			});
			saving = true;
			bgsaver.execute(() -> {
				while (saving) {
					QCache.this.saveCache();
					try {
						TimeUnit.MINUTES.sleep(5);// 5 min
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void saveCache() {
		List<Object> keys = cache.allKeys();
		if (keys != null) {
			Map<Object, Object> all = cache.getAll(keys);
			if (all != null) {
				for (Object obj : all.values()) {
					if (obj instanceof Entity) {
						Entity entity = (Entity) obj;
						entity.saveToDB();
					}
				}
			}
		}
	}

	public <T> T get(Object key) {
		return cache.get(key);
	}

	public <T> void put(Object key, T value) {
		cache.put(key, value);
	}

	public <T> void get(Object key, Function<Object, T> ifAbsent, Consumer<T> callback) {
		T value = get(key);
		if (value != null) { // cache hit
			callback.accept(value);
		} else { // cache absent
			cacheExecutor.execute(() -> {
				T v = cache.get(key, ifAbsent);
				callbackExecutor.execute(() -> {
					callback.accept(v);
				});
			});
		}
	}
}

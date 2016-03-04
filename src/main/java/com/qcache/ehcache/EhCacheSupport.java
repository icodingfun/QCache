package com.qcache.ehcache;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.qcache.ICache;
import com.qcache.singlework.SingleWork;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author guofeng.qin
 */
public class EhCacheSupport implements ICache {
	private final static String DEFAULT_CACHE_NAME = "qcache";

	private static class InnerHolder {
		private static final EhCacheSupport instance = new EhCacheSupport();
	}

	private EhCacheSupport() {
		URL cfgUrl = EhCacheSupport.class.getResource("./ehcache.xml");
		cacheManager = CacheManager.newInstance(cfgUrl);
		defaultCache = cacheManager.getCache(DEFAULT_CACHE_NAME);
		singleWork = new SingleWork();
	}

	public static final EhCacheSupport getInstance() {
		return InnerHolder.instance;
	}

	private CacheManager cacheManager;
	private Cache defaultCache;
	private SingleWork singleWork;

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(Object key) {
		Element element = defaultCache.get(key);
		if (element != null) {
			return ((V) element.getObjectValue());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(Object key, Function<Object, V> setIfNotExist) {
		Element element = defaultCache.get(key);
		if (element == null) {
			return (V) singleWork.doWork(key, () -> {
				Element check = defaultCache.get(key); // double check
				if (check != null) {
					return check.getObjectValue();
				}

				V value = setIfNotExist.apply(key);
				System.out.println("put key..." + (defaultCache.get(key) == null));
				put(key, value);
				return value;
			});
		}
		return (V) element.getObjectValue();
	}

	@Override
	public <T> void put(Object key, T value) {
		Element element = new Element(key, value);
		defaultCache.put(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> allKeys() {
		return defaultCache.getKeys();
	}

	@Override
	public Map<Object, Object> getAll(List<Object> keys) {
		Map<Object, Element> all = defaultCache.getAll(keys);
		Map<Object, Object> returnMap = new HashMap<>();
		if (all != null) {
			for (Map.Entry<Object, Element> entry : all.entrySet()) {
				returnMap.put(entry.getKey(), entry.getValue().getObjectValue());
			}
		}
		return returnMap;
	}
}

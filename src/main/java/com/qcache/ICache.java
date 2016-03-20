package com.qcache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author guofeng.qin
 */
public interface ICache {

	Map<Object, Object> getAll(List<Object> keys);

	List<Object> allKeys();

	<V> V get(Object key);

	<V> V get(Object key, Function<Object, V> setIfNotExist);

	<T> void put(Object key, T value);
}

package com.stupid.method.util;

import java.util.HashMap;

/** 只是为了能链式调用..... **/
public class MapUtil<K, V> {

	HashMap<K, V> map;

	public MapUtil() {
		map = new HashMap<K, V>();

	}

	public MapUtil<K, V> put(K key, V value) {

		map.put(key, value);
		return this;
	}

	public HashMap<K, V> getHashMap() {
		return this.map;
	}

	public MapUtil<K, V> clear() {
		this.map.clear();

		return this;
	}

	public MapUtil<K, V> putAll(HashMap<K, V> map) {
		this.map.putAll(map);

		return this;
	}

}

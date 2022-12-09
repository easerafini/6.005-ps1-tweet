package twitter;

import java.util.HashMap;

public class MyHashMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		return super.put((K) key.toString().toLowerCase(), value);
	}

	@Override
	public V remove(Object key) {
		key = (key instanceof String) ? ((String) key).toLowerCase() : key;
		return super.remove(key);
	}

	@Override
	public boolean containsKey(Object key) {
		key = (key instanceof String) ? ((String) key).toLowerCase() : key;
		return super.containsKey(key);
	}

	@Override
	public V get(Object key) {
		key = (key instanceof String) ? ((String) key).toLowerCase() : key;
		return super.get(key);
	}
}

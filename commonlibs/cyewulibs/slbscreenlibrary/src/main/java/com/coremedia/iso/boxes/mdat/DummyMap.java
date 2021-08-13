package com.coremedia.iso.boxes.mdat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A SortedSet that contains just one value.
 */
public class DummyMap<K, V> implements Map<K, V> {
    HashSet<K> keys = new HashSet<K>();
    V value;

    public DummyMap(V value) {
        this.value = value;
    }

    public Comparator<? super K> comparator() {
        return null;  // I don't have any
    }

    public void addKeys(K[] keys) {
        Collections.addAll(this.keys, keys);

    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.value == value;
    }

    @Override
    public V get(Object key) {
        return keys.contains(key) ? value : null;
    }

    @Override
    public V put(K key, V value) {
        assert this.value == value;
        keys.add(key);
        return this.value;
    }

    @Override
    public V remove(Object key) {
        V v = get(key);
        keys.remove(key);
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K k : m.keySet()) {
            assert m.get(k) == value;
            this.keys.add(k);
        }
    }

    @Override
    public void clear() {
        keys.clear();
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}

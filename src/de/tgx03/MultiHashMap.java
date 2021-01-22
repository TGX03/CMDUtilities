package de.tgx03;

import java.util.*;
import java.util.function.Consumer;

public class MultiHashMap<K, V> {
    
    private final int listSize;
    private final HashMap<K, List<V>> map;
    
    public MultiHashMap() {
        listSize = -1;
        map = new HashMap<>();
    }

    public MultiHashMap(int initialListCapacity) {
        listSize = initialListCapacity;
        map = new HashMap<>();
    }

    public MultiHashMap(int initialListCapacity, int initialMapCapacity) {
        listSize = initialListCapacity;
        map  = new HashMap<>(initialMapCapacity);
    }

    public MultiHashMap(int initialListCapacity, int initialMapCapacity, float loadFactor) {
        listSize = initialListCapacity;
        map  = new HashMap<>(initialMapCapacity, loadFactor);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(K key) {
        if (map.containsKey(key)) {
            return map.get(key).size() > 0;
        } else {
            return false;
        }
    }

    public V getElement(K key) {
        if (map.containsKey(key) && map.get(key).size() > 0) {
            return map.get(key).get(map.get(key).size() - 1);
        } else {
            return null;
        }
    }

    public List<V> getAllElements(K key) {
        if (map.containsKey(key)) {
            return new ArrayList<>(map.get(key));
        } else {
            return new ArrayList<>(0);
        }
    }

    public void remove (V value) {
        for (List<V> list : map.values()) {
            list.remove(value);
        }
    }

    public void remove (K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).remove(value);
        }
    }

    public int size() {
        int result = 0;
        for (List<V> list : map.values()) {
            result = result + list.size();
        }
        return result;
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public boolean isEmpty() {
        if (!map.isEmpty()) {
            for (List<V> list : map.values()) {
                if (list.size() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, List<V>>> mapSet = map.entrySet();
        Set<Map.Entry<K, V>> finalSet = new HashSet<>();
        for (Map.Entry<K, List<V>> entry : mapSet) {
            for (V element : entry.getValue()) {
                Map.Entry<K, V> newEntry = new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), element);
                finalSet.add(newEntry);
            }
        }
        return finalSet;
    }

    public void put(K key, V value) {
        if (!map.containsKey(key)) {
            ArrayList<V> list = new ArrayList<>(listSize);
            map.put(key, list);
        }
        map.get(key).add(value);
    }

    public void forEach(Consumer<? super V> action) {
        map.forEach((key, value) -> value.forEach(action));
    }
}

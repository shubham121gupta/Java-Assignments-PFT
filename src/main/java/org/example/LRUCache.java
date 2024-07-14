package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache {
    private final int capacity;
    private final LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(1));  // returns 1

        lruCache.put(3, 3);  // removed key 2 as it was LRU
        System.out.println(lruCache.get(2));  // returns -1 as key 2 is removed

        lruCache.put(4, 4);  // removed key 1 as it was LRU
        System.out.println(lruCache.get(1));  // returns -1 as 1 is also removed
        System.out.println(lruCache.get(3));  // returns 3
        System.out.println(lruCache.get(4));  // returns 4
    }
}

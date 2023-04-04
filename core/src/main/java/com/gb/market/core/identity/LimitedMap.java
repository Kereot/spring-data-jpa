package com.gb.market.core.identity;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitedMap<K, V> extends LinkedHashMap<K, V> {
    private final int MAX_SIZE;

    public LimitedMap(int maxSize) {
        MAX_SIZE = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_SIZE;
    }
}

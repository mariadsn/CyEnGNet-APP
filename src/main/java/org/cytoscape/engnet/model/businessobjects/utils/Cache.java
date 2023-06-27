package org.cytoscape.engnet.model.businessobjects.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @param <T>
 */
public class Cache<T> {
    private final Map<T, T> cache = new HashMap<T, T>();

    public T get(T obj) {
        return cache.get(obj);
    }
    
    public void add(T obj) {
        cache.put(obj, obj);
    }
    
    public T getOrAdd(T obj) {
        T cachedObj = get(obj);
        if (cachedObj != null) {
            return cachedObj;
        }
        
        add(obj);
        return obj;
    }
    
    public void clear() {
        cache.clear();
    }
    
    public Set<T> getAll() {
        return cache.keySet();
    }
    
    public int size() {
        return cache.size();
    }
    
    public boolean isEmpty() {
        return cache.isEmpty();
    }
}

package com.mars.cloud.main.core.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地接口缓存
 */
public class MarsCacheApi {

    /**
     * 缓存
     */
    private Map<String, List<String>> urls = new ConcurrentHashMap<>();

    private static MarsCacheApi marsCacheApi = new MarsCacheApi();

    public static MarsCacheApi getMarsCacheApi(){
        return marsCacheApi;
    }

    /**
     * 插入缓存
     * @param urls 路径
     */
    public void save(Map<String, List<String>> urls){
        this.urls = urls;
    }

    /**
     * 插入缓存
     * @param key 键
     * @param urls 路径
     */
    public void set(String key, List<String> urls){
        this.urls.put(key, urls);
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 路径
     */
    public List<String> get(String key){
        return urls.get(key);
    }

    /**
     * 删除缓存
     * @param key 建
     */
    public void remove(String key){
        urls.remove(key);
    }
}

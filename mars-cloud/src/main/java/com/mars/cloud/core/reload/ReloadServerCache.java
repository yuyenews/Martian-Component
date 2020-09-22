package com.mars.cloud.core.reload;

import com.mars.cloud.core.cache.ServerApiCacheManager;
import com.mars.cloud.core.cache.model.RestApiCacheModel;

import java.util.List;
import java.util.Map;

/**
 * 刷新本地服务缓存
 */
public class ReloadServerCache {

    private ServerApiCacheManager serverApiCacheManager = new ServerApiCacheManager();

    /**
     * 10秒刷新一次
     */
    public void doReload(){
        Map<String, List<RestApiCacheModel>> restApiModelMap = null;

        serverApiCacheManager.saveCache(restApiModelMap);
    }
}

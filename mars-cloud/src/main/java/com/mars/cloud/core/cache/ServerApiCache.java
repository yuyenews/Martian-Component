package com.mars.cloud.core.cache;

import com.mars.cloud.core.cache.model.RestApiCacheModel;
import com.mars.cloud.core.reload.ReloadServerCache;

import java.util.List;

/**
 * 服务的本地缓存，从naco获取接口列表保存到本地
 */
public class ServerApiCache {

    private static ServerApiCacheManager serverApiCacheManager = new ServerApiCacheManager();

    private static ReloadServerCache reloadServerCache = new ReloadServerCache();

    /**
     * 获取
     * @param serverName
     * @param methodName
     * @return
     */
    public static RestApiCacheModel getRestApiModelForCache(String serverName, String methodName) throws Exception {
        List<RestApiCacheModel> restApiCacheModelList = serverApiCacheManager.getRestApiCacheModelList(serverName,methodName);
        if(restApiCacheModelList == null || restApiCacheModelList.size() < 1){
            /* 如果缓存中没有获取到接口，就去zk里面获取 */
            restApiCacheModelList = reloadServerCache.getRestApiCacheModelByServerName(serverName, methodName);
            if(restApiCacheModelList == null || restApiCacheModelList.size() < 1){
                throw new Exception("没有找到服务接口, serverName:" + serverName + ", methodName:" + methodName);
            }

            for(RestApiCacheModel item : restApiCacheModelList){
                serverApiCacheManager.addCache(serverName, methodName, item);
            }
        }

        return getRestApiModel(restApiCacheModelList);
    }

    /**
     * 用负载均衡算法，算出要调用哪一个接口
     * @param restApiCacheModelList
     * @return
     */
    private static RestApiCacheModel getRestApiModel(List<RestApiCacheModel> restApiCacheModelList){
        // TODO
        return restApiCacheModelList.get(0);
    }
}

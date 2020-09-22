package com.mars.cloud.core.cache;

import com.mars.cloud.core.cache.model.RestApiCacheModel;

import java.util.List;
import java.util.Map;

/**
 * 服务的本地缓存，从naco获取接口列表保存到本地
 */
public class ServerApiCache {

    private static ServerApiCacheManager serverApiCacheManager = new ServerApiCacheManager();

    /**
     * 获取
     * @param serverName
     * @param methodName
     * @return
     */
    public static RestApiCacheModel getRestApiModelForCache(String serverName, String methodName){
        Map<String, List<RestApiCacheModel>> restApiModelMap = serverApiCacheManager.getRestApiModelsByKey();
        List<RestApiCacheModel> restApiCacheModelList = restApiModelMap.get(serverApiCacheManager.getKey(serverName,methodName));
        if(restApiCacheModelList == null || restApiCacheModelList.size() < 1){
            // 从nacos直接读取，并存放到本地缓存
            if(restApiCacheModelList != null && restApiCacheModelList.size() > 0){
                for(RestApiCacheModel restApiCacheModel : restApiCacheModelList){
                    serverApiCacheManager.addCache(serverName, methodName, restApiCacheModel);
                }
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
        return null;
    }
}

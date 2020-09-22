package com.mars.cloud.fuse;

import com.mars.cloud.core.cache.model.RestApiCacheModel;

public class FuseManager {

    /**
     * 是否已经熔断
     * @param restApiCacheModel
     * @return
     */
    public static boolean isFuse(RestApiCacheModel restApiCacheModel){
        return false;
    }

    /**
     * 添加错误次数，只要成功一次就从0开始计算
     * @param restApiCacheModel
     * @return
     */
    public static boolean addFailNum(RestApiCacheModel restApiCacheModel){
        return false;
    }

    /**
     * 添加熔断后的请求次数
     * @param restApiCacheModel
     * @return
     */
    public static boolean addFuseNum(RestApiCacheModel restApiCacheModel){
        return false;
    }

    /**
     * 清空请求失败次数
     * @param restApiCacheModel
     * @return
     */
    public static boolean clearFailNum(RestApiCacheModel restApiCacheModel){
        return false;
    }
}

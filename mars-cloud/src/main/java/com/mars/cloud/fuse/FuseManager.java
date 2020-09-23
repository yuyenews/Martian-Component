package com.mars.cloud.fuse;

import com.mars.cloud.core.cache.model.RestApiCacheModel;

public class FuseManager {

    /**
     * 是否已经熔断
     * @param restApiCacheModel
     * @return
     */
    public static boolean isFuse(RestApiCacheModel restApiCacheModel) throws Exception {
        return FuseCounter.isFuse(restApiCacheModel);
    }

    /**
     * 添加错误次数，只要成功一次就从0开始计算
     * @param restApiCacheModel
     * @return
     */
    public static synchronized void addFailNum(RestApiCacheModel restApiCacheModel) throws Exception {
        FuseCounter.addFailNum(restApiCacheModel);
    }

    /**
     * 添加熔断后的请求次数
     * @param restApiCacheModel
     * @return
     */
    public static synchronized void addFuseNum(RestApiCacheModel restApiCacheModel) throws Exception {
        FuseCounter.addFuseNum(restApiCacheModel);
    }

    /**
     * 清空请求失败次数
     * @param restApiCacheModel
     * @return
     */
    public static synchronized void clearFailNum(RestApiCacheModel restApiCacheModel) throws Exception {
        FuseCounter.clearFailNum(restApiCacheModel);
    }
}

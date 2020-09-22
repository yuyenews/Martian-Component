package com.mars.cloud.request.rest.request;

import com.mars.cloud.core.cache.ServerApiCache;
import com.mars.cloud.core.cache.model.RestApiCacheModel;
import com.mars.cloud.fuse.FuseManager;
import com.mars.cloud.core.util.HttpUtil;
import com.mars.common.util.SerializableUtil;

import java.io.InputStream;

/**
 * 发起rest请求
 */
public class MarsRestTemplate {

    /**
     * 发起请求
     *
     * @param serverName serverName
     * @param methodName methodName
     * @return 结果
     * @throws Exception 异常
     */
    public static <T> T request(String serverName, String methodName,Class<T> resultType) throws Exception {
        return request(serverName, methodName, null,resultType);
    }

    /**
     * 发起请求
     *
     * @param serverName serverName
     * @param methodName methodName
     * @param params     params
     * @return 结果
     * @throws Exception 异常
     */
    public static <T> T request(String serverName, String methodName, Object[] params, Class<T> resultType) throws Exception {
        RestApiCacheModel restApiCacheModel = null;
        try {

            restApiCacheModel = ServerApiCache.getRestApiModelForCache(serverName, methodName);

            if(params == null){
                params = new Object[0];
            }

            /* 判断是否已经被熔断，如果没被熔断，就请求此接口 */
            boolean isFuse =  FuseManager.isFuse(restApiCacheModel);
            if(isFuse){
                InputStream inputStream = HttpUtil.request(restApiCacheModel, params);
                T result = SerializableUtil.deSerialization(inputStream, resultType);

                /* 由于要连续请求失败到一定次数，才会熔断，所以请求成功就清除错误次数 */
                FuseManager.clearFailNum(restApiCacheModel);

                return result;
            } else {
                /* 如果熔断了就拒绝请求，并记录拒绝次数，让熔断器来判断是否进入半熔断状态 */
                FuseManager.addFuseNum(restApiCacheModel);
                throw new Exception("");
            }
        } catch (Exception e) {
            /* 如果请求失败，就记录次数，用来给熔断器判断 是否进入熔断状态 */
            FuseManager.addFailNum(restApiCacheModel);
            throw new Exception("发起请求出现异常,url:[" + restApiCacheModel.getUrl() + "],", e);
        }
    }
}

package com.mars.cloud.fuse;

import com.mars.cloud.core.cache.model.RestApiCacheModel;
import com.mars.cloud.core.config.model.FuseConfig;
import com.mars.cloud.core.util.MarsCloudConfigUtil;
import com.mars.cloud.fuse.model.FuseModel;
import com.mars.common.constant.MarsSpace;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 熔断计数器
 */
public class FuseCounter {

    private static MarsSpace marsSpace = MarsSpace.getEasySpace();

    private static final String COUNTER_KEY = "counterKey";

    private static FuseConfig fuseConfig;

    private static boolean isInit = false;

    /**
     * 初始化
     * @throws Exception
     */
    private static void init() throws Exception {
        if(fuseConfig == null && !isInit){
            // fuseConfig = MarsCloudConfigUtil.getMarsCloudConfig().getFuseConfig();
            isInit = true;
        }
    }

    /**
     * 获取计数器
     * @return
     */
    private static Map<String, FuseModel> getFuseMap(){
        Map<String, FuseModel> fuseMap = null;
        Object fuseObj = marsSpace.getAttr(COUNTER_KEY);
        if(fuseObj == null){
            fuseMap = new ConcurrentHashMap<>();
            marsSpace.setAttr(COUNTER_KEY, fuseMap);
        } else {
            fuseMap = (Map<String, FuseModel>)fuseObj;
        }
        return fuseMap;
    }

    /**
     * 判断是否是熔断状态
     * @param restApiCacheModel
     * @return
     */
    public static boolean isFuse(RestApiCacheModel restApiCacheModel) throws Exception{
        init();
        if(fuseConfig == null){
            return true;
        }

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(restApiCacheModel.getUrl());
        Long failNum = fuseModel.getFailNum();
        Long fuseNum = fuseModel.getFuseNum();

        /* 如果失败的次数超过了配置的上限，并且熔断后被请求的次数还未达到半熔断的临界点，那么就认为此时是熔断状态 */
        if(failNum >= fuseConfig.getFailNum() && fuseNum < fuseConfig.getFuseNum()){
            return false;
        }
        return true;
    }

    /**
     * 添加错误次数，只要成功一次就从0开始计算
     * @param restApiCacheModel
     * @return
     */
    public static void addFailNum(RestApiCacheModel restApiCacheModel) throws Exception{
        init();
        if(fuseConfig == null){
            return;
        }

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(restApiCacheModel.getUrl());
        fuseModel.setFailNum(fuseModel.getFailNum()+1);

        fuseMap.put(restApiCacheModel.getUrl(), fuseModel);
        marsSpace.setAttr(COUNTER_KEY, fuseMap);
    }

    /**
     * 添加熔断后的请求次数
     * @param restApiCacheModel
     * @return
     */
    public static void addFuseNum(RestApiCacheModel restApiCacheModel) throws Exception{
        init();
        if(fuseConfig == null){
            return;
        }

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(restApiCacheModel.getUrl());
        fuseModel.setFuseNum(fuseModel.getFuseNum()+1);

        fuseMap.put(restApiCacheModel.getUrl(), fuseModel);
        marsSpace.setAttr(COUNTER_KEY, fuseMap);
    }

    /**
     * 清空请求失败次数
     * @param restApiCacheModel
     * @return
     */
    public static void clearFailNum(RestApiCacheModel restApiCacheModel) throws Exception{
        init();
        if(fuseConfig == null){
            return;
        }

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(restApiCacheModel.getUrl());
        if(fuseModel.getFailNum() >= fuseConfig.getFailNum()){
            /* 如果错误次数达到了熔断次数，则把错误次数改为 熔断次数-1，这样如果再错一次会立刻进入熔断 */
            fuseModel.setFailNum(fuseConfig.getFailNum()-1);
        } else {
            /* 否则就直接清零，重新计算错误次数 */
            fuseModel.setFailNum(0L);
        }
        fuseModel.setFuseNum(0L);

        fuseMap.put(restApiCacheModel.getUrl(), fuseModel);
        marsSpace.setAttr(COUNTER_KEY, fuseMap);
    }
}

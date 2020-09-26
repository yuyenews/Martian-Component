package com.mars.fuse;

import com.mars.cloud.config.model.FuseConfig;
import com.mars.cloud.fuse.FuseManager;
import com.mars.cloud.util.MarsCloudConfigUtil;
import com.mars.common.constant.MarsSpace;
import com.mars.fuse.model.FuseModel;
import com.mars.fuse.util.MD5Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 熔断计数器
 */
public class FuseCounter implements FuseManager {

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
            fuseConfig = MarsCloudConfigUtil.getMarsCloudConfig().getFuseConfig();
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
     * @param serverName
     * @param methodName
     * @param url
     * @return
     * @throws Exception
     */
    public boolean isFuse(String serverName, String methodName, String url) throws Exception{
        init();
        if(fuseConfig == null){
            return true;
        }

        String key = getKey(serverName, methodName, url);

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(key);
        if(fuseModel == null){
            return true;
        }
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
     * @param serverName
     * @param methodName
     * @param url
     * @throws Exception
     */
    public synchronized void addFailNum(String serverName, String methodName, String url) throws Exception{
        init();
        if(fuseConfig == null){
            return;
        }

        String key = getKey(serverName, methodName, url);

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(key);
        fuseModel.setFailNum(fuseModel.getFailNum()+1);

        fuseMap.put(key, fuseModel);
        marsSpace.setAttr(COUNTER_KEY, fuseMap);
    }

    /**
     * 添加熔断后的请求次数
     * @param serverName
     * @param methodName
     * @param url
     * @throws Exception
     */
    public synchronized void addFuseNum(String serverName, String methodName, String url) throws Exception{
        init();
        if(fuseConfig == null){
            return;
        }

        String key = getKey(serverName, methodName, url);

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(key);
        fuseModel.setFuseNum(fuseModel.getFuseNum()+1);

        fuseMap.put(key, fuseModel);
        marsSpace.setAttr(COUNTER_KEY, fuseMap);
    }

    /**
     * 清空请求失败次数
     * @param serverName
     * @param methodName
     * @param url
     * @throws Exception
     */
    public synchronized void clearFailNum(String serverName, String methodName, String url) throws Exception{
        init();
        if(fuseConfig == null){
            return;
        }

        String key = getKey(serverName, methodName, url);

        Map<String, FuseModel> fuseMap = getFuseMap();
        FuseModel fuseModel = fuseMap.get(key);
        if(fuseModel.getFailNum() >= fuseConfig.getFailNum()){
            /* 如果错误次数达到了熔断次数，则把错误次数改为 熔断次数-1，这样如果再错一次会立刻进入熔断 */
            fuseModel.setFailNum(fuseConfig.getFailNum()-1);
        } else {
            /* 否则就直接清零，重新计算错误次数 */
            fuseModel.setFailNum(0L);
        }
        fuseModel.setFuseNum(0L);

        fuseMap.put(key, fuseModel);
        marsSpace.setAttr(COUNTER_KEY, fuseMap);
    }

    private String getKey(String serverName, String methodName, String url) throws Exception {
        StringBuffer str = new StringBuffer();
        str.append(serverName);
        str.append(methodName);
        str.append(url);
        return MD5Util.encryption(str.toString());
    }
}

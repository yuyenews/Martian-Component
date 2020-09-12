package com.mars.cloud.request.rest.load;

import com.mars.cloud.main.core.cache.MarsCacheApi;
import com.mars.cloud.main.core.constant.MarsCloudConstant;
import com.mars.cloud.main.core.zookeeper.ZkHelper;
import com.mars.cloud.request.rest.manager.BalancingManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取请求路径
 */
public class MarsCloudServerApis {

    /**
     * 根据服务名和Controller的方法名称,获取接口信息
     * @param serverName
     * @return
     */
    public static String getUrl(String serverName,String methodName) throws Exception {
        String path = MarsCloudConstant.SERVER_NODE.replace("{serverName}",serverName).replace("{method}",methodName);

        /* 从本地缓存中获取请求连接 */
        List<String> urlList  = getUrlsForCache(path);

        if(urlList == null){
            /* 如果本地缓存中没有所需的连接，就去zookeeper里读取，并缓存到本地 */
            urlList = getUrlsForZookeeper(path);
            if(urlList != null && urlList.size() > 0){
                MarsCacheApi.getMarsCacheApi().set(path,urlList);
            }
        }
        /* 将获取到的一批连接根据负载均衡算法得出一个并返回 */
        return getUrlForList(path, urlList);
    }

    /**
     * 从本地缓存中获取请求连接
     * @param path
     * @return
     */
    private static List<String> getUrlsForCache(String path){
        return MarsCacheApi.getMarsCacheApi().get(path);
    }

    /**
     * 从zookeeper读取接口信息，然后加入本地缓存
     * @param path
     * @return
     */
    private static List<String> getUrlsForZookeeper(String path) throws Exception {

        /* 如果zookeeper不是连接状态，则打开连接 */
        ZkHelper.openConnection();

        /* 创建存储连接的集合跟对象 */
        List<String> urls = new ArrayList<>();

        /* 获取到zookeeper里的连接 */
        List<String> urlNodes = ZkHelper.getChildren(path);
        if(urlNodes == null || urlNodes.size() < 1){
            return urls;
        }

        /* 将获取到的连接提取出来保存到集合中 */
        for(String urlNode : urlNodes){
            urls.add(ZkHelper.getData(path+"/"+urlNode));
        }

        return urls;
    }

    /**
     * 根据负载均衡策略，从集群中获取一个连接
     * @param path
     * @param urlList
     * @return
     */
    private static String getUrlForList(String path, List<String> urlList) throws Exception {
        if(urlList == null || urlList.size() < 1){
            throw new Exception("请求地址不正确，请检查serverName和methodName后再尝试");
        }
        return BalancingManager.getUrl(path, urlList);
    }
}

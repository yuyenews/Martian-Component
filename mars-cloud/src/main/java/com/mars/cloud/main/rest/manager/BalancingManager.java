package com.mars.cloud.main.rest.manager;

import com.mars.cloud.main.core.config.model.enums.Strategy;
import com.mars.cloud.main.core.util.MarsCloudConfigUtil;
import com.mars.server.server.request.HttpMarsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡算法
 */
public class BalancingManager {

    private static Logger logger = LoggerFactory.getLogger(BalancingManager.class);

    private static Random random = new Random();

    private static HttpMarsContext httpMarsContext = HttpMarsContext.getHttpContext();

    /**
     * 获取此次请求的URL,自动根据配置选择负载均衡策略
     *
     * @param path 路径
     * @param urls 参数
     * @return 结果
     */
    public static String getUrl(String path,List<String> urls) {
        try {
            Strategy strategy = MarsCloudConfigUtil.getMarsCloudConfig().getCloudConfig().getStrategy();

            switch (strategy){
                case POLLING:
                    return polling(path,urls);
                case RANDOM:
                    return random(urls);
                default:
                    return null;
            }

        } catch (Exception e){
            logger.error("",e);
            return null;
        }
    }

    /**
     * 随机算法
     *
     * @param urls 参数
     * @return 结果
     */
    private static String random(List<String> urls) {
        return urls.get(getRandomIndex(urls));
    }

    /**
     * 轮询算法
     *
     * @param urls 参数
     * @return 结果
     */
    private static String polling(String path, List<String> urls) {
        int index = getPollingIndex(path,urls);
        String url = urls.get(index);
        while(url == null && index > 0){
            index--;
            url = urls.get(index);
            if(url != null){
                return url;
            }
        }
        return url;
    }

    /**
     * 随机算法
     *
     * @return 下标
     */
    private static int getRandomIndex(List<String> urls) {
        int index = 0;
        if (urls.size() > 1) {
            index = random.nextInt(urls.size());
        }

        if (index < 0) {
            index = 0;
        }
        return index;
    }

    /**
     * 轮询算法
     *
     * @return 下标
     */
    private static synchronized int getPollingIndex(String path,List<String> urls) {

        int nowIndex = getNowIndex(path);

        if(nowIndex >= (urls.size() - 1)){
            nowIndex = 0;
            /*
             * 在项目的运行中，有些服务会被下掉，有些服务会减少或者修改接口
             * 这种变动可能会给这个缓存中造成垃圾数据，所以每经过一轮就清除一下
             * 用来防止产生垃圾数据
             */
            httpMarsContext.remove(path);
        } else {
            nowIndex++;
        }
        httpMarsContext.setAttr(path,nowIndex);
        return nowIndex;
    }

    /**
     * 获取当前下标
     * @param path 路径
     * @return
     */
    private static int getNowIndex(String path){
        Object nowIndexCache = httpMarsContext.getAttr(path);
        if(nowIndexCache != null){
            return Integer.parseInt(nowIndexCache.toString());
        }
        return 0;
    }
}

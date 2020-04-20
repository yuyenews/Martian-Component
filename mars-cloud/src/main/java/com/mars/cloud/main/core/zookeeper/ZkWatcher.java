package com.mars.cloud.main.core.zookeeper;

import com.mars.cloud.main.core.cache.MarsCacheApi;
import com.mars.cloud.main.load.refresh.RefreshManager;
import com.mars.cloud.main.load.register.Registered;
import com.mars.ioc.factory.BeanFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 监视器
 */
public class ZkWatcher implements Watcher {

    private static Logger marsLogger = LoggerFactory.getLogger(ZkWatcher.class);


    private CountDownLatch countDownLatch;

    public ZkWatcher(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    /**
     * 监视zk的状态
     *
     * @param event 事件
     */
    @Override
    public void process(WatchedEvent event) {
        try {
            switch (event.getState()) {
                case Expired:
                    /* 超时重连 */
                    reConnectionZookeeper();
                    break;
                case SyncConnected:
                    /* 连接成功 */
                    countDownLatch.countDown();
                    break;
            }

            /* 注册中心的接口有了变化，就刷新本地缓存 */
            switch (event.getType()) {
                case NodeChildrenChanged:
                case NodeCreated:
                case NodeDeleted:
                case NodeDataChanged:
                    /* 刷新本地缓存 */
                    refreshCacheApi();
                    break;
            }
        } catch (Exception e) {
            marsLogger.error("处理zookeeper的通知时出错", e);
        }
    }

    /**
     * 重连zookeeper
     */
    private void reConnectionZookeeper() {
        while (true) {
            try {
                marsLogger.info("zookeeper连接已断开，正在重新连接并注册接口");

                /* 重连并注册 */
                ZkHelper.openConnection();
                Registered registered = BeanFactory.getBean("registered", Registered.class);
                registered.registerApi();

                /* 刷新本地缓存 */
                refreshCacheApi();
                return;
            } catch (Exception e) {
                /* 重连失败就 再连接一次，务必保证重连成功 */
                marsLogger.error("zookeeper重连失败，即将重试", e);
                try {
                    Thread.sleep(2000);
                } catch (Exception e2){
                }
                continue;
            }
        }
    }

    /**
     * 刷新本地API缓存
     */
    private void refreshCacheApi() {
        try {
            RefreshManager refreshManager = BeanFactory.getBean("refreshManager",RefreshManager.class);
            Map<String, List<String>> apiMaps = refreshManager.refreshCacheApi();
            MarsCacheApi.getMarsCacheApi().save(apiMaps);
            return;
        } catch (Exception e) {
            /*
             * 如果出异常了，由于被捕获，所以程序不会停掉
             * 而且还有一个定时任务在做补偿，15秒后会再刷新一次
             * 所以这里什么都不干，默默的就好
             */
        }
    }
}
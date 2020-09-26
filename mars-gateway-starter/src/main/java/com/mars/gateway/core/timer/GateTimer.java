package com.mars.gateway.core.timer;

import com.mars.cloud.core.cache.LoadServerCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务
 */
public class GateTimer {

    private static Logger logger = LoggerFactory.getLogger(GateTimer.class);

    private static LoadServerCache loadServerCache = new LoadServerCache();

    /**
     * 刷新频率
     */
    private static final int period = 10000;

    /**
     * 刷新本地缓存的微服务接口
     */
    public static void doTimer(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    loadServerCache.doLoad();
                } catch (Exception e){
                    logger.error("刷新本地服务缓存失败，10秒后将重试", e);
                }
            }
        }, new Date(),period);
    }
}

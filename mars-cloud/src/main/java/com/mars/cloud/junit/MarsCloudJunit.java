package com.mars.cloud.junit;

import com.mars.cloud.main.core.config.MarsCloudConfig;
import com.mars.cloud.starter.startlist.StartFeign;
import com.mars.common.annotation.junit.MarsTest;
import com.mars.common.util.MarsConfiguration;
import com.mars.jdbc.core.load.InitJdbc;
import com.mars.start.base.MarsJunitStart;
import com.mars.start.startmap.StartMap;
import com.mars.start.startmap.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * junit
 */
public abstract class MarsCloudJunit {

    private Logger logger = LoggerFactory.getLogger(MarsCloudJunit.class);

    /**
     * 加载项目启动的必要数据
     *
     * @param packName 包名
     */
    public void init(Class packName) {

        Map<Integer, StartMap> startList = new HashMap<>();
        startList.put(0, new StartLoadClass());
        startList.put(1, new StartBeans());
        startList.put(2, new StartJDBC());
        startList.put(3, new StartFeign());
        startList.put(4, new StartBeanObject());
        startList.put(5, new HasStart());
        startList.put(6, new StartMarsTimer());
        startList.put(7, new StartLoadAfter());
        startList.put(8, new StartExecuteTimer());

        MarsJunitStart.setStartList(startList);
        MarsJunitStart.start(new InitJdbc(), packName, this);
    }

    /**
     * 获取配置信息
     * @return
     */
    public abstract MarsCloudConfig getMarsConfig();

    /**
     * 加载项目启动的必要数据
     *
     */
    public MarsCloudJunit() {
        MarsConfiguration.loadConfig(getMarsConfig());

        MarsTest marsTest = this.getClass().getAnnotation(MarsTest.class);
        if(marsTest == null || marsTest.startClass() == null){
            logger.error("没有正确的配置MarsTest注解");
        } else {
            init(marsTest.startClass());
        }
    }
}

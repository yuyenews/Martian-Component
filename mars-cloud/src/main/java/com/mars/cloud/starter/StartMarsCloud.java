package com.mars.cloud.starter;

import com.mars.cloud.main.core.config.MarsCloudConfig;
import com.mars.cloud.starter.startlist.StartFeign;
import com.mars.common.util.MarsConfiguration;
import com.mars.jdbc.load.InitJdbc;
import com.mars.start.base.BaseStartMars;
import com.mars.start.startmap.StartMap;
import com.mars.start.startmap.impl.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Mars-cloud启动类
 */
public class StartMarsCloud {

    /**
     * 启动Mars框架
     *
     * @param clazz 类
     */
    public static void start(Class<?> clazz, MarsCloudConfig marsCloudConfig) {

        Map<Integer, StartMap> startList = new HashMap<>();

        startList.put(0, new StartCoreServlet());
        startList.put(1, new StartLoadClass());
        startList.put(2, new StartBeans());
        startList.put(3, new StartJDBC());
        startList.put(4, new StartFeign());
        startList.put(5, new StartBeanObject());
        startList.put(6, new StartMarsApi());
        startList.put(7, new StartInter());
        startList.put(8, new HasStart());
        startList.put(9, new StartMarsTimer());
        startList.put(10, new StartLoadAfter());
        startList.put(11, new StartExecuteTimer());

        BaseStartMars.setStartList(startList);
        MarsConfiguration.loadConfig(marsCloudConfig);

        BaseStartMars.start(clazz,new InitJdbc());
    }
}

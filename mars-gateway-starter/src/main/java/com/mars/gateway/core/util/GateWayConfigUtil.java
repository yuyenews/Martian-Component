package com.mars.gateway.core.util;

import com.mars.cloud.config.MarsCloudConfig;
import com.mars.common.base.config.MarsConfig;
import com.mars.common.constant.MarsConstant;
import com.mars.common.constant.MarsSpace;
import com.mars.common.util.MarsConfiguration;
import com.mars.gateway.config.MarsGateWayConfig;
import com.mars.gateway.config.impl.MarsCloudConfigImpl;
import com.mars.gateway.config.impl.MarsGateWayConfigImpl;

/**
 * 配置工具类
 */
public class GateWayConfigUtil {

    private static MarsSpace constants = MarsSpace.getEasySpace();

    private static final String GATE_WAY_CONFIG = "gateWayConfig";

    /**
     * 设置配置信息
     * @param marsGateWayConfig
     */
    public static void setConfig(MarsGateWayConfig marsGateWayConfig){
        // 缓存cloud配置文件
        MarsCloudConfigImpl marsCloudConfig = new MarsCloudConfigImpl();
        marsCloudConfig.setMarsGateWayConfig(marsGateWayConfig);
        MarsConfiguration.loadConfig(marsCloudConfig);

        // 缓存网关配置文件
        constants.setAttr(GATE_WAY_CONFIG,marsGateWayConfig);
    }

    /**
     * 获取配置信息
     * @return
     */
    public static MarsGateWayConfig getMarsGateWayConfig(){
        Object config = constants.getAttr(GATE_WAY_CONFIG);
        if(config != null){
            return (MarsGateWayConfig)config;
        }
        return null;
    }
}

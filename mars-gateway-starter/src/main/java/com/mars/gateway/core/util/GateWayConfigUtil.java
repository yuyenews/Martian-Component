package com.mars.gateway.core.util;

import com.mars.cloud.config.MarsCloudConfig;
import com.mars.cloud.config.model.CloudConfig;
import com.mars.cloud.config.model.FuseConfig;
import com.mars.common.base.config.MarsConfig;
import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.base.config.model.ThreadPoolConfig;
import com.mars.common.util.MarsConfiguration;
import com.mars.gateway.config.MarsGateWayConfig;
import com.mars.gateway.config.impl.MarsCloudConfigImpl;
import com.mars.gateway.config.impl.MarsGateWayConfigImpl;

/**
 * 配置工具类
 */
public class GateWayConfigUtil {

    /**
     * 设置配置信息
     * @param marsGateWayConfig
     */
    public static void setConfig(MarsGateWayConfig marsGateWayConfig){
        MarsCloudConfigImpl marsCloudConfig = new MarsCloudConfigImpl();
        marsCloudConfig.setMarsGateWayConfig(marsGateWayConfig);

        MarsConfiguration.loadConfig(marsCloudConfig);
    }

    /**
     * 获取配置信息
     * @return
     */
    public static MarsGateWayConfig getMarsGateWayConfig(){
        MarsConfig marsConfig = MarsConfiguration.getConfig();
        if(marsConfig instanceof MarsCloudConfig){
            MarsCloudConfig marsCloudConfig = (MarsCloudConfig)marsConfig;
            return getMarsGateWayConfigObj(marsCloudConfig);
        }
        return null;
    }

    /**
     * 获取配置信息对象
     * @param marsCloudConfig
     * @return
     */
    private static MarsGateWayConfig getMarsGateWayConfigObj(MarsCloudConfig marsCloudConfig){
        MarsGateWayConfigImpl marsGateWayConfig = new MarsGateWayConfigImpl();
        marsGateWayConfig.setMarsCloudConfig(marsCloudConfig);
        return marsGateWayConfig;
    }
}

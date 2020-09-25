package com.mars.gateway.core.util;

import com.mars.cloud.config.MarsCloudConfig;
import com.mars.cloud.config.model.CloudConfig;
import com.mars.cloud.config.model.FuseConfig;
import com.mars.common.base.config.MarsConfig;
import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.base.config.model.ThreadPoolConfig;
import com.mars.common.util.MarsConfiguration;
import com.mars.gateway.config.MarsGateWayConfig;

/**
 * 配置工具类
 */
public class GateWayConfigUtil {

    /**
     * 设置配置信息
     * @param marsGateWayConfig
     */
    public static void setConfig(MarsGateWayConfig marsGateWayConfig){
        MarsCloudConfig marsCloudConfig = new MarsCloudConfig() {
            @Override
            public CloudConfig getCloudConfig() {
                CloudConfig cloudConfig = marsGateWayConfig.getGateWayConfig();
                cloudConfig.setGateWay(true);
                return cloudConfig;
            }

            @Override
            public FuseConfig getFuseConfig() {
                return marsGateWayConfig.getFuseConfig();
            }

            @Override
            public int port() {
                return marsGateWayConfig.port();
            }

            @Override
            public ThreadPoolConfig getThreadPoolConfig() {
                return marsGateWayConfig.getThreadPoolConfig();
            }

            @Override
            public CrossDomainConfig crossDomainConfig() {
                return marsGateWayConfig.crossDomainConfig();
            }
        };

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
        MarsGateWayConfig marsGateWayConfig = new MarsGateWayConfig() {
            @Override
            public CloudConfig getGateWayConfig() {
                return marsCloudConfig.getCloudConfig();
            }

            @Override
            public FuseConfig getFuseConfig() {
                return marsCloudConfig.getFuseConfig();
            }

            @Override
            public int port() {
                return marsCloudConfig.port();
            }

            @Override
            public ThreadPoolConfig getThreadPoolConfig() {
                return marsCloudConfig.getThreadPoolConfig();
            }

            @Override
            public CrossDomainConfig crossDomainConfig() {
                return marsCloudConfig.crossDomainConfig();
            }
        };
        return marsGateWayConfig;
    }
}

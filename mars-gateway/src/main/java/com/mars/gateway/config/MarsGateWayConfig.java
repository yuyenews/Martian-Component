package com.mars.gateway.config;

import com.mars.cloud.config.MarsCloudConfig;
import com.mars.cloud.config.model.CloudConfig;

/**
 * 网关配置
 */
public abstract class MarsGateWayConfig extends MarsCloudConfig {

    @Override
    public CloudConfig getCloudConfig() {
        CloudConfig cloudConfig = getGateWayConfig();
        cloudConfig.setGateWay(true);
        return cloudConfig;
    }

    /**
     * 配置网关信息
     * @return
     */
    public abstract CloudConfig getGateWayConfig();
}

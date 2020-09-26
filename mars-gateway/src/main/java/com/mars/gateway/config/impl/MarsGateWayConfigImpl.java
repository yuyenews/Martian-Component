package com.mars.gateway.config.impl;

import com.mars.cloud.config.MarsCloudConfig;
import com.mars.cloud.config.model.CloudConfig;
import com.mars.cloud.config.model.FuseConfig;
import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.base.config.model.ThreadPoolConfig;
import com.mars.gateway.config.MarsGateWayConfig;

public class MarsGateWayConfigImpl extends MarsGateWayConfig {

    private MarsCloudConfig marsCloudConfig;

    public MarsCloudConfig getMarsCloudConfig() {
        return marsCloudConfig;
    }

    public void setMarsCloudConfig(MarsCloudConfig marsCloudConfig) {
        this.marsCloudConfig = marsCloudConfig;
    }

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
}

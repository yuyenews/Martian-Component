package com.mars.cloud.config;

import com.mars.cloud.core.config.MarsCloudConfig;
import com.mars.cloud.core.config.model.CloudConfig;
import com.mars.cloud.core.config.model.FuseConfig;

public class TestConfig extends MarsCloudConfig {

    @Override
    public CloudConfig getCloudConfig() {
        return new CloudConfig();
    }

    @Override
    public FuseConfig getFuseConfig() {
        FuseConfig fuseConfig = new FuseConfig();
        fuseConfig.setFailNum(100L);
        fuseConfig.setFuseNum(10L);
        return fuseConfig;
    }
}

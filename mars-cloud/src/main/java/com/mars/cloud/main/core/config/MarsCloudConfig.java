package com.mars.cloud.main.core.config;

import com.mars.cloud.main.core.config.model.CloudConfig;
import com.mars.core.base.config.MarsConfig;

/**
 * Mars-cloud配置
 */
public abstract class MarsCloudConfig extends MarsConfig {

    /**
     * Mars-cloud配置
     * @return 配置
     */
    public abstract CloudConfig getCloudConfig();
}

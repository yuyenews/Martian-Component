package com.mars.cloud.core.config;

import com.mars.cloud.core.config.model.CloudConfig;
import com.mars.common.base.config.MarsConfig;

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

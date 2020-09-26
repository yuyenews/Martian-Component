package com.mars.gateway.config;

import com.mars.cloud.config.model.CloudConfig;
import com.mars.cloud.config.model.FuseConfig;
import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.base.config.model.ThreadPoolConfig;

/**
 * 网关配置
 */
public abstract class MarsGateWayConfig {

    /**
     * 端口号
     * @return
     */
    public int port(){
        return 8080;
    }

    /**
     * 配置网关信息
     * @return
     */
    public abstract CloudConfig getGateWayConfig();

    /**
     * 线程池配置
     * @return 线程池配置
     */
    public ThreadPoolConfig getThreadPoolConfig(){
        return new ThreadPoolConfig();
    }

    /**
     * 跨域配置
     * @return 跨域配置
     */
    public CrossDomainConfig crossDomainConfig(){
        return new CrossDomainConfig();
    }

    /**
     * 熔断器配置
     * @return
     */
    public FuseConfig getFuseConfig() {
        return new FuseConfig();
    }
}

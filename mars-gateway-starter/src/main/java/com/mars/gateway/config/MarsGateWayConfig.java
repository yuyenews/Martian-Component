package com.mars.gateway.config;

import com.mars.cloud.balanced.BalancedCalc;
import com.mars.cloud.config.model.CloudConfig;
import com.mars.cloud.config.model.FuseConfig;
import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.base.config.model.ThreadPoolConfig;
import com.mars.gateway.api.filter.GateFilter;

import java.util.ArrayList;
import java.util.List;

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
     * 读取请求报文的缓冲区大小(每次读多少字节)
     * 默认 1M
     * @return
     */
    public int readSize(){
        return 1 * 1024 * 1024;
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

    /**
     * 获取负载均衡策略
     * @return
     */
    public BalancedCalc getBalancedCalc(){
        /* 返回null 表示使用默认（普通轮询）策略 */
        return null;
    }

    /**
     * 获取一个过滤器
     * @return
     */
    public List<GateFilter> getGateFilter(){
        return new ArrayList<>();
    }
}

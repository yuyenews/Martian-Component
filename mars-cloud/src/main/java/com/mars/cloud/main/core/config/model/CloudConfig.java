package com.mars.cloud.main.core.config.model;

import com.mars.cloud.main.core.config.model.enums.Strategy;

public class CloudConfig {
    /**
     * 服务名称，同一个服务的负载均衡集群的name必须一致，不同集群之间必须唯一
     */
    private String name;
    /**
     * 尽量长一点，防止接口过多来不及发布
     */
    private Long sessionTimeout = 10000L;
    /**
     * 请求Mars-Cloud接口超时时间
     */
    private Long timeOut = 10000L;
    /**
     * 负载均衡策略 POLLING 轮询，RANDOM 随机（暂时只支持这两种）
     */
    private Strategy strategy = Strategy.POLLING;
    /**
     * zookeeper地址，多个地址用英文逗号分割，并在外面加一个双引号
     */
    private String register;

    /**
     * 是否为一个网关
     */
    private Boolean gateWay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public Boolean getGateWay() {
        return gateWay;
    }

    public void setGateWay(Boolean gateWay) {
        this.gateWay = gateWay;
    }
}

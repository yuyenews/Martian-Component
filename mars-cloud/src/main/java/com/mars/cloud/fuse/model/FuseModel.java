package com.mars.cloud.fuse.model;

/**
 * 熔断器，计数实体类
 */
public class FuseModel {

    /**
     * 失败多少次就熔断
     */
    private Long failNum = 0L;

    /**
     * 熔断后被请求多少次后，进入半熔断状态
     */
    private Long fuseNum = 0L;

    public Long getFailNum() {
        return failNum;
    }

    public void setFailNum(Long failNum) {
        this.failNum = failNum;
    }

    public Long getFuseNum() {
        return fuseNum;
    }

    public void setFuseNum(Long fuseNum) {
        this.fuseNum = fuseNum;
    }
}

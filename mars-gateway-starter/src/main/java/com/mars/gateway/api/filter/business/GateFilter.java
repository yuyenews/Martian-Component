package com.mars.gateway.api.filter.business;

import com.mars.server.server.request.HttpMarsRequest;
import com.mars.server.server.request.HttpMarsResponse;

/**
 * 网关过滤器
 */
public interface GateFilter {

    String SUCCESS = "success";

    /**
     * 开始执行过滤器
     * @param request
     * @param response
     * @return
     */
    Object doFilter(HttpMarsRequest request, HttpMarsResponse response);
}

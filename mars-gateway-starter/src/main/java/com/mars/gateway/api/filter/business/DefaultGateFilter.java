package com.mars.gateway.api.filter.business;

import com.mars.server.server.request.HttpMarsRequest;
import com.mars.server.server.request.HttpMarsResponse;

/**
 * 默认拦截器
 */
public class DefaultGateFilter implements GateFilter {

    /**
     * 直接通过，默认拦截器不拦截任何请求
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object doFilter(HttpMarsRequest request, HttpMarsResponse response) {
        return SUCCESS;
    }
}

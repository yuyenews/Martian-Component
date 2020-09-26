package com.mars.gateway.api.inters;

import com.mars.common.constant.MarsConstant;
import com.mars.gateway.api.util.DispatcherUtil;
import com.mars.iserver.execute.access.PathAccess;
import com.sun.net.httpserver.HttpExchange;

public class Filter {

    public static boolean doFilter(String requestUri, HttpExchange httpExchange){
        String method =  httpExchange.getRequestMethod().toUpperCase();
        /* 如果请求方式是options，那就说明这是一个试探性的请求，直接响应即可 */
        if(method.equals(MarsConstant.OPTIONS.toLowerCase())){
            return true;
        }

        /* 如果请求的路径不合法，则直接响应 */
        String uri = DispatcherUtil.getUriName(requestUri);
        if(PathAccess.hasAccess(uri)){
            return true;
        }
        return false;
    }
}

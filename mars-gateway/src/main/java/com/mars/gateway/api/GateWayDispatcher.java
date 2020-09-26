package com.mars.gateway.api;

import com.alibaba.fastjson.JSON;
import com.mars.gateway.api.inters.Filter;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.api.util.DispatcherUtil;
import com.mars.gateway.core.constant.GateWayConstant;
import com.mars.gateway.core.util.RequestAndResultUtil;
import com.mars.gateway.request.RequestServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 核心控制器，用来实现转发和响应
 */
public class GateWayDispatcher implements HttpHandler {

    private Logger log = LoggerFactory.getLogger(GateWayDispatcher.class);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String requestUri = httpExchange.getRequestURI().toString();
            String uri = DispatcherUtil.getRequestPath(requestUri).toUpperCase();
            if(uri.equals("/")){
                /* 如果请求的是根目录，则返回欢迎语 */
                RequestAndResultUtil.send(httpExchange, GateWayConstant.WELCOME);
                return;
            }

            boolean isFail = Filter.doFilter(requestUri, httpExchange);
            if(isFail){
                /* 如果请求的路径不合法，则直接响应一个ok */
                RequestAndResultUtil.send(httpExchange, GateWayConstant.OK);
                return;
            }

            RequestInfoModel requestInfoModel = RequestAndResultUtil.getServerNameAndMethodName(requestUri);

            if(requestUri.startsWith(RequestAndResultUtil.ROUTER)){
                Object object = RequestServer.doRouterRequest(requestInfoModel, httpExchange);
                RequestAndResultUtil.send(httpExchange, JSON.toJSONString(object));
            } else if(requestUri.startsWith(RequestAndResultUtil.DOWNLOAD)) {
                InputStream inputStream = RequestServer.doDownLoadRequest(requestInfoModel, httpExchange);
                RequestAndResultUtil.downLoad(httpExchange, UUID.randomUUID().toString(), inputStream);
            } else {
                throw new IOException("请求路径有误");
            }
        } catch (Exception e) {
            log.error("处理请求失败!", e);
            RequestAndResultUtil.send(httpExchange,"处理请求发生错误"+e.getMessage());
        }
    }
}

package com.mars.gateway.request;

import com.mars.cloud.request.rest.request.MarsRestTemplate;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.request.param.ParamConversionToModel;
import com.mars.gateway.request.util.RequestUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;

/**
 * 转发给对应的微服务
 */
public class RequestServer {

    /**
     * 向微服务发起请求
     * @param requestInfoModel
     * @param httpExchange
     * @return
     * @throws Exception
     */
    public static Object doRequest(RequestInfoModel requestInfoModel, HttpExchange httpExchange) throws Exception {
        Object params = ParamConversionToModel.paramConversionToMap(httpExchange);

        return MarsRestTemplate.request(requestInfoModel.getServerName(),
                requestInfoModel.getMethodName(),
                new Object[]{params},
                Object.class,
                RequestUtil.getContentType(httpExchange));
    }

    /**
     * 向微服务发起请求
     * @param requestInfoModel
     * @param httpExchange
     * @return
     * @throws Exception
     */
    public static InputStream doDownLoadRequest(RequestInfoModel requestInfoModel, HttpExchange httpExchange){
        return null;
    }


}

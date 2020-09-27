package com.mars.gateway.request;

import com.mars.cloud.request.rest.request.MarsRestTemplate;
import com.mars.cloud.request.util.model.HttpResultModel;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.request.param.ParamConversionToModel;
import com.mars.gateway.request.util.RequestUtil;
import com.sun.net.httpserver.HttpExchange;

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
    public static Object doRouterRequest(RequestInfoModel requestInfoModel, HttpExchange httpExchange) throws Exception {
        return doRequest(requestInfoModel, httpExchange, Object.class);
    }

    /**
     * 向微服务发起请求
     * @param requestInfoModel
     * @param httpExchange
     * @return
     * @throws Exception
     */
    public static HttpResultModel doDownLoadRequest(RequestInfoModel requestInfoModel, HttpExchange httpExchange) throws Exception {
        return doRequest(requestInfoModel, httpExchange, HttpResultModel.class);
    }

    /**
     * 发起请求
     * @param requestInfoModel
     * @param httpExchange
     * @param resultType
     * @return
     * @throws Exception
     */
    private static <T> T doRequest(RequestInfoModel requestInfoModel, HttpExchange httpExchange, Class<T> resultType) throws Exception {
        Object params = ParamConversionToModel.paramConversionToMap(httpExchange);

        return MarsRestTemplate.request(requestInfoModel.getServerName(),
                requestInfoModel.getMethodName(),
                new Object[]{params},
                resultType,
                RequestUtil.getContentType(httpExchange));
    }
}

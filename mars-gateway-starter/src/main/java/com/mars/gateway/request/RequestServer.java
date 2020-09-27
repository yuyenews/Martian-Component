package com.mars.gateway.request;

import com.mars.cloud.request.rest.request.MarsRestTemplate;
import com.mars.cloud.request.util.model.HttpResultModel;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.request.param.ParamConversionToModel;
import com.mars.gateway.request.util.RequestUtil;
import com.mars.server.server.request.HttpMarsRequest;

/**
 * 转发给对应的微服务
 */
public class RequestServer {

    /**
     * 向微服务发起请求
     * @param requestInfoModel
     * @param request
     * @return
     * @throws Exception
     */
    public static Object doRouterRequest(RequestInfoModel requestInfoModel, HttpMarsRequest request) throws Exception {
        return doRequest(requestInfoModel, request, Object.class);
    }

    /**
     * 向微服务发起请求
     * @param requestInfoModel
     * @param request
     * @return
     * @throws Exception
     */
    public static HttpResultModel doDownLoadRequest(RequestInfoModel requestInfoModel, HttpMarsRequest request) throws Exception {
        return doRequest(requestInfoModel, request, HttpResultModel.class);
    }

    /**
     * 发起请求
     * @param requestInfoModel
     * @param request
     * @param resultType
     * @return
     * @throws Exception
     */
    private static <T> T doRequest(RequestInfoModel requestInfoModel, HttpMarsRequest request, Class<T> resultType) throws Exception {
        Object params = ParamConversionToModel.paramConversionToMap(request);

        return MarsRestTemplate.request(requestInfoModel.getServerName(),
                requestInfoModel.getMethodName(),
                new Object[]{params},
                resultType,
                RequestUtil.getContentType(request));
    }
}

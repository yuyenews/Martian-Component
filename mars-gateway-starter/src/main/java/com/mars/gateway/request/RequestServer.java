package com.mars.gateway.request;

import com.mars.cloud.request.rest.request.MarsRestTemplate;
import com.mars.cloud.request.util.model.HttpResultModel;
import com.mars.cloud.request.util.model.MarsHeader;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.request.param.ParamConversionToModel;
import com.mars.gateway.request.util.RequestUtil;
import com.mars.server.server.request.HttpMarsRequest;
import com.sun.net.httpserver.Headers;

import java.util.List;

/**
 * 转发给对应的微服务
 */
public class RequestServer {

    /**
     * 发起请求
     *
     * @param requestInfoModel
     * @param request
     * @return
     * @throws Exception
     */
    public static HttpResultModel doRequest(RequestInfoModel requestInfoModel, HttpMarsRequest request) throws Exception {
        Object params = ParamConversionToModel.paramConversionToMap(request);

        MarsHeader header = getHeader(request);

        return MarsRestTemplate.request(requestInfoModel.getServerName(),
                requestInfoModel.getMethodName(),
                new Object[]{params},
                HttpResultModel.class,
                RequestUtil.getContentType(request), header);
    }

    /**
     * 获取请求头
     *
     * @param request
     * @return
     */
    private static MarsHeader getHeader(HttpMarsRequest request) {
        Headers headers = request.getHttpExchange().getRequestHeaders();
        if (headers == null || headers.size() < 1) {
            return null;
        }
        MarsHeader marsHeader = new MarsHeader();
        for (String name : headers.keySet()) {
            List<String> values = headers.get(name);
            if (values == null || values.size() < 1) {
                continue;
            }
            marsHeader.put(name, values);
        }
        return marsHeader;
    }
}

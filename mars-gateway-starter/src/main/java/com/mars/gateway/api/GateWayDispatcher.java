package com.mars.gateway.api;

import com.alibaba.fastjson.JSON;
import com.mars.cloud.constant.MarsCloudConstant;
import com.mars.cloud.request.util.model.HttpResultModel;
import com.mars.cloud.util.SerializableCloudUtil;
import com.mars.gateway.api.filter.GateFactory;
import com.mars.gateway.api.filter.GateFilter;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.core.util.RequestAndResultUtil;
import com.mars.gateway.request.RequestServer;
import com.mars.server.server.request.HttpMarsRequest;
import com.mars.server.server.request.HttpMarsResponse;
import com.mars.server.server.request.impl.HttpMarsDefaultRequest;
import com.mars.server.server.request.impl.HttpMarsDefaultResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 核心控制器，用来实现转发和响应
 */
public class GateWayDispatcher implements HttpHandler {

    private Logger log = LoggerFactory.getLogger(GateWayDispatcher.class);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            HttpMarsRequest request = new HttpMarsDefaultRequest(httpExchange);
            HttpMarsResponse response = new HttpMarsDefaultResponse(httpExchange);

            // 执行过滤器
            Object result = execFilter(request, response);
            if(result != null && !result.toString().equals(GateFilter.SUCCESS)){
                response.send(JSON.toJSONString(result));
                return;
            }

            String requestUri = request.getUrl();
            RequestInfoModel requestInfoModel = RequestAndResultUtil.getServerNameAndMethodName(requestUri);

            HttpResultModel httpResultModel = RequestServer.doRequest(requestInfoModel, request);
            String fileName = getFileName(httpResultModel);
            if(fileName.equals(MarsCloudConstant.RESULT_FILE_NAME)){
                Object object = SerializableCloudUtil.deSerialization(httpResultModel.getInputStream(), Object.class);
                response.send(JSON.toJSONString(object));
            } else {
                response.downLoad(fileName, httpResultModel.getInputStream());
            }
        } catch (Exception e) {
            log.error("处理请求失败!", e);
            RequestAndResultUtil.send(httpExchange,"处理请求发生错误"+e.getMessage());
        }
    }

    /**
     * 执行过滤器
     * @param request
     * @param response
     * @return
     */
    private Object execFilter(HttpMarsRequest request, HttpMarsResponse response){
        List<GateFilter> gateFilterList = GateFactory.getGateFilter();
        if(gateFilterList != null && gateFilterList.size() > 0){
            for(GateFilter gateFilter : gateFilterList){
                Object result = gateFilter.doFilter(request, response);
                if(result != null && !result.toString().equals(GateFilter.SUCCESS)){
                    return result;
                }
            }
        }
        return GateFilter.SUCCESS;
    }

    /**
     * 获取文件名称
     * @param httpResultModel
     * @return
     */
    private String getFileName(HttpResultModel httpResultModel){
        String fileName = httpResultModel.getFileName();
        if(fileName == null){
            fileName = UUID.randomUUID().toString();
        } else {
            String[] fileNames = fileName.split("=");
            if(fileNames == null || fileNames.length < 2){
                fileName = UUID.randomUUID().toString();
            } else {
                fileName = fileNames[1];
            }
        }
        return fileName;
    }
}

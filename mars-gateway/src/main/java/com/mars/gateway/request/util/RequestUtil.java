package com.mars.gateway.request.util;

import com.mars.cloud.annotation.enums.ContentType;
import com.mars.gateway.request.param.ParamTypeConstant;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class RequestUtil {

    /**
     * 获取内容类型
     * @param httpExchange
     * @return
     */
    public static ContentType getContentType(HttpExchange httpExchange){
        if(httpExchange.getRequestMethod().toUpperCase().equals("GET")){
            return ContentType.FORM;
        } else {
            String contentStr = getContentTypeStr(httpExchange);
            if(ParamTypeConstant.isUrlEncoded(contentStr)){
                return ContentType.FORM;
            } else if(ParamTypeConstant.isFormData(contentStr)){
                return ContentType.FORM_DATA;
            } else if(ParamTypeConstant.isJSON(contentStr)){
                return ContentType.JSON;
            }
        }
        return ContentType.FORM;
    }

    /**
     * 获取参数类型
     * @return 参数类型
     */
    private static String getContentTypeStr(HttpExchange httpExchange){
        try {
            List<String> ctList = httpExchange.getRequestHeaders().get("Content-type");
            if(ctList == null || ctList.size() < 1){
                return "N";
            }
            return ctList.get(0).trim().toLowerCase();
        } catch (Exception e){
            return "N";
        }
    }
}

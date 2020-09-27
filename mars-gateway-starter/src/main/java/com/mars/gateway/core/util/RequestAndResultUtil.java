package com.mars.gateway.core.util;

import com.mars.common.util.StringUtil;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.api.util.DispatcherUtil;
import com.mars.server.server.request.HttpMarsResponse;
import com.sun.net.httpserver.HttpExchange;


/**
 * 请求和响应工具类
 */
public class RequestAndResultUtil {

    public static final String ROUTER = "/router";
    public static final String DOWNLOAD = "/download";

    /**
     * 从请求中解析出要转发的服务名和方法名
     * @param url
     * @return
     * @throws Exception
     */
    public static RequestInfoModel getServerNameAndMethodName(String url) throws Exception {
        url = DispatcherUtil.getUriName(url);

        url = url.replace(ROUTER,"")
                .replace(DOWNLOAD,"");

        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        if (StringUtil.isNull(url)) {
            throw new Exception("请求路径有误");
        }

        String[] urls = url.split("/");
        if (urls == null || urls.length < 2) {
            throw new Exception("请求路径有误");
        }

        RequestInfoModel requestInfoModel = new RequestInfoModel();
        requestInfoModel.setServerName(urls[0]);
        requestInfoModel.setMethodName(urls[1]);
        return requestInfoModel;
    }

    /**
     * 响应数据
     *
     * @param context 消息
     */
    public static void send(HttpExchange httpExchange, String context) {
        HttpMarsResponse response = new HttpMarsResponse(httpExchange);
        response.send(context);
    }
}

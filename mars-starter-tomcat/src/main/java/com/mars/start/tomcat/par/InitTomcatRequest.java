package com.mars.start.tomcat.par;

import com.mars.iserver.par.InitRequest;
import com.mars.server.server.request.HttpMarsRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

public class InitTomcatRequest implements InitRequest {

    public HttpMarsRequest getHttpMarsRequest(HttpMarsRequest httpMarsRequest) throws Exception {
        HttpServletRequest request = httpMarsRequest.getNativeRequest(HttpServletRequest.class);
        InputStream inputStream = request.getInputStream();

        // TODO 从请求中获取参数，赋值给httpMarsRequest后返回httpMarsRequest

        return httpMarsRequest;
    }
}

package com.mars.start.tomcat.server.servlet;

import com.mars.common.constant.MarsConstant;
import com.mars.iserver.execute.RequestExecute;
import com.mars.iserver.server.MarsServerHandler;
import com.mars.iserver.util.ResponseUtil;
import com.mars.server.server.request.HttpMarsRequest;
import com.mars.server.server.request.HttpMarsResponse;
import com.mars.start.tomcat.server.request.HttpMarsTomcatRequest;
import com.mars.start.tomcat.server.request.HttpMarsTomcatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 核心servlet
 */
public class MarsTomcatServlet extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(MarsServerHandler.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(MarsConstant.ENCODING);
        resp.setCharacterEncoding(MarsConstant.ENCODING);

        /* 组装httpRequest对象 */
        HttpMarsRequest request = new HttpMarsTomcatRequest(req);;

        /* 组装httpResponse对象 */
        HttpMarsResponse response = new HttpMarsTomcatResponse(resp);

        try {
            RequestExecute.execute(request, response);
        } catch (Exception e) {
            log.error("处理请求失败!", e);
            ResponseUtil.sendServerError(response,"处理请求发生错误"+e.getMessage());
        }
    }
}

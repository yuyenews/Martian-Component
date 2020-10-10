package com.mars.start.tomcat.server;

import com.mars.common.constant.MarsConstant;
import com.mars.common.constant.MarsSpace;
import com.mars.iserver.server.MarsServer;
import com.mars.start.tomcat.server.servlet.MarsTomcatServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * tomcat服务
 */
public class MarsTomcatServer implements MarsServer {

    private static Logger log = LoggerFactory.getLogger(MarsServer.class);

    public void start(int port) {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(port);
            tomcat.setBaseDir(".");

            final Context context = tomcat.addContext("/", null);
            Tomcat.addServlet(context, "dispatcher", new MarsTomcatServlet());
            context.addServletMappingDecoded("/*", "dispatcher");

            tomcat.init();
            tomcat.start();

            /* 标识服务是否已经启动 */
            MarsSpace.getEasySpace().setAttr(MarsConstant.HAS_SERVER_START, "yes");
            log.info("启动成功");

            //开启异步服务，接收请求
            tomcat.getServer().await();
        } catch (Exception e) {
            log.error("启动tomcat报错", e);
        }
    }
}

package com.mars.start.tomcat.starter;

import com.mars.common.annotation.bean.MarsOnLoad;
import com.mars.common.base.BaseOnLoad;
import com.mars.iserver.par.factory.InitRequestFactory;
import com.mars.iserver.server.factory.MarsServerFactory;
import com.mars.start.tomcat.par.InitTomcatRequest;
import com.mars.start.tomcat.server.MarsTomcatServer;

/**
 * 项目启动事件
 */
@MarsOnLoad
public class MarsTomcatLoad implements BaseOnLoad {

    /**
     * 设置要用的服务和参数解析器
     * @throws Exception
     */
    public void before() throws Exception {
        MarsServerFactory.setMarsServer(new MarsTomcatServer());
        InitRequestFactory.setInitRequest(new InitTomcatRequest());
    }

    public void after() throws Exception {
    }
}

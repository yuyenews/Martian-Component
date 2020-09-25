package com.mars.gateway.core.server;

import com.mars.common.base.config.model.ThreadPoolConfig;
import com.mars.gateway.api.GateWayDispatcher;
import com.mars.gateway.config.MarsGateWayConfig;
import com.mars.gateway.core.server.thread.ThreadPool;
import com.mars.gateway.core.util.GateWayConfigUtil;
import com.mars.gateway.starter.StartGateWay;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * HTTP服务
 */
public class GateServer {

    private static Logger logger = LoggerFactory.getLogger(StartGateWay.class);

    /**
     * 启动一个Http服务
     * @param port
     * @throws Exception
     */
    public static void startServer(int port) throws Exception {
        MarsGateWayConfig marsConfig = GateWayConfigUtil.getMarsGateWayConfig();
        ThreadPoolConfig threadPoolConfig = marsConfig.getThreadPoolConfig();

        /* 创建服务 */
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), threadPoolConfig.getBackLog());
        httpServer.createContext("/", new GateWayDispatcher());
        /* 设置服务器的线程池对象 */
        httpServer.setExecutor(ThreadPool.getThreadPoolExecutor());

        logger.info("网关启动成功......");

        /* 启动服务器 */
        httpServer.start();
    }
}

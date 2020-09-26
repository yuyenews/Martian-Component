package com.mars.gateway.core.util;

import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.constant.MarsConstant;
import com.mars.common.util.StringUtil;
import com.mars.gateway.api.GateWayDispatcher;
import com.mars.gateway.api.model.RequestInfoModel;
import com.mars.gateway.api.util.DispatcherUtil;
import com.mars.gateway.config.MarsGateWayConfig;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 请求和响应工具类
 */
public class RequestAndResultUtil {

    private static Logger log = LoggerFactory.getLogger(GateWayDispatcher.class);

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
        OutputStream out = null;
        try {
            crossDomain(httpExchange);

            /* 设置响应头，必须在sendResponseHeaders方法之前设置 */
            httpExchange.getResponseHeaders().add("Content-Type:", "text/json;charset="+ MarsConstant.ENCODING);

            /* 设置响应码和响应体长度，必须在getResponseBody方法之前调用 */
            byte[] responseContentByte = context.getBytes(MarsConstant.ENCODING);
            httpExchange.sendResponseHeaders(200, responseContentByte.length);

            out = httpExchange.getResponseBody();
            out.write(responseContentByte);
        } catch (Exception e){
            log.error("响应数据异常",e);
        } finally {
            if (out != null){
                try{
                    out.flush();
                    out.close();
                } catch (Exception e){
                }
            }
        }
    }

    /**
     * 文件下载
     * @param fileName
     * @param inputStream
     */
    public static void downLoad(HttpExchange httpExchange, String fileName, InputStream inputStream) {
        OutputStream out = null;
        try {
            if(fileName == null || inputStream == null){
                log.error("downLoad方法的传参不可以为空");
                return;
            }
            crossDomain(httpExchange);
            httpExchange.getResponseHeaders().add("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName,MarsConstant.ENCODING));

            int len=0;
            byte[] buffer = new byte[1024];

            //设置响应码和响应体长度
            httpExchange.sendResponseHeaders(200, inputStream.available());

            out = httpExchange.getResponseBody();
            while((len=inputStream.read(buffer))!=-1){
                out.write(buffer, 0, len);
            }
        } catch (Exception e){
            log.error("响应数据异常",e);
        } finally {
            try{
                if(out != null){
                    out.flush();
                    out.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (Exception e){
            }
        }
    }

    /**
     * 设置跨域
     */
    private static void crossDomain(HttpExchange httpExchange) {
        MarsGateWayConfig marsConfig = GateWayConfigUtil.getMarsGateWayConfig();
        CrossDomainConfig crossDomainConfig = marsConfig.crossDomainConfig();
        httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", crossDomainConfig.getOrigin());
        httpExchange.getResponseHeaders().set("Access-Control-Allow-Methods", crossDomainConfig.getMethods());
        httpExchange.getResponseHeaders().set("Access-Control-Max-Age", crossDomainConfig.getMaxAge());
        httpExchange.getResponseHeaders().set("Access-Control-Allow-Headers", crossDomainConfig.getHeaders());
        httpExchange.getResponseHeaders().set("Access-Control-Allow-Credentials", crossDomainConfig.getCredentials());
    }
}

package com.mars.start.tomcat.server.request;

import com.mars.common.base.config.MarsConfig;
import com.mars.common.base.config.model.CrossDomainConfig;
import com.mars.common.constant.MarsConstant;
import com.mars.common.util.MarsConfiguration;
import com.mars.server.server.request.HttpMarsResponse;
import com.mars.server.server.request.impl.HttpMarsDefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * 将tomcat的response转成Martian的response
 */
public class HttpMarsTomcatResponse extends HttpMarsResponse {

    private Logger logger = LoggerFactory.getLogger(HttpMarsDefaultResponse.class);

    private HttpServletResponse response;

    public HttpMarsTomcatResponse(HttpServletResponse response){
        this.response = response;
    }

    /**
     * 获取原生的response
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> T geNativeResponse(Class<T> aClass) {
        return (T)response;
    }

    /**
     * 设置响应头
     * @param key
     * @param val
     */
    public void setHeader(String key, String val) {
        response.setHeader(key, val);
    }

    /**
     * 响应数据
     * @param s
     */
    public void send(String s) {
        PrintWriter out = null;
        try {
            crossDomain();

            /* 设置响应头 */
            response.setContentType("text/json;charset=" + MarsConstant.ENCODING);
            out = response.getWriter();
            out.write(s);
        } catch (Exception e){
            logger.error("响应数据异常",e);
        } finally {
            if(out != null){
                out.close();
            }
        }
    }

    /**
     * 下载文件
     * @param fileName
     * @param inputStream
     */
    public void downLoad(String fileName, InputStream inputStream) {
        OutputStream out = null;
        try {
            if(fileName == null || inputStream == null){
                logger.error("downLoad方法的传参不可以为空");
                return;
            }
            crossDomain();
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName,MarsConstant.ENCODING));

            int len=0;
            byte[] buffer = new byte[1024];

            out = response.getOutputStream();
            while((len=inputStream.read(buffer))!=-1){
                out.write(buffer, 0, len);
            }
        } catch (Exception e){
            logger.error("响应数据异常",e);
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
    private void crossDomain() {
        MarsConfig marsConfig = MarsConfiguration.getConfig();
        CrossDomainConfig crossDomainConfig = marsConfig.crossDomainConfig();
        response.setHeader("Access-Control-Allow-Origin", crossDomainConfig.getOrigin());
        response.setHeader("Access-Control-Allow-Methods", crossDomainConfig.getMethods());
        response.setHeader("Access-Control-Max-Age", crossDomainConfig.getMaxAge());
        response.setHeader("Access-Control-Allow-Headers", crossDomainConfig.getHeaders());
        response.setHeader("Access-Control-Allow-Credentials", crossDomainConfig.getCredentials());
    }
}

package com.mars.start.tomcat.server.request;

import com.mars.server.server.request.HttpMarsRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 将tomcat的request转成Martian的request
 */
public class HttpMarsTomcatRequest extends HttpMarsRequest {

    private HttpServletRequest request;

    public HttpMarsTomcatRequest(HttpServletRequest request){
        this.request = request;
    }

    /**
     * 获取原生request对象
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> T getNativeRequest(Class<T> aClass) {
        return (T)request;
    }

    /**
     * 获取内容类型
     * @return
     */
    public String getContentType() {
        try {
            if(getMethod().toUpperCase().equals("GET")){
                return "N";
            }
            return request.getContentType().trim().toLowerCase();
        } catch (Exception e){
            return "N";
        }
    }

    /**
     * 获取请求方法
     * @return
     */
    public String getMethod() {
        return request.getMethod();
    }

    /**
     * 获取请求的路径
     * @return
     */
    public String getUrl() {
        return request.getRequestURI();
    }

    /**
     * 获取请求头
     * @param s
     * @return
     */
    public String getHeader(String s) {
        return request.getHeader(s);
    }

    /**
     * 获取请求头
     * @param s
     * @return
     */
    public List<String> getHeaders(String s) {
        List<String> values = new ArrayList<String>();
        Enumeration<String> headers = request.getHeaders(s);
        while (headers.hasMoreElements()){
            String value = headers.nextElement();
            values.add(value);
        }
        return values;
    }

    /**
     * 获取客户端地址
     * @return
     */
    public String getInetSocketAddress() {
        StringBuffer result = new StringBuffer();
        result.append(request.getRemoteAddr());
        result.append(":");
        result.append(request.getRemotePort());

        return result.toString();
    }
}

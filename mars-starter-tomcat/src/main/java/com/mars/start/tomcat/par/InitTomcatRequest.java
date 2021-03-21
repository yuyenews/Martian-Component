package com.mars.start.tomcat.par;

import com.mars.common.constant.MarsConstant;
import com.mars.iserver.constant.ParamTypeConstant;
import com.mars.iserver.par.InitRequest;
import com.mars.iserver.par.formdata.ParsingFormData;
import com.mars.server.server.request.HttpMarsRequest;
import com.mars.server.server.request.model.MarsFileUpLoad;
import org.apache.commons.fileupload.UploadContext;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 初始化request中的数据
 */
public class InitTomcatRequest implements InitRequest {

    /**
     * 从请求中获取参数
     * @param httpMarsRequest
     * @return
     * @throws Exception
     */
    public HttpMarsRequest getHttpMarsRequest(HttpMarsRequest httpMarsRequest) throws Exception {
        Map<String, MarsFileUpLoad> files = new HashMap<String, MarsFileUpLoad>();
        Map<String, List<String>> marsParams = new HashMap<String, List<String>>();

        HttpServletRequest request = httpMarsRequest.getNativeRequest(HttpServletRequest.class);

        if (request.getMethod().toUpperCase().equals("GET")) {
            /* 从get请求中获取参数 */
            marsParams = urlencoded(request);
        } else {
            /* 从非GET请求中获取参数 */
            InputStream inputStream = request.getInputStream();
            if (inputStream == null) {
                return httpMarsRequest;
            }

            /* 根据提交方式，分别处理参数 */
            String contentType = httpMarsRequest.getContentType();
            if (ParamTypeConstant.isUrlEncoded(contentType)) {
                /* 正常的表单提交 */
                marsParams = urlencoded(request);
            } else if (ParamTypeConstant.isFormData(contentType)) {
                /* formData提交，可以用于文件上传 */
                Map<String, Object> result = formData(request);
                files = (Map<String, MarsFileUpLoad>) result.get(ParsingFormData.FILES_KEY);
                marsParams = (Map<String, List<String>>) result.get(ParsingFormData.PARAMS_KEY);
            } else if (ParamTypeConstant.isJSON(contentType)) {
                /* RAW提交(json) */
                String jsonParams = raw(inputStream);
                httpMarsRequest.setJsonParam(jsonParams);
            }
        }
        /* 将提取出来的参数，放置到HttpMarsRequest中 */
        httpMarsRequest.setFiles(files);
        httpMarsRequest.setParams(marsParams);

        return httpMarsRequest;
    }

    /**
     * 获取表单提交的数据
     * @param request
     * @return
     */
    private Map<String, List<String>> urlencoded(HttpServletRequest request){
        Map<String, List<String>> paramsMap = new HashMap<String, List<String>>();
        Map<String, String[]> paramList = request.getParameterMap();

        if(paramList == null || paramList.size() < 1){
            return paramsMap;
        }

        for(String key : paramList.keySet()){
            String[] paramValues = paramList.get(key);
            if(paramValues == null) {
                continue;
            }
            List<String> params = new ArrayList<String>();
            for(String val : paramValues){
                params.add(val);
            }
            paramsMap.put(key, params);
        }
        return paramsMap;
    }

    /**
     * RAW提交处理
     * @param inputStream 输入流
     * @return httpMarsRequest的参数对象
     * @throws Exception 异常
     */
    private String raw(InputStream inputStream) throws Exception {
        String paramStr = getParamStr(inputStream);
        if(paramStr == null || paramStr.trim().equals("")){
            return null;
        }
        return paramStr;
    }

    /**
     * 从输入流里面读取所有的数据
     * @param inputStream 输入流
     * @return 数据
     * @throws Exception 异常
     */
    private String getParamStr(InputStream inputStream) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, MarsConstant.ENCODING));
        String line = null;
        StringBuffer paramsStr = new StringBuffer();
        while ((line = br.readLine()) != null) {
            paramsStr.append(line);
        }
        return paramsStr.toString();
    }

    /**
     * 从formData中获取参数
     * @param request
     * @return
     * @throws Exception
     */
    private Map<String, Object> formData(HttpServletRequest request) throws Exception {
        UploadContext uploadContext = new HttpServletRequestContext(request);
        return ParsingFormData.parsing(uploadContext);
    }
}

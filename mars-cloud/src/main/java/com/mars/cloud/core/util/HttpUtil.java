package com.mars.cloud.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mars.cloud.core.annotation.enums.ContentType;
import com.mars.cloud.core.cache.model.RestApiCacheModel;
import com.mars.cloud.core.constant.MarsCloudConstant;
import com.mars.cloud.request.rest.model.RequestParamModel;
import com.mars.cloud.request.rest.util.ParamConversionUtil;
import com.mars.common.annotation.enums.ReqMethod;
import com.mars.common.util.SerializableUtil;
import com.mars.server.server.request.model.MarsFileUpLoad;
import okhttp3.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HTTP请求工具类
 */
public class HttpUtil {

    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    private static final String FORM_DATA = "multipart/form-data";

    /**
     * 发起请求
     * @param restApiCacheModel
     * @param params
     * @return
     */
    public static InputStream request(RestApiCacheModel restApiCacheModel, Object[] params, ContentType contentType) throws Exception{
        if(restApiCacheModel.getReqMethod().equals(ReqMethod.GET)){
            return formGet(restApiCacheModel, params);
        } else {
            if(contentType.equals(ContentType.FORM.getCode())){
                return formPost(restApiCacheModel,params);
            } else if(contentType.equals(ContentType.FORM_DATA.getCode())){
                return formData(restApiCacheModel,params);
            } else if(contentType.equals(ContentType.JSON.getCode())){
                return json(restApiCacheModel,params);
            } else {
                throw new Exception("请求的接口，ContentType未知，接口名:" + restApiCacheModel.getUrl());
            }
        }
    }

    /**
     * formData提交
     * @param restApiModel
     * @param params
     * @return
     * @throws Exception
     */
    private static InputStream formData(RestApiCacheModel restApiModel, Object[] params) throws Exception {

        OkHttpClient okHttpClient = getOkHttpClient();

        /* 发起post请求 将数据传递过去 */
        MediaType formData = MediaType.parse(FORM_DATA);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(formData);

        /* 将参数转成统一规格的对象，进行下面的传参操作 */
        Map<String, RequestParamModel> requestParamModelMap = ParamConversionUtil.getRequestParamModelList(params);
        for(RequestParamModel requestParamModel : requestParamModelMap.values()){
            if(requestParamModel.isFile()){
                // 如果是文件
                Map<String, MarsFileUpLoad> marsFileUpLoadMap = requestParamModel.getMarsFileUpLoads();

                for(MarsFileUpLoad marsFileUpLoad : marsFileUpLoadMap.values()){
                    byte[] file = ParamConversionUtil.toByteArray(marsFileUpLoad.getInputStream());

                    RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));
                    builder.addFormDataPart(requestParamModel.getName(), marsFileUpLoad.getFileName(), fileBody);
                }
            } else {
                // 如果不是文件
                builder.addFormDataPart(requestParamModel.getName(), requestParamModel.getValue().toString());
            }
        }

        Request request = new Request.Builder()
                .post(builder.build())
                .url(restApiModel.getUrl())
                .build();

        return okCall(okHttpClient, request);
    }

    /**
     * post表单提交
     * @param restApiModel
     * @param params
     * @return
     */
    private static InputStream formPost(RestApiCacheModel restApiModel, Object[] params) throws Exception {
        OkHttpClient okHttpClient = getOkHttpClient();

        JSONObject jsonParam = ParamConversionUtil.conversionToJson(params);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for(String key : jsonParam.keySet()) {
            formBodyBuilder.add(key,jsonParam.getString(key));
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request
                .Builder()
                .post(formBody)
                .url(restApiModel.getUrl())
                .build();

        return okCall(okHttpClient, request);
    }

    /**
     * get表单提交
     * @param restApiModel
     * @param params
     * @return
     */
    private static InputStream formGet(RestApiCacheModel restApiModel, Object[] params) throws Exception {
        OkHttpClient okHttpClient = getOkHttpClient();

        JSONObject jsonParam = ParamConversionUtil.conversionToJson(params);

        StringBuffer paramStr = new StringBuffer();

        boolean isFirst = true;
        for(String key : jsonParam.keySet()) {
            if(isFirst){
                paramStr.append("?");
            } else {
                paramStr.append("&");
            }
            paramStr.append(key);
            paramStr.append("=");
            paramStr.append(jsonParam.getString(key));

            isFirst = false;
        }

        Request request = new Request
                .Builder()
                .get()
                .url(restApiModel.getUrl() + paramStr.toString())
                .build();

        return okCall(okHttpClient, request);
    }

    /**
     * json提交
     * @param restApiModel
     * @param params
     * @return
     */
    private static InputStream json(RestApiCacheModel restApiModel, Object[] params) throws Exception {
        String jsonStrParam = "{}";
        JSONObject jsonParam = ParamConversionUtil.conversionToJson(params);
        if (jsonParam != null) {
            jsonStrParam = jsonParam.toJSONString();
        }

        OkHttpClient okHttpClient = getOkHttpClient();

        MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);

        RequestBody requestbody = RequestBody.create(jsonStrParam, mediaType);
        Request request = new Request
                .Builder()
                .post(requestbody)
                .url(restApiModel.getUrl())
                .build();

        return okCall(okHttpClient, request);
    }

    /**
     * 开始请求
     *
     * @param okHttpClient 客户端
     * @param request      请求
     * @return 结果
     * @throws Exception 异常
     */
    private static InputStream okCall(OkHttpClient okHttpClient, Request request) throws Exception {
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        int code = response.code();
        ResponseBody responseBody = response.body();
        if (code != 200) {
            throw new Exception("请求接口出现异常:" + responseBody.string());
        }
        return responseBody.byteStream();
    }

    /**
     * 获取okHttp客户端
     *
     * @return 客户端
     * @throws Exception 异常
     */
    private static OkHttpClient getOkHttpClient() throws Exception {
        long timeOut = MarsCloudConfigUtil.getMarsCloudConfig().getCloudConfig().getTimeOut();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(timeOut, TimeUnit.SECONDS)//设置读取超时时间
                .build();
        return okHttpClient;
    }
}

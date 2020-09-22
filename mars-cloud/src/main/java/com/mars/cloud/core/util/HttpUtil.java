package com.mars.cloud.core.util;

import com.mars.cloud.core.annotation.enums.ContentType;
import com.mars.cloud.core.cache.model.RestApiCacheModel;
import com.mars.cloud.core.constant.MarsCloudConstant;
import com.mars.common.annotation.enums.ReqMethod;
import com.mars.common.util.SerializableUtil;
import okhttp3.*;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * HTTP请求工具类
 */
public class HttpUtil {

    /**
     * 发起请求
     * @param restApiCacheModel
     * @param params
     * @return
     */
    public static InputStream request(RestApiCacheModel restApiCacheModel, Object[] params) throws Exception{
        if(restApiCacheModel.getReqMethod().equals(ReqMethod.GET)){
            return formGet(restApiCacheModel, params);
        } else {
            if(restApiCacheModel.getContentType().equals(ContentType.FORM.getCode())){
                return formPost(restApiCacheModel,params);
            } else if(restApiCacheModel.getContentType().equals(ContentType.FORM_DATA.getCode())){
                return formData(restApiCacheModel,params);
            } else if(restApiCacheModel.getContentType().equals(ContentType.JSON.getCode())){
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
        /* 将参数序列化成byte[] */
        byte[] param = SerializableUtil.serialization(params);

        OkHttpClient okHttpClient = getOkHttpClient();

        /* 发起post请求 将数据传递过去 */
        MediaType formData = MediaType.parse("multipart/form-data");

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(formData);
        // TODO
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), param);
        builder.addFormDataPart(MarsCloudConstant.PARAM, "params", fileBody);

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
    private static InputStream formPost(RestApiCacheModel restApiModel, Object[] params){
        // TODO
        return null;
    }

    /**
     * get表单提交
     * @param restApiModel
     * @param params
     * @return
     */
    private static InputStream formGet(RestApiCacheModel restApiModel, Object[] params){
        // TODO
        return null;
    }

    /**
     * json提交
     * @param restApiModel
     * @param params
     * @return
     */
    private static InputStream json(RestApiCacheModel restApiModel, Object[] params){
        // TODO
        return null;
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

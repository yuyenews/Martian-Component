package com.mars.cloud.main.core.util;

import com.mars.cloud.main.core.constant.MarsCloudConstant;
import com.mars.core.util.SerializableUtil;
import okhttp3.*;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * http请求工具类
 */
public class MarsCloudHttpUtil {

    /**
     * 超时时间
     */
    private static long timeOut;


    /**
     * 发起请求，以序列化方式传递数据
     *
     * @param url    路径
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     */
    public static InputStream request(String url, Object[] params) throws Exception {

        /* 将参数序列化成byte[] */
        byte[] param = SerializableUtil.serialization(params);

        OkHttpClient okHttpClient = getOkHttpClient();

        /* 发起post请求 将数据传递过去 */
        MediaType formData = MediaType.parse("multipart/form-data");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), param);
        MultipartBody body = new MultipartBody.Builder()
                .setType(formData)
                .addFormDataPart(MarsCloudConstant.PARAM, "params", fileBody)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
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
        init();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(timeOut, TimeUnit.SECONDS)//设置读取超时时间
                .build();
        return okHttpClient;
    }

    /**
     * 初始化timeOut
     *
     * @throws Exception 异常
     */
    private static void init() throws Exception {
        timeOut = MarsCloudConfigUtil.getMarsCloudConfig().getCloudConfig().getTimeOut();
    }
}

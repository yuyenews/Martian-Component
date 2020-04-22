package com.mars.cloud.main.rest.util;

import com.mars.cloud.main.core.constant.MarsCloudConstant;
import com.mars.core.constant.MarsConstant;
import com.mars.core.util.SerializableUtil;
import com.mars.netty.par.base.BaseParamAndResult;
import com.mars.server.server.request.HttpMarsRequest;
import com.mars.server.server.request.HttpMarsResponse;
import com.mars.server.server.request.model.MarsFileUpLoad;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 入参与响应数据
 */
public class MarsCloudParamAndResult implements BaseParamAndResult {

    /**
     * 获取参数
     * @param method 方法
     * @param request 请求
     * @param response 响应
     * @return 参数
     * @throws Exception 异常
     */
    @Override
    public Object[] getParam(Method method, HttpMarsRequest request, HttpMarsResponse response) throws Exception {
        try {
            Class[] paramTypes = method.getParameterTypes();
            if(paramTypes == null || paramTypes.length < 1){
                return null;
            }
            return getObject(request);
        } catch (Exception e){
            throw new Exception("参数注入异常",e);
        }
    }

    /**
     * 返回数据
     * @param response 响应
     * @param resultObj 返回的数据
     * @throws Exception 异常
     */
    @Override
    public void result(HttpMarsResponse response, Object resultObj) throws Exception {
        if(resultObj != null && resultObj.toString().equals(MarsConstant.VOID)) {
            throw new Exception("API的返回类型不可以为void");
        }
        byte[] bytes = SerializableUtil.serialization(resultObj);
        response.downLoad("blank",converToInputStream(bytes));
    }

    /**
     * 将序列化后的对象转成输入流
     * @param by
     * @return
     */
    private InputStream converToInputStream(byte[] by){
        InputStream swapStream = new ByteArrayInputStream(by);
        return swapStream;
    }

    /**
     * 获取参数并反序列化
     * @param request 请求
     * @return 对象
     * @throws Exception 异常
     */
    private Object[] getObject(HttpMarsRequest request) throws Exception {
        MarsFileUpLoad marsFileUpLoad = request.getFile(MarsCloudConstant.PARAM);
        if(marsFileUpLoad == null){
            return null;
        }
        InputStream inputStream = marsFileUpLoad.getInputStream();
        Object[] params = SerializableUtil.deSerialization(inputStream, Object[].class);
        if(params == null || params.length < 1){
            return null;
        }
        return params;
    }
}
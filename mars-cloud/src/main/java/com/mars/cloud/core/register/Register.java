package com.mars.cloud.core.register;

import com.mars.cloud.core.annotation.MarsContentType;
import com.mars.cloud.core.annotation.enums.ContentType;
import com.mars.cloud.core.register.model.RestApiModel;
import com.mars.cloud.core.util.MarsCloudUtil;
import com.mars.common.constant.MarsConstant;
import com.mars.common.constant.MarsSpace;
import com.mars.mvc.load.model.MarsMappingModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 往nacos注册服务
 */
public class Register {

    /**
     * 获取全局存储空间
     */
    private MarsSpace constants = MarsSpace.getEasySpace();

    public void doRegister(){
        
    }

    /**
     * 获取所有API
     * @return
     */
    private List<RestApiModel> getMarsApis() throws Exception {
        /* 从内存中获取本项目的MarsApi */
        Object apiMap = constants.getAttr(MarsConstant.CONTROLLER_OBJECTS);
        if (apiMap == null) {
            return new ArrayList<>();
        }

        List<RestApiModel> restApiModelList = new ArrayList<>();

        Map<String, MarsMappingModel> marsApiObjects = (Map<String, MarsMappingModel>) apiMap;
        for(String methodName : marsApiObjects.keySet()){
            MarsMappingModel marsMappingModel = marsApiObjects.get(methodName);
            if(marsMappingModel == null){
                continue;
            }
            RestApiModel restApiModel = new RestApiModel();

            MarsContentType marsContentType = marsMappingModel.getExeMethod().getAnnotation(MarsContentType.class);
            if(marsContentType == null){
                restApiModel.setContentType(ContentType.FORM.getCode());
            } else {
                restApiModel.setContentType(marsContentType.ContentType().getCode());
            }
            restApiModel.setUrl(MarsCloudUtil.getLocalHost() + "/" + methodName);
            restApiModel.setMethodName(methodName);
            restApiModel.setReqMethod(marsMappingModel.getReqMethod());
            restApiModelList.add(restApiModel);
        }

        return restApiModelList;
    }
}

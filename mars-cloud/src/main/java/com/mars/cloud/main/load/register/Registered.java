package com.mars.cloud.main.load.register;

import com.mars.cloud.main.core.constant.MarsCloudConstant;
import com.mars.cloud.main.core.util.MarsCloudConfigUtil;
import com.mars.cloud.main.core.util.MarsCloudUtil;
import com.mars.cloud.main.core.zookeeper.ZkHelper;
import com.mars.common.annotation.bean.MarsBean;
import com.mars.common.annotation.enums.ReqMethod;
import com.mars.common.constant.MarsConstant;
import com.mars.common.constant.MarsSpace;
import com.mars.mvc.model.MarsMappingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 注册接口
 */
@MarsBean("registered")
public class Registered {

    private Logger marsLogger = LoggerFactory.getLogger(Registered.class);

    /**
     * 获取全局存储空间
     */
    private MarsSpace constants = MarsSpace.getEasySpace();

    /**
     * 将接口注册到注册中心
     */
    public void registerApi() throws Exception {
        try {
            /* 打开zookeeper连接 */
            ZkHelper.openConnection();

            marsLogger.info("接口注册中.......");

            /* 获取本服务的名称 */
            String serverName = MarsCloudConfigUtil.getCloudName();
            /* 获取本服务IP */
            String ip = MarsCloudUtil.getLocalIp();
            /* 获取本服务端口 */
            String port = MarsCloudUtil.getPort();

            /* 从内存中获取本项目的MarsApi */
            Map<String, MarsMappingModel> marsApiObjects = getMarsApis();

            /* 将所有的接口注册到注册中心 */
            for (String methodName : marsApiObjects.keySet()) {
                MarsMappingModel marsMappingModel = marsApiObjects.get(methodName);
                checkMethod(marsMappingModel);

                /* 将本服务的接口写入zookeeper */
                String node = MarsCloudConstant.API_SERVER_NODE
                        .replace("{serverName}", serverName)
                        .replace("{method}", methodName)
                        .replace("{ip}", ip)
                        .replace("{port}", port);

                ZkHelper.createNodes(node, MarsCloudUtil.getLocalHost() + "/" + methodName);

                marsLogger.info("接口[" + MarsCloudUtil.getLocalHost() + "/" + methodName + "]注册成功");
            }
        } catch (Exception e){
            throw new Exception("注册与发布接口失败", e);
        }
    }

    /**
     * 获取所有API
     * @return
     */
    private Map<String, MarsMappingModel> getMarsApis(){
        /* 从内存中获取本项目的MarsApi */
        Object apiMap = constants.getAttr(MarsConstant.CONTROLLER_OBJECTS);
        if (apiMap == null) {
            return null;
        }
        Map<String, MarsMappingModel> marsApiObjects = (Map<String, MarsMappingModel>) apiMap;
        return marsApiObjects;
    }

    /**
     * 校验请求方式是否符合要求
     * @param marsMappingModel
     * @throws Exception
     */
    private void checkMethod(MarsMappingModel marsMappingModel) throws Exception {
        ReqMethod reqMethod = marsMappingModel.getReqMethod();
        if(!reqMethod.equals(ReqMethod.POST)){
            throw new Exception("MarsCloud的接口必须全部是POST请求");
        }
    }
}

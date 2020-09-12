package com.mars.cloud.main.core.after;

import com.mars.cloud.main.core.util.MarsCloudConfigUtil;
import com.mars.cloud.request.feign.load.LoadMarsFeign;
import com.mars.cloud.main.load.start.LoadStart;
import com.mars.cloud.request.rest.util.MarsCloudParamAndResult;
import com.mars.common.annotation.bean.MarsOnLoad;
import com.mars.common.annotation.bean.MarsWrite;
import com.mars.common.base.BaseOnLoad;
import com.mars.iserver.par.factory.ParamAndResultFactory;

/**
 * 在项目启动后加载Cloud配置数据
 */
@MarsOnLoad
public class StartCloudAfter implements BaseOnLoad {

    /**
     * 启动后加载
     */
    @MarsWrite("loadStart")
    private LoadStart loadStart;

    /**
     * 框架资源加载前
     * @throws Exception
     */
    @Override
    public void before() throws Exception {
        /* 指定 处理参数和响应的对象实例 */
        ParamAndResultFactory.setBaseParamAndResult(new MarsCloudParamAndResult());

        /* 加载Feign对象 */
        LoadMarsFeign.LoadCloudFeign();
    }

    /**
     * 框架启动后
     * @throws Exception
     */
    @Override
    public void after() throws Exception {
        Boolean getWay = MarsCloudConfigUtil.getMarsCloudConfig().getCloudConfig().getGateWay();
        if(!getWay) {
            /* 注册接口 */
            loadStart.loadApi();
        }
    }
}

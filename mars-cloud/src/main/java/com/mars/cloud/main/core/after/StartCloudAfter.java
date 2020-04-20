package com.mars.cloud.main.core.after;

import com.mars.cloud.main.core.util.MarsCloudConfigUtil;
import com.mars.cloud.main.load.start.LoadStart;
import com.mars.cloud.main.rest.util.MarsCloudParamAndResult;
import com.mars.core.annotation.MarsAfter;
import com.mars.core.annotation.MarsWrite;
import com.mars.core.base.BaseAfter;
import com.mars.netty.par.factory.ParamAndResultFactory;

/**
 * 在项目启动后加载Cloud配置数据
 */
@MarsAfter
public class StartCloudAfter implements BaseAfter {

    /**
     * 启动后加载
     */
    @MarsWrite("loadStart")
    private LoadStart loadStart;

    @Override
    public void after() throws Exception {
        Boolean getWay = MarsCloudConfigUtil.getMarsCloudConfig().getCloudConfig().getGateWay();
        if(getWay) {
            /* 指定 处理参数和响应的对象实例 */
            ParamAndResultFactory.setBaseParamAndResult(new MarsCloudParamAndResult());

            /* 注册接口 */
            loadStart.loadApi();
        }
    }
}

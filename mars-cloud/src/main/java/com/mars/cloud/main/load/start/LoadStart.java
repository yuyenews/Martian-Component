package com.mars.cloud.main.load.start;

import com.mars.cloud.main.load.register.Registered;
import com.mars.common.annotation.bean.MarsBean;
import com.mars.common.annotation.bean.MarsWrite;

/**
 * 加载
 */
@MarsBean("loadStart")
public class LoadStart {

    /**
     * 注册业务逻辑所在的类
     */
    @MarsWrite("registered")
    private Registered registered;

    /**
     * 注册接口
     */
    public void loadApi() throws Exception {
        registered.registerApi();
    }
}

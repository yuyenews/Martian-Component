package com.mars.cloud.main.load.start;

import com.mars.cloud.main.load.register.Registered;
import com.mars.common.annotation.bean.MarsBean;
import com.mars.common.annotation.bean.MarsWrite;

@MarsBean("loadStart")
public class LoadStart {

    @MarsWrite("registered")
    private Registered registered;

    /**
     * 注册接口
     */
    public void loadApi() throws Exception {
        registered.registerApi();
    }
}

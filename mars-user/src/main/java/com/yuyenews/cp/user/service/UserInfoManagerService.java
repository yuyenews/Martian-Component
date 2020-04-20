package com.yuyenews.cp.user.service;

import com.mars.core.annotation.MarsBean;
import com.mars.core.annotation.MarsWrite;
import com.yuyenews.cp.user.dao.UserInfoManagerDAO;

/**
 * 用户管理服务层
 */
@MarsBean("userInfoManagerService")
public class UserInfoManagerService {

    /**
     * 用户管理持久层
     */
    @MarsWrite
    private UserInfoManagerDAO userInfoManagerDAO;
}

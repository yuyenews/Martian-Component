package com.mars.cp.users.service;

import com.mars.core.annotation.MarsBean;
import com.mars.core.annotation.MarsWrite;
import com.mars.cp.users.dao.MarsUserLoginDAO;
import com.mars.cp.users.entity.biz.UserInfoDTO;
import com.mars.cp.users.entity.login.UserLoginDTO;
import com.mars.cp.users.util.MarsMD5Util;

/**
 * 用户登录
 */
@MarsBean("marsUserLoginService")
public class MarsUserLoginService {

    /**
     * 用户登录持久层
     */
    @MarsWrite
    private MarsUserLoginDAO marsUserLoginDAO;

    /**
     * 用户登录
     *
     * @param userLoginDTO 用户信息
     * @return 结果
     */
    public UserInfoDTO marsUsersLogin(UserLoginDTO userLoginDTO) {
        userLoginDTO.setUser_pwd(MarsMD5Util.MD5Encode(userLoginDTO.getUser_pwd()));
        return marsUserLoginDAO.login(userLoginDTO);
    }
}

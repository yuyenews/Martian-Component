package com.mars.cp.users.service;

import com.mars.core.annotation.MarsBean;
import com.mars.core.annotation.MarsWrite;
import com.mars.cp.base.entity.ResultVO;
import com.mars.cp.users.api.MarsUserLoginApi;
import com.mars.cp.users.dao.MarsUserLoginDAO;
import com.mars.cp.users.entity.UserInfoDTO;
import com.mars.cp.users.entity.UserLoginVO;
import com.mars.cp.users.util.MarsMD5Util;
import com.mars.server.server.jwt.JwtManager;

/**
 * 用户登录
 */
@MarsBean("marsUserLoginService")
public class MarsUserLoginService implements MarsUserLoginApi {

    /**
     * 用户登录持久层
     */
    @MarsWrite
    private MarsUserLoginDAO marsUserLoginDAO;

    /**
     * 用户登录
     *
     * @param userLoginVO 用户信息
     * @return 结果
     */
    @Override
    public ResultVO marsUsersLogin(UserLoginVO userLoginVO) {
        userLoginVO.setUser_pwd(MarsMD5Util.MD5Encode(userLoginVO.getUser_pwd()));

        UserInfoDTO userInfo = marsUserLoginDAO.login(userLoginVO);
        userInfo.setUser_pwd(null);
        String token = JwtManager.getJwtManager().createToken(userInfo);

        return ResultVO.success(userInfo).addResult("token", token);
    }
}

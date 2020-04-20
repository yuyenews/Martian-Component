package com.yuyenews.cp.user.service;

import com.mars.core.annotation.MarsBean;
import com.mars.core.annotation.MarsWrite;
import com.yuyenews.cp.core.vo.ResultVO;
import com.yuyenews.cp.user.api.UserInfoApi;
import com.yuyenews.cp.user.dao.UserInfoDAO;
import com.yuyenews.cp.user.model.UserInfoDTO;

/**
 * 用户服务层
 */
@MarsBean("userInfoService")
public class UserInfoService implements UserInfoApi {

    /**
     * 用户持久层
     */
    @MarsWrite
    private UserInfoDAO userInfoDAO;

    public ResultVO marsCpUserLogin(UserInfoDTO userInfoDTO) {
        return null;
    }

    public ResultVO marsCpUserRegistered(UserInfoDTO userInfoDTO) {
        return null;
    }

    public ResultVO marsCpGetUserInfo(UserInfoDTO userInfoDTO) {
        return null;
    }
}

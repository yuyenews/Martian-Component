package com.mars.cp.users.service;

import com.mars.core.annotation.MarsBean;
import com.mars.core.annotation.MarsWrite;
import com.mars.cp.base.entity.ResultVO;
import com.mars.cp.users.api.MarsUserInfoApi;
import com.mars.cp.users.dao.MarsUserInfoDAO;
import com.mars.cp.users.entity.UserGetVO;
import com.mars.cp.users.entity.UserInfoDTO;
import com.mars.cp.users.util.MarsMD5Util;

/**
 * 用户信息
 */
@MarsBean("marsUserInfoService")
public class MarsUserInfoService implements MarsUserInfoApi {

    /**
     * 用户信息持久层
     */
    @MarsWrite("marsUserInfoDAO")
    private MarsUserInfoDAO marsUserInfoDAO;

    /**
     * 根据ID获取用户信息
     *
     * @param userGetVO 用户信息
     * @return 结果
     */
    @Override
    public ResultVO marsUsersGetUserInfo(UserGetVO userGetVO) {
        UserInfoDTO userInfoDTO = marsUserInfoDAO.getUserInfo(userGetVO.getId());
        return ResultVO.success(userInfoDTO);
    }

    /**
     * 用户注册
     *
     * @param userInfoDTO 用户信息
     * @return 结果
     */
    public int marsUsersReg(UserInfoDTO userInfoDTO) {
        userInfoDTO.setUser_pwd(MarsMD5Util.MD5Encode(userInfoDTO.getUser_pwd()));
        return marsUserInfoDAO.addUserInfo(userInfoDTO);
    }
}

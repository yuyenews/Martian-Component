package com.yuyenews.cp.user.api;

import com.mars.core.annotation.MarsApi;
import com.mars.core.annotation.RequestMethod;
import com.mars.core.annotation.enums.ReqMethod;
import com.yuyenews.cp.core.vo.ResultVO;
import com.yuyenews.cp.user.model.UserInfoDTO;

/**
 * 用户API
 */
@MarsApi(refBean = "userInfoService")
public interface UserInfoApi {

    /**
     * 用户登录
     * @param userInfoDTO 参数
     * @return 响应
     */
    @RequestMethod(ReqMethod.POST)
    ResultVO marsCpUserLogin(UserInfoDTO userInfoDTO);

    /**
     * 用户注册
     * @param userInfoDTO 参数
     * @return 响应
     */
    @RequestMethod(ReqMethod.POST)
    ResultVO marsCpUserRegistered(UserInfoDTO userInfoDTO);

    /**
     * 获取用户信息
     * @param userInfoDTO 参数
     * @return 响应
     */
    @RequestMethod(ReqMethod.POST)
    ResultVO marsCpGetUserInfo(UserInfoDTO userInfoDTO);
}

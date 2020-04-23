package com.mars.cp.users.api;

import com.mars.core.annotation.MarsApi;
import com.mars.core.annotation.RequestMethod;
import com.mars.core.annotation.enums.ReqMethod;
import com.mars.cp.base.entity.ResultVO;
import com.mars.cp.users.entity.UserGetVO;

/**
 * 用户信息
 */
@MarsApi(refBean = "marsUserInfoService")
public interface MarsUserInfoApi {

    /**
     * 根据ID获取用户信息
     *
     * @param userGetVO 用户信息
     * @return 结果
     */
    @RequestMethod(ReqMethod.POST)
    ResultVO marsUsersGetUserInfo(UserGetVO userGetVO);
}

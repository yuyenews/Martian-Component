package com.mars.cp.users.api;

import com.mars.core.annotation.MarsApi;
import com.mars.core.annotation.RequestMethod;
import com.mars.core.annotation.enums.ReqMethod;
import com.mars.cp.base.entity.ResultVO;
import com.mars.cp.users.entity.UserLoginVO;

/**
 * 用户登录
 */
@MarsApi(refBean = "marsUserLoginService")
public interface MarsUserLoginApi {

    /**
     * 用户登录
     *
     * @param userLoginVO 用户信息
     * @return 结果
     */
    @RequestMethod(ReqMethod.POST)
    ResultVO marsUsersLogin(UserLoginVO userLoginVO);

}

package com.mars.cp.users.dao;

import com.mars.core.annotation.MarsDao;
import com.mars.cp.users.entity.UserInfoDTO;
import com.mars.cp.users.entity.UserLoginVO;
import com.mars.jdbc.annotation.MarsSelect;

/**
 * 用户登录
 */
@MarsDao
public abstract class MarsUserLoginDAO {

    /**
     * 用户登录
     *
     * @param userLoginVO 参数
     * @return 结果
     */
    @MarsSelect(sql = "select * from mcp_userinfo where (user_name = #{user_tag} or phone_no = #{user_tag} or mail_no = #{user_tag}) and user_pwd = #{user_pwd}", resultType = UserInfoDTO.class)
    public abstract UserInfoDTO login(UserLoginVO userLoginVO);
}

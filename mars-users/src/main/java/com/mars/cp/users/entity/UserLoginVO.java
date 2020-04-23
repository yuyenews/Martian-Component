package com.mars.cp.users.entity;

import com.mars.core.annotation.MarsDataCheck;

/**
 * 登录VO
 */
public class UserLoginVO {

    /**
     * 用户标识（用户名，手机号，邮箱）
     */
    @MarsDataCheck(notNull = true,msg = "请输入用户名或者手机号或者邮箱")
    private String user_tag;

    /**
     * 用户密码
     */
    @MarsDataCheck(notNull = true,msg = "请输入密码")
    private String user_pwd;

    public String getUser_tag() {
        return user_tag;
    }

    public void setUser_tag(String user_tag) {
        this.user_tag = user_tag;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }
}

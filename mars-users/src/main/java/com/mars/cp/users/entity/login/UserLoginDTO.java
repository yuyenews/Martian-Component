package com.mars.cp.users.entity.login;

/**
 * 登录VO
 */
public class UserLoginDTO {

    /**
     * 用户标识（用户名，手机号，邮箱）
     */
    private String user_tag;

    /**
     * 用户密码
     */
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

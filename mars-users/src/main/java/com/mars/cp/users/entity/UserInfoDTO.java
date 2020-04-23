package com.mars.cp.users.entity;

public class UserInfoDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 密码
     */
    private String user_pwd;

    /**
     * 手机号
     */
    private String phone_no;

    /**
     * 邮箱号
     */
    private String mail_no;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getMail_no() {
        return mail_no;
    }

    public void setMail_no(String mail_no) {
        this.mail_no = mail_no;
    }
}

package com.mars.cp.users.entity;

import com.mars.core.annotation.MarsDataCheck;

/**
 * 获取用户VO
 */
public class UserGetVO {

    /**
     * 主键
     */
    @MarsDataCheck(notNull = true, msg = "ID不可以为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

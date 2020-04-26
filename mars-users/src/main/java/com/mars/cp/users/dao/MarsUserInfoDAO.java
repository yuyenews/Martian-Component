package com.mars.cp.users.dao;

import com.mars.core.annotation.MarsDao;
import com.mars.cp.users.entity.biz.UserInfoDTO;
import com.mars.jdbc.annotation.MarsGet;
import com.mars.jdbc.annotation.MarsUpdate;
import com.mars.jdbc.annotation.enums.OperType;

/**
 * 用户信息持久层
 */
@MarsDao
public abstract class MarsUserInfoDAO {

    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 结果
     */
    @MarsGet(tableName = "mcp_userinfo", primaryKey = "id")
    public abstract UserInfoDTO getUserInfo(int id);

    /**
     * 添加用户
     *
     * @param userInfoDTO 参数
     * @return 结果
     */
    @MarsUpdate(tableName = "mcp_userinfo", operType = OperType.INSERT)
    public abstract int addUserInfo(UserInfoDTO userInfoDTO);
}

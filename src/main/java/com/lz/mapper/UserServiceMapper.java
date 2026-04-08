package com.lz.mapper;

import com.lz.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserServiceMapper {

    /**
     * 根据用户名查询用户信息，用于登录
     *
     * @param username 用户名
     * @return 用户信息；查无此人时返回 null
     */
    UserBean login(String username);

    /**
     * 注册用户，插入新用户到数据库
     *
     * @param userBean 用户信息
     * @return 影响行数
     */
    int register(UserBean userBean);
}

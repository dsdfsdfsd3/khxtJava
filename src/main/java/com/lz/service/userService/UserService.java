package com.lz.service.userService;

import com.lz.bean.Role;
import com.lz.bean.UserBean;



public interface UserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserBean login(String username);

    /**
     * 用户注册
     *
     * @param userBean 用户信息（用户名、密码）
     * @return 注册结果消息
     */
    String register(UserBean userBean);

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    Role getRoleById(Long roleId);

    /**
     * 根据角色名称查询角色信息
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    Role getRoleByRoleName(String roleName);
}

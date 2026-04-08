package com.lz.service.userService.Impl;

import com.lz.bean.Role;
import com.lz.bean.UserBean;
import com.lz.mapper.RoleMapper;
import com.lz.mapper.UserServiceMapper;
import com.lz.service.userService.UserService;
import com.lz.tool.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserServiceMapper userServiceMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserBean login(String username) {
        return userServiceMapper.login(username);
    }

    @Override
    public String register(UserBean userBean) {
        System.out.println("[UserServiceImpl.register] 开始注册用户: " + userBean.getUsername());
        
        try {
            // 1. 检查用户名是否已存在
            UserBean existUser = userServiceMapper.login(userBean.getUsername());
            if (existUser != null) {
                System.out.println("[UserServiceImpl.register] 用户名已存在: " + userBean.getUsername());
                return "用户名已存在";
            }

            // 2. 对密码进行 MD5 加密
            userBean.setPassword(MD5Util.md5(userBean.getPassword()));
            System.out.println("[UserServiceImpl.register] 密码已加密");

            // 3. 设置默认角色为普通用户 (role_id = 2，1 是管理员，2 是普通用户)
            if (userBean.getRoleId() == null) {
                userBean.setRoleId(2L);
            }
            System.out.println("[UserServiceImpl.register] 角色ID: " + userBean.getRoleId());

            // 4. 设置默认状态为正常 (1)
            if (userBean.getStatus() == null) {
                userBean.setStatus(1);
            }
            System.out.println("[UserServiceImpl.register] 状态: " + userBean.getStatus());

            // 5. 插入数据库
            userServiceMapper.register(userBean);
            System.out.println("[UserServiceImpl.register] 注册成功，用户ID: " + userBean.getId());
            
            return "注册成功";
        } catch (Exception e) {
            System.err.println("[UserServiceImpl.register] 注册失败: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleMapper.getRoleById(roleId);
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleMapper.getRoleByRoleName(roleName);
    }
}

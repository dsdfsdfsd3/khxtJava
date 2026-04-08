package com.lz.controller.user;


import com.lz.Dto.UserDto;
import com.lz.bean.Role;
import com.lz.bean.UserBean;
import com.lz.service.userService.UserService;
import com.lz.tool.JwtUtil;
import com.lz.tool.MD5Util;
import com.lz.tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

@Autowired
private UserService userService;

@Autowired
private JwtUtil jwtUtil;

    /**
     * 用户注册接口
     *
     * @param userBean 注册信息（用户名、密码、真实姓名）
     * @return 注册结果
     */
    @PostMapping("/regis")
    public Result register(@RequestBody UserBean userBean) {
        // 1. 基础参数校验
        if (userBean.getUsername() == null || userBean.getUsername().isEmpty() 
            || userBean.getPassword() == null || userBean.getPassword().isEmpty()) {
            return Result.error("用户名和密码不能为空");
        }

        // 2. 调用 service 层进行注册
        String result = userService.register(userBean);
        
        if ("注册成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 用户登录接口
     * 前端只传账号和密码，后端根据用户名+密码校验成功后，
     * 再根据数据库中的 role_id 字段判断该账号的角色，
     * 并把 role_id 写入 JWT，后续通过 token 进行权限控制。
     *
     * @param userDto 登录请求参数（用户名、密码）
     * @return 登录结果，成功时返回 token + 用户信息
     */
    @PostMapping("/login")
    public Result UserLogin(@RequestBody UserDto userDto){
        // 1. 基础参数校验
        String username = userDto.getUsername();
        String  password = userDto.getPassword();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            return Result.error("用户名或密码不能为空");
        }
        // 2. 根据用户名查询用户，并进行密码校验
        UserBean userBean =  userService.login(username);
        if (userBean != null){
            // 数据库中存的是 MD5 密文，这里对前端密码做同样的 MD5 处理再比对
            boolean flag = userBean.getPassword().equals(MD5Util.md5(password));
            if (flag){
                // 3. 不再从前端接收角色，直接使用数据库中保存的角色信息
                // 组装 JWT 中要保存的业务字段（后续拦截器会从 token 中取出 role_id 做权限判断）
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", userBean.getId());
                claims.put("username", userBean.getUsername());
                claims.put("roleId", userBean.getRoleId());
                String token = jwtUtil.createJWT(claims);

                // 为安全起见，响应中不返回密码字段
                userBean.setPassword(null);
                
                // 4. 查询完整角色信息
                Role role = userService.getRoleById(userBean.getRoleId());
                
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                data.put("user", userBean);
                data.put("role", role);
                return Result.success(data);
            }
        }
        // 用户不存在或密码错误统一返回登录失败
        return Result.error("用户名或密码错误");
    }
}

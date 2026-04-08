package com.lz.interceptor;

import com.lz.tool.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Arrays;
import java.util.List;


/*
 * 登录拦截器
 * 作用：
 *  1. 统一从请求头中获取并解析 JWT（Authorization: Bearer xxx）
 *  2. 解析成功后将 claims 放入 request，方便业务代码获取当前登录用户信息
 *  3. 简单演示基于角色的权限控制：/admin 开头的接口只允许角色为 admin 的用户访问
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 不需要拦截的路径（公开接口）
     * 注意：需要同时处理带 /api 前缀和不带前缀的路径
     */
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/user/login",
            "/api/user/login",
            "/user/regis",
            "/api/user/regis",
            "/user/register",
            "/api/user/register",
            "/user/logout",
            "/api/user/logout",
            "/user/forget",
            "/api/user/forget",
            "/error"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // 调试：打印实际收到的请求路径
        System.out.println("[LoginInterceptor] 收到请求: " + uri);
        System.out.println("[LoginInterceptor] 是否匹配排除路径: " + isExcludePath(uri));
        System.out.println("[LoginInterceptor] 排除路径列表: " + EXCLUDE_PATHS);

        // 1. 检查是否为公开接口（不需要 token）
        if (isExcludePath(uri)) {
            System.out.println("[LoginInterceptor] ✓ 放行（公开接口）");
            return true;
        }
        
        // 2. 从请求头获取前端携带的 JWT token（约定使用 Authorization 字段）
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录或token已过期\"}");
            return false;
        }
        
        // 兼容 "Bearer xxx" 格式，去掉前缀只保留真实 token
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        try {
            // 解析并验证 token，有异常会被 catch 捕获
            Map<String, Object> claims = jwtUtil.parseJWT(token);
            // 将解析出的业务数据挂到 request 上，后续 Controller 可以直接获取
            request.setAttribute("claims", claims);

            // 示例：以 /admin 开头的接口只允许管理员访问
            // 注意：现在 JWT 中使用的是 roleId 字段，1 表示管理员
            if (uri != null && uri.startsWith("/admin")) {
                Object roleId = claims.get("roleId");
                // roleId 为 1 表示管理员
                if (roleId == null || !Long.valueOf(1).equals(Long.valueOf(roleId.toString()))) {
                    // 当前登录用户不是管理员，禁止访问管理员接口
                    response.setStatus(403);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\":403,\"message\":\"权限不足，需要管理员权限\"}");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            // token 无效或已过期等情况，统一按未登录处理
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"token无效或已过期\"}");
            return false;
        }
    }
    
    /**
     * 判断当前请求路径是否在排除列表中的
     */
    private boolean isExcludePath(String uri) {
        return EXCLUDE_PATHS.contains(uri);
    }
}

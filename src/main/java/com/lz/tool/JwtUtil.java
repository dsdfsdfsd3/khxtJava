package com.lz.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lz.webConfig.JwtProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 基于Auth0 JWT库实现JWT令牌的生成（createJWT）和解析验证（parseJWT）功能
 * JWT（JSON Web Token）：用于在各方之间安全传输的紧凑、自包含的JSON格式令牌
 */
@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }


    /**
     * 生成JWT令牌
     * 核心逻辑：构建JWT对象 → 存入业务数据 → 设置过期时间 → 使用HMAC256算法签名 → 生成token字符串
     *
     * @param claims 要存入JWT的业务数据（自定义声明），如用户ID、手机号等键值对
     * @return 生成的JWT令牌字符串
     */
    public String createJWT(Map<String, Object> claims) {
        return JWT.create()
                // 向JWT中存入自定义业务数据（claims），key为"claims"，值为传入的业务数据Map
                .withClaim("claims", claims)
                // 设置JWT过期时间：当前时间 + 7天（1000毫秒*60秒*60分*24小时*7天）
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpireTime() ))
                // 使用HMAC256对称加密算法签名，密钥为JWT_KEY，确保令牌不被篡改
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    /**
     * 解析并验证JWT令牌
     * 核心逻辑：创建验证器 → 验证令牌的签名和有效期 → 解析令牌 → 获取存入的业务数据
     * 若令牌无效（签名错误、已过期等），会抛出对应的异常，需在调用处捕获
     *
     * @param token 待验证解析的JWT令牌字符串
     * @return 令牌中存储的业务数据（自定义声明）Map
     * @throws com.auth0.jwt.exceptions.JWTVerificationException 令牌验证失败时抛出（如签名错误、过期等）
     */
    public Map<String, Object> parseJWT(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey())) // 创建验证器，指定签名算法和密钥
                .build() // 构建验证器实例
                .verify(token) // 验证令牌的有效性（签名+有效期），验证失败会抛异常
                .getClaim("claims") // 获取令牌中key为"claims"的自定义声明
                .asMap(); // 将声明转换为Map类型返回
    }
}
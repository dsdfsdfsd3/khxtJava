package com.lz.webConfig;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data // 自动生成getter/setter，需确保已引入Lombok依赖
@Component // 交给Spring容器管理
@ConfigurationProperties(prefix = "lz.jwt") // 配置项前缀
public class JwtProperties {


    /**
     * JWT签名密钥（默认值防止配置缺失导致报错）
     */
    private String secretKey;


    private int expireTime  ;
}

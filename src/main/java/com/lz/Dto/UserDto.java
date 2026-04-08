package com.lz.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /**
     * 登录账号
     */
    private  String  username;
    /**
     * 登录密码（明文，接口中会进行MD5加密比对）
     */
    private  String  password;
}

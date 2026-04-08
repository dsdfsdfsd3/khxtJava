package com.lz.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 简化版MD5工具类（无加盐）
 */
public class MD5Util {
    // 固定UTF-8编码，避免哈希值不一致
    private static final String CHARSET = "UTF-8";

    /**
     * 基础MD5加密（无加盐）
     * @param input 要加密的字符串（比如密码）
     * @return 32位小写MD5哈希值
     */
    public static String md5(String input) {
        // 空值处理：防止空指针
        if (input == null || input.isEmpty()) {
            return "";
        }

        try {
            // 1. 获取MD5算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2. 将字符串转为字节数组并计算哈希
            byte[] digest = md.digest(input.getBytes(CHARSET));
            // 3. 字节数组转32位十六进制字符串
            StringBuilder hexStr = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                // 补0：保证单个字节转为2位十六进制
                if (hex.length() == 1) {
                    hexStr.append("0");
                }
                hexStr.append(hex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            // 算法不存在则抛出运行时异常（实际不会发生）
            throw new RuntimeException("MD5算法初始化失败", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // 测试示例：直接运行看效果

}
-- ============================================
-- 数据库完整初始化脚本（MySQL 8.0.42 兼容）
-- 数据库名：khxt
-- ============================================

-- 第1步：创建数据库
CREATE DATABASE IF NOT EXISTS `khxt` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- 第2步：使用数据库
USE `khxt`;

-- ============================================
-- 第3步：创建角色表（role）
-- ============================================
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称，如 admin、user',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- 第4步：插入默认角色数据
INSERT INTO `role` (`id`, `role_name`, `description`) VALUES
(1, 'admin', '系统管理员，拥有所有权限'),
(2, 'user', '普通用户，拥有基本权限');

-- ============================================
-- 第5步：创建用户表（sys_user）
-- ============================================
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '登录账号',
  `password` VARCHAR(100) NOT NULL COMMENT '密码（MD5加密）',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `role_id` BIGINT DEFAULT 2 COMMENT '角色ID，关联role表（1-管理员，2-普通用户）',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-正常 0-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_username` (`username`),
  KEY `idx_user_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ============================================
-- 第6步：验证数据
-- ============================================

-- 查看角色表数据
SELECT * FROM `role`;

-- 查看用户表结构
DESC `sys_user`;

-- 查看用户表数据（初始应该为空）
SELECT id, username, real_name, role_id, status, create_time FROM `sys_user`;

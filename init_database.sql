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

-- ============================================
-- 第7步：创建部门表（department）
-- ============================================
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` VARCHAR(100) NOT NULL COMMENT '部门名称',
  `dept_code` VARCHAR(50) DEFAULT NULL COMMENT '部门编码',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父部门ID，0表示顶级部门',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-正常 0-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dept_name` (`dept_name`),
  KEY `idx_dept_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

-- 插入默认部门数据
INSERT INTO `department` (`id`, `dept_name`, `dept_code`, `parent_id`, `sort_order`) VALUES
(1, '技术运营部', 'TECH', 0, 1),
(2, '人力资源部', 'HR', 0, 2),
(3, '财务部', 'FINANCE', 0, 3),
(4, '市场部', 'MARKET', 0, 4);

-- ============================================
-- 第8步：创建岗位表（position）
-- ============================================
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_name` VARCHAR(100) NOT NULL COMMENT '岗位名称',
  `post_code` VARCHAR(50) DEFAULT NULL COMMENT '岗位编码',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '岗位描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-正常 0-禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_post_name` (`post_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位表';

-- 插入默认岗位数据
INSERT INTO `position` (`post_name`, `post_code`, `description`, `sort_order`) VALUES
('部门经理', 'MANAGER', '负责部门日常管理工作', 1),
('高级工程师', 'SENIOR_DEV', '负责核心系统设计与开发', 2),
('工程师', 'DEVELOPER', '负责具体功能开发与维护', 3),
('测试工程师', 'TESTER', '负责系统测试与质量保障', 4),
('产品经理', 'PM', '负责产品规划与需求分析', 5);

-- ============================================
-- 第9步：创建员工表（employee）
-- ============================================
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `emp_no` VARCHAR(50) NOT NULL COMMENT '员工工号',
  `name` VARCHAR(50) NOT NULL COMMENT '员工姓名',
  `gender` TINYINT DEFAULT 1 COMMENT '性别：1-男 2-女',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
  `department_id` BIGINT DEFAULT NULL COMMENT '所属部门ID',
  `position_id` BIGINT DEFAULT NULL COMMENT '岗位ID',
  `entry_date` DATE DEFAULT NULL COMMENT '入职日期',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-在职 2-离职 3-休假',
  `address` VARCHAR(500) DEFAULT NULL COMMENT '家庭住址',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_emp_no` (`emp_no`),
  KEY `idx_emp_dept` (`department_id`),
  KEY `idx_emp_post` (`position_id`),
  KEY `idx_emp_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';

-- 插入示例员工数据
INSERT INTO `employee` (`emp_no`, `name`, `gender`, `phone`, `email`, `department_id`, `position_id`, `entry_date`, `status`) VALUES
('EMP001', '张三', 1, '13800138001', 'zhangsan@example.com', 1, 1, '2023-01-15', 1),
('EMP002', '李四', 2, '13800138002', 'lisi@example.com', 1, 2, '2023-03-20', 1),
('EMP003', '王五', 1, '13800138003', 'wangwu@example.com', 2, 1, '2022-11-10', 1),
('EMP004', '赵六', 2, '13800138004', 'zhaoliu@example.com', 3, 3, '2023-06-01', 1),
('EMP005', '孙七', 1, '13800138005', 'sunqi@example.com', 4, 5, '2023-08-15', 1);

-- ============================================
-- 第10步：验证数据
-- ============================================

-- 查看部门表数据
SELECT * FROM `department`;

-- 查看岗位表数据
SELECT * FROM `position`;

-- 查看员工表数据
SELECT * FROM `employee`;

-- =============================================
-- 员工管理、部门管理、岗位管理 数据库表初始化脚本
-- 创建时间：2026-04-08
-- =============================================

-- 使用数据库
USE khxt;

-- =============================================
-- 1. 部门表（department）
-- =============================================
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` VARCHAR(100) NOT NULL COMMENT '部门名称',
  `dept_code` VARCHAR(50) NOT NULL COMMENT '部门编码',
  `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父部门ID（0表示顶级部门）',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序号',
  `status` INT NOT NULL DEFAULT 1 COMMENT '状态（1-启用，2-停用）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dept_name` (`dept_name`),
  UNIQUE KEY `uk_dept_code` (`dept_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- =============================================
-- 2. 岗位表（position）
-- =============================================
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_name` VARCHAR(100) NOT NULL COMMENT '岗位名称',
  `post_code` VARCHAR(50) NOT NULL COMMENT '岗位编码',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '岗位描述',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序号',
  `status` INT NOT NULL DEFAULT 1 COMMENT '状态（1-启用，2-停用）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_post_name` (`post_name`),
  UNIQUE KEY `uk_post_code` (`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- =============================================
-- 3. 员工表（employee）
-- =============================================
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `emp_no` VARCHAR(50) NOT NULL COMMENT '工号',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `gender` INT NOT NULL DEFAULT 1 COMMENT '性别（1-男，2-女）',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '电话号码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `department_id` BIGINT NOT NULL COMMENT '部门ID',
  `position_id` BIGINT NOT NULL COMMENT '岗位ID',
  `role_id` BIGINT DEFAULT NULL COMMENT '角色ID',
  `entry_date` DATE DEFAULT NULL COMMENT '入职日期',
  `status` INT NOT NULL DEFAULT 1 COMMENT '状态（1-在职，2-离职，3-试用期）',
  `address` VARCHAR(200) DEFAULT NULL COMMENT '地址',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_emp_no` (`emp_no`),
  KEY `idx_name` (`name`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_position_id` (`position_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_employee_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_employee_position` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_employee_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- =============================================
-- 测试数据
-- =============================================

-- 插入部门测试数据
INSERT INTO `department` (`dept_name`, `dept_code`, `parent_id`, `sort_order`, `status`) VALUES
('总经办', 'ZJB', 0, 1, 1),
('技术部', 'JSB', 0, 2, 1),
('产品部', 'CPB', 0, 3, 1),
('市场部', 'SCB', 0, 4, 1),
('人力资源部', 'RLZYB', 0, 5, 1),
('财务部', 'CWB', 0, 6, 1),
('前端开发组', 'QDKFZ', 2, 1, 1),
('后端开发组', 'HDKFZ', 2, 2, 1),
('测试组', 'CSZ', 2, 3, 1);

-- 插入岗位测试数据
INSERT INTO `position` (`post_name`, `post_code`, `description`, `sort_order`, `status`) VALUES
('总经理', 'ZJL', '负责公司整体运营管理', 1, 1),
('技术总监', 'JSZJ', '负责技术架构和团队管理', 2, 1),
('产品经理', 'CPJL', '负责产品规划和需求分析', 3, 1),
('前端开发工程师', 'QDKF', '负责前端开发工作', 4, 1),
('后端开发工程师', 'HDKF', '负责后端开发工作', 5, 1),
('测试工程师', 'CSGC', '负责软件测试工作', 6, 1),
('市场专员', 'SCZY', '负责市场推广工作', 7, 1),
('HR专员', 'HRZY', '负责人力资源工作', 8, 1),
('财务专员', 'CWZY', '负责财务工作', 9, 1);

-- 插入员工测试数据
INSERT INTO `employee` (`emp_no`, `name`, `gender`, `phone`, `email`, `department_id`, `position_id`, `role_id`, `entry_date`, `status`, `address`, `remark`) VALUES
('EMP001', '张三', 1, '13800138001', 'zhangsan@example.com', 1, 1, 1, '2023-01-15', 1, '北京市朝阳区', '总经理'),
('EMP002', '李四', 1, '13800138002', 'lisi@example.com', 2, 2, 2, '2023-02-20', 1, '北京市海淀区', '技术总监'),
('EMP003', '王五', 2, '13800138003', 'wangwu@example.com', 3, 3, 2, '2023-03-10', 1, '北京市西城区', '产品经理'),
('EMP004', '赵六', 1, '13800138004', 'zhaoliu@example.com', 7, 4, 2, '2023-04-05', 1, '北京市东城区', '前端开发'),
('EMP005', '钱七', 2, '13800138005', 'qianqi@example.com', 8, 5, 2, '2023-05-12', 1, '北京市丰台区', '后端开发'),
('EMP006', '孙八', 1, '13800138006', 'sunba@example.com', 2, 5, 2, '2023-06-18', 1, '北京市石景山区', '后端开发'),
('EMP007', '周九', 2, '13800138007', 'zhoujiu@example.com', 9, 6, 2, '2023-07-22', 3, '北京市通州区', '测试工程师-试用期'),
('EMP008', '吴十', 1, '13800138008', 'wushi@example.com', 4, 7, 2, '2023-08-30', 1, '北京市大兴区', '市场专员'),
('EMP009', '郑一一', 2, '13800138009', 'zhengyiyi@example.com', 5, 8, 2, '2023-09-15', 1, '北京市昌平区', 'HR专员'),
('EMP010', '冯一二', 1, '13800138010', 'fengyier@example.com', 6, 9, 2, '2022-12-01', 2, '北京市顺义区', '财务专员-已离职');

-- =============================================
-- 查询验证
-- =============================================
SELECT '部门数据：' AS info;
SELECT * FROM department;

SELECT '岗位数据：' AS info;
SELECT * FROM position;

SELECT '员工数据：' AS info;
SELECT e.*, d.dept_name, p.post_name 
FROM employee e 
LEFT JOIN department d ON e.department_id = d.id 
LEFT JOIN position p ON e.position_id = p.id;

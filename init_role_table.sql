-- ============================================
-- 角色表（role）初始化脚本
-- ============================================

-- 1. 创建 role 表
CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称，如 admin、user',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 2. 插入默认角色数据
INSERT IGNORE INTO `role` (`id`, `role_name`, `description`) VALUES
(1, 'admin', '系统管理员，拥有所有权限'),
(2, 'user', '普通用户，拥有基本权限');

-- ============================================
-- 用户表（sys_user）字段修改脚本
-- ============================================

-- 3. 如果 sys_user 表中还是使用旧的 role 字段，需要修改为 role_id
-- 注意：执行前请备份数据，确认字段类型是否匹配
ALTER TABLE `sys_user` 
CHANGE COLUMN `role` `role_id` VARCHAR(50) DEFAULT '2' COMMENT '角色ID，关联role表';

-- 或者如果 role_id 字段已存在，只需要确保数据类型正确
-- ALTER TABLE `sys_user` MODIFY COLUMN `role_id` VARCHAR(50) DEFAULT '2' COMMENT '角色ID，关联role表';

-- 4. 添加外键约束（可选，根据实际情况决定是否需要）
-- ALTER TABLE `sys_user` 
-- ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL;

-- ============================================
-- 查询验证
-- ============================================

-- 查看 role 表数据
SELECT * FROM `role`;

-- 查看 sys_user 表的 role_id 字段
SELECT id, username, real_name, role_id, status FROM `sys_user`;

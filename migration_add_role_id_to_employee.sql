-- =============================================
-- 数据库迁移脚本：为employee表添加role_id字段
-- 创建时间：2026-04-08
-- 说明：如果employee表已存在，执行此脚本添加role_id字段
-- =============================================

USE khxt;

-- 检查并添加role_id字段
SET @dbname = DATABASE();
SET @tablename = 'employee';
SET @columnname = 'role_id';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (column_name = @columnname)
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD COLUMN ', @columnname, ' BIGINT DEFAULT NULL COMMENT ''角色ID'' AFTER position_id')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 检查并添加role_id索引
SET @indexname = 'idx_role_id';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (index_name = @indexname)
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD INDEX ', @indexname, ' (role_id)')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 检查并添加外键约束
SET @fkname = 'fk_employee_role';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (constraint_name = @fkname)
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD CONSTRAINT ', @fkname, ' FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE SET NULL ON UPDATE CASCADE')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 验证修改结果
SELECT 'employee表结构已更新，role_id字段添加成功！' AS result;
DESCRIBE employee;

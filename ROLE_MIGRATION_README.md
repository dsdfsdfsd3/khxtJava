# Role 字段迁移说明文档

## 概述
本次修改将 `sys_user` 表中的 `role` 字段（字符串，如 "admin"/"user"）迁移到独立的 `role` 表，并在 `sys_user` 表中使用 `role_id` 字段关联 `role` 表。

## 修改内容

### 1. 数据库变更

#### 1.1 新增 role 表
```sql
CREATE TABLE `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称，如 admin、user',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
```

#### 1.2 插入默认角色数据
```sql
INSERT INTO `role` (`id`, `role_name`, `description`) VALUES
(1, 'admin', '系统管理员，拥有所有权限'),
(2, 'user', '普通用户，拥有基本权限');
```

#### 1.3 修改 sys_user 表
将 `role` 字段改为 `role_id`，存储角色ID：
```sql
ALTER TABLE `sys_user` 
CHANGE COLUMN `role` `role_id` VARCHAR(50) DEFAULT '2' COMMENT '角色ID，关联role表';
```

### 2. Java 代码变更

#### 2.1 UserBean.java
- ✅ 已将 `role` 字段改为 `roleId`
- 字段类型：`String roleId`

#### 2.2 Role.java
- ✅ 完善了 Role 实体类
- 添加了 Lombok 注解（@Data, @NoArgsConstructor, @AllArgsConstructor）
- 添加了 `description` 字段

#### 2.3 UserServiceMapper.xml
- ✅ 登录查询：`role` → `role_id AS roleId`
- ✅ 注册插入：`role` → `role_id`，`#{role}` → `#{roleId}`

#### 2.4 UserController.java
- ✅ 更新了注释说明
- ✅ JWT 中仍然使用 `"role"` 作为 key，但值是 `roleId`

#### 2.5 UserServiceImpl.java
- ✅ 默认角色从 `"1"` 改为 `"2"`（假设 1=管理员，2=普通用户）
- ✅ 添加了 `RoleMapper` 注入
- ✅ 添加了 `getRoleById()` 方法

#### 2.6 LoginInterceptor.java
- ✅ 权限判断从 `"admin"` 改为 `"1"`（角色ID）
- ⚠️ **注意**：这里假设角色ID为 "1" 的是管理员，请根据实际情况调整

#### 2.7 新增 RoleMapper.java
- ✅ 创建了角色查询的 Mapper 接口
- 提供 `getRoleById()` 和 `getRoleByRoleName()` 方法

#### 2.8 新增 RoleMapper.xml
- ✅ 创建了角色查询的 MyBatis XML 映射文件

### 3. 文件清单

#### 修改的文件
- `src/main/java/com/lz/bean/UserBean.java` （已完成）
- `src/main/java/com/lz/bean/Role.java`
- `src/main/java/com/lz/controller/user/UserController.java`
- `src/main/java/com/lz/service/userService/UserService.java`
- `src/main/java/com/lz/service/userService/Impl/UserServiceImpl.java`
- `src/main/java/com/lz/interceptor/LoginInterceptor.java`
- `src/main/resources/mapper/UserServiceMapper.xml`

#### 新增的文件
- `src/main/java/com/lz/mapper/RoleMapper.java`
- `src/main/resources/mapper/RoleMapper.xml`
- `init_role_table.sql` （数据库初始化脚本）
- `ROLE_MIGRATION_README.md` （本文档）

## 使用说明

### 1. 执行数据库脚本
```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
source D:\myproject\khxtJava\init_role_table.sql
```

### 2. 确认角色ID对应关系
- `1` = 管理员（admin）
- `2` = 普通用户（user）

如果需要调整，请修改：
- `LoginInterceptor.java` 第 51 行的判断逻辑
- `UserServiceImpl.java` 第 40 行的默认角色ID

### 3. 启动应用
```bash
mvn clean install
mvn spring-boot:run
```

### 4. 测试验证
- 注册新用户，默认角色ID为 "2"（普通用户）
- 登录验证，JWT 中会包含 `role` 字段（值为 role_id）
- 访问 `/admin` 开头的接口，需要角色ID为 "1" 的管理员账号

## 注意事项

⚠️ **重要提醒**：

1. **备份数据**：在执行数据库脚本前，请务必备份现有数据
2. **角色ID对应关系**：确认 `1=admin, 2=user` 是否符合你的设计
3. **JWT 中的 role 字段**：虽然数据库中改成了 `role_id`，但 JWT 中仍然使用 `"role"` 作为 key，保持一致性
4. **权限判断逻辑**：`LoginInterceptor` 中硬编码了 `"1"` 作为管理员判断，建议后续改为从配置文件读取或动态查询 role 表

## 后续优化建议

1. **动态权限判断**：不要硬编码角色ID，而是通过查询 role 表的 `role_name` 来判断权限
2. **多角色支持**：考虑用户拥有多个角色的场景（可能需要用户-角色关联表）
3. **权限注解**：使用自定义注解（如 `@RequireRole("admin")`）来简化权限控制
4. **角色缓存**：对角色信息进行缓存，减少数据库查询

## 问题排查

如果遇到问题，请检查：
1. 数据库表结构是否正确
2. role 表是否有初始数据
3. sys_user 表的 role_id 字段是否正确映射
4. MyBatis XML 映射文件是否生效
5. 应用日志中是否有 SQL 执行错误

---
最后更新：2026-04-08

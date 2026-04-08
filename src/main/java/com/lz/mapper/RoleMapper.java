package com.lz.mapper;

import com.lz.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色 Mapper 接口
 */
@Mapper
public interface RoleMapper {

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息；查无此角色时返回 null
     */
    Role getRoleById(@Param("roleId") Long roleId);

    /**
     * 根据角色名称查询角色信息
     *
     * @param roleName 角色名称
     * @return 角色信息；查无此角色时返回 null
     */
    Role getRoleByRoleName(@Param("roleName") String roleName);
}

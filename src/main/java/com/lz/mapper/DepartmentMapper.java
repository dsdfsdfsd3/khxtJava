package com.lz.mapper;

import com.lz.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    
    /**
     * 查询所有部门
     */
    List<Department> findAll();
    
    /**
     * 根据ID查询部门
     */
    Department findById(@Param("id") Long id);
    
    /**
     * 根据名称查询部门
     */
    Department findByName(@Param("deptName") String deptName);
    
    /**
     * 新增部门
     */
    int insert(Department department);
    
    /**
     * 更新部门
     */
    int update(Department department);
    
    /**
     * 删除部门
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 检查是否有子部门
     */
    int countByParentId(@Param("parentId") Long parentId);
}

package com.lz.mapper;

import com.lz.bean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    
    /**
     * 查询所有员工（关联部门和岗位）
     */
    List<Employee> findAll();
    
    /**
     * 分页查询员工列表
     */
    List<Employee> findByPage(@Param("start") int start, @Param("size") int size, 
                              @Param("name") String name, @Param("departmentId") Long departmentId,
                              @Param("status") Integer status);
    
    /**
     * 统计员工数量
     */
    int countByCondition(@Param("name") String name, @Param("departmentId") Long departmentId,
                         @Param("status") Integer status);
    
    /**
     * 根据ID查询员工
     */
    Employee findById(@Param("id") Long id);
    
    /**
     * 根据工号查询员工
     */
    Employee findByEmpNo(@Param("empNo") String empNo);
    
    /**
     * 新增员工
     */
    int insert(Employee employee);
    
    /**
     * 更新员工
     */
    int update(Employee employee);
    
    /**
     * 删除员工
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 统计在职员工数量
     */
    int countActiveEmployees();
    
    /**
     * 统计各部门员工数量
     */
    List<java.util.Map<String, Object>> countByDepartment();
}

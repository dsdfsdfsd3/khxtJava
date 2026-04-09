package com.lz.mapper;

import com.lz.bean.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PositionMapper {
    
    /**
     * 查询所有岗位
     */
    List<Position> findAll();
    
    /**
     * 根据ID查询岗位
     */
    Position findById(@Param("id") Long id);
    
    /**
     * 根据名称查询岗位
     */
    Position findByName(@Param("postName") String postName);
    
    /**
     * 新增岗位
     */
    int insert(Position position);
    
    /**
     * 更新岗位
     */
    int update(Position position);
    
    /**
     * 删除岗位
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 检查岗位是否被员工使用
     */
    int countByPositionId(@Param("positionId") Long positionId);
}

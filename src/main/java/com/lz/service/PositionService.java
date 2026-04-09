package com.lz.service;

import com.lz.bean.Position;

import java.util.List;

public interface PositionService {
    
    /**
     * 查询所有岗位
     */
    List<Position> findAll();
    
    /**
     * 根据ID查询岗位
     */
    Position findById(Long id);
    
    /**
     * 新增岗位
     */
    String insert(Position position);
    
    /**
     * 更新岗位
     */
    String update(Position position);
    
    /**
     * 删除岗位
     */
    String deleteById(Long id);
}

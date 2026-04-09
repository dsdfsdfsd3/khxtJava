package com.lz.service.Impl;

import com.lz.bean.Position;
import com.lz.mapper.PositionMapper;
import com.lz.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> findAll() {
        return positionMapper.findAll();
    }

    @Override
    public Position findById(Long id) {
        return positionMapper.findById(id);
    }

    @Override
    public String insert(Position position) {
        try {
            // 校验岗位名称是否已存在
            Position existPosition = positionMapper.findByName(position.getPostName());
            if (existPosition != null) {
                return "岗位名称已存在";
            }
            
            // 设置创建时间
            position.setCreateTime(new Date());
            position.setUpdateTime(new Date());
            
            // 默认状态为启用
            if (position.getStatus() == null) {
                position.setStatus(1);
            }
            
            // 默认排序为0
            if (position.getSortOrder() == null) {
                position.setSortOrder(0);
            }
            
            positionMapper.insert(position);
            return "新增成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "新增失败：" + e.getMessage();
        }
    }

    @Override
    public String update(Position position) {
        try {
            // 校验岗位是否存在
            Position existPosition = positionMapper.findById(position.getId());
            if (existPosition == null) {
                return "岗位不存在";
            }
            
            // 如果修改了岗位名称，校验名称是否被其他岗位使用
            if (!existPosition.getPostName().equals(position.getPostName())) {
                Position positionByName = positionMapper.findByName(position.getPostName());
                if (positionByName != null && !positionByName.getId().equals(position.getId())) {
                    return "岗位名称已被其他岗位使用";
                }
            }
            
            // 设置更新时间
            position.setUpdateTime(new Date());
            
            positionMapper.update(position);
            return "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败：" + e.getMessage();
        }
    }

    @Override
    public String deleteById(Long id) {
        try {
            // 校验岗位是否存在
            Position position = positionMapper.findById(id);
            if (position == null) {
                return "岗位不存在";
            }
            
            // 检查是否有员工使用该岗位
            int employeeCount = positionMapper.countByPositionId(id);
            if (employeeCount > 0) {
                return "该岗位下还有员工，无法删除";
            }
            
            positionMapper.deleteById(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败：" + e.getMessage();
        }
    }
}

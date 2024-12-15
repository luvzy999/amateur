package com.example.mapper;

import com.example.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 新增
     */
    int insert(Order order);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Order order);

    /**
     * 根据ID查询
     */
    Order selectById(Integer id);

    /**
     * 查询所有
     */
    List<Order> selectAll(Order order);

}

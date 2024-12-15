package com.example.service;

import com.example.entity.Order;
import com.example.mapper.OrderMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 新增
     */
    public void add(Order order) {
        orderMapper.insert(order);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        orderMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {

    }

    /**
     * 修改
     */
    public void updateById(Order order) {

    }

    /**
     * 根据ID查询
     */
    public Order selectById(Integer id) {
        return orderMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Order> selectAll(Order order) {
        return orderMapper.selectAll(order);
    }

    /**
     * 分页查询
     */
    public PageInfo<Order> selectPage(Order order, Integer pageNum, Integer pageSize) {
        return null;
    }


}

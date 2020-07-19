package cn.time.order.service;

import cn.time.order.entity.Order;

public interface OrderService {

    public Order findById(Integer id);

    public void save(Order order);

    public void update(Order order);

    public void delete(Integer id);
}

package cn.time.order.service.impl;

import cn.time.order.dao.OrderDao;
import cn.time.order.entity.Order;
import cn.time.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id).get();
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public void update(Order order) {
        orderDao.save(order);
    }

    @Override
    public void delete(Integer id) {
        orderDao.deleteById(id);
    }
}

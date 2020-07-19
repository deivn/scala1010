package cn.time.order.dao;

import cn.time.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDao extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
}

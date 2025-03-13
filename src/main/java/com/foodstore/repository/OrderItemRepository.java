package com.foodstore.repository;

import com.foodstore.model.Order;
import com.foodstore.model.OrderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Thêm phương thức tìm kiếm OrderItem theo Order
    List<OrderItem> findByOrder(Order order);
}

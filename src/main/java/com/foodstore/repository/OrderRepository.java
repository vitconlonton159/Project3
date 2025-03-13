package com.foodstore.repository;

import com.foodstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
	
}

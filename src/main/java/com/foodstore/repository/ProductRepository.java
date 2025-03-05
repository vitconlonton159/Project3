package com.foodstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foodstore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
}

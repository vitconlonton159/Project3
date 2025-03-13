package com.foodstore.repository;

import com.foodstore.model.CartItem;
import com.foodstore.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Phương thức này sẽ tìm tất cả các sản phẩm trong giỏ hàng của người dùng
    List<CartItem> findByUser(User user);
}
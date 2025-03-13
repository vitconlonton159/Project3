package com.foodstore.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // Đánh dấu lớp này là một entity của JPA
@Table(name = "cart_items") // Tên bảng trong cơ sở dữ liệu
public class CartItem {
	
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private Long cartItemId;

    @ManyToOne // Một sản phẩm có thể có nhiều lần trong giỏ
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user; // Liên kết với bảng users

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product; // Liên kết với bảng products

    private int quantity; // Số lượng sản phẩm trong giỏ

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

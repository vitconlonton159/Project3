package com.foodstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import com.foodstore.model.CartItem;
import com.foodstore.model.Product;
import com.foodstore.model.User;
import com.foodstore.repository.CartItemRepository;
import com.foodstore.repository.ProductRepository;
import com.foodstore.repository.UserRepository;



@Controller
@RequestMapping("/cart") // Đảm bảo đường dẫn bắt đầu bằng "/cart"

public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    

    // Hiển thị giỏ hàng của người dùng
    @GetMapping
    public String viewCart(Model model, @RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        model.addAttribute("cartItems", cartItems);
        return "cart"; // Trả về trang giỏ hàng (cart.html)
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        
        cartItemRepository.save(cartItem); // Lưu vào giỏ hàng
        return "redirect:/cart?userId=" + userId; // Chuyển hướng đến trang giỏ hàng
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Giỏ hàng không tồn tại"));
        cartItemRepository.delete(cartItem); // Xóa sản phẩm khỏi giỏ hàng
        return "redirect:/cart?userId=" + cartItem.getUser().getUserId(); // Chuyển hướng lại trang giỏ hàng
    }
}


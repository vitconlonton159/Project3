package com.foodstore.controller;

import com.foodstore.model.Product;
import com.foodstore.repository.ProductRepository;

import org.springframework.ui.Model; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    // Để hiển thị sản phẩm trong giao diện người dùng (HTML)
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();  // Lấy tất cả sản phẩm từ CSDL
        System.out.println("Fetched Products: " + products);  // In ra dữ liệu lấy được từ CSDL
        model.addAttribute("products", products);  // Truyền danh sách sản phẩm cho view
        return "products";  // Chỉ đường dẫn tới trang products.html
    }

    // API trả về dữ liệu sản phẩm dưới dạng JSON (không dùng Thymeleaf)
    @RequestMapping("/api/products")
    @ResponseBody
    public List<Product> getProductsApi() {
        return productRepository.findAll(); // Trả về dữ liệu dưới dạng JSON
    }
    
}

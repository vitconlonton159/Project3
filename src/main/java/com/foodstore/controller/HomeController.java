package com.foodstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // Trả về file home.html
    }

    @GetMapping("/products")
    public String products() {
        return "products"; // Trả về file products.html
    }

    @GetMapping("/orders")
    public String orders() {
        return "orders"; // Trả về file orders.html
    }
}

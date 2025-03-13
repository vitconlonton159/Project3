package com.foodstore.controller;

import com.foodstore.model.Order;
import com.foodstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdController {

    @Autowired
    private OrderService orderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "list"; // Đảm bảo tên file là adminOrder/list
    }

    // Hiển thị form tạo đơn hàng
    @GetMapping("/create")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "create"; // Đảm bảo tên file là adminOrder/create
    }

    // Xử lý tạo đơn hàng
    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/admin/orders"; // Quay lại danh sách đơn hàng
    }

    // Hiển thị chi tiết đơn hàng
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id).orElse(null));
        return "detail"; // Đảm bảo tên file là adminOrder/detail
    }

    // Hiển thị form chỉnh sửa đơn hàng
    @GetMapping("/{id}/edit")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id).orElse(null);
        System.out.println("Order ID: " + (order != null ? order.getOrderId() : "Không tìm thấy"));
        model.addAttribute("order", order);
        return "edit";
    }

    // Xử lý cập nhật đơn hàng
    @PostMapping("/{id}/edit")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        order.setOrderId(id);
        orderService.updateOrder(order);
        return "redirect:/admin/orders"; // Quay lại danh sách đơn hàng
    }

    // Xử lý xóa đơn hàng
    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders"; // Quay lại danh sách đơn hàng
    }
}

package com.foodstore.controller;

import java.math.BigDecimal;
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
import com.foodstore.model.Order;
import com.foodstore.model.OrderItem;
import com.foodstore.model.Product;
import com.foodstore.model.User;
import com.foodstore.repository.CartItemRepository;
import com.foodstore.repository.OrderItemRepository;
import com.foodstore.repository.OrderRepository;
import com.foodstore.repository.ProductRepository;
import com.foodstore.repository.UserRepository;



@Controller
@RequestMapping("/order") // Đảm bảo đường dẫn bắt đầu bằng "/order"
public class OrderController {
	
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/create")
    public String createOrderPage(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity, Model model) {
        // Lấy người dùng từ userId
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        model.addAttribute("user", user);

        // Lấy sản phẩm từ productId
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);

        // Trả về trang "order" để hiển thị thông tin sản phẩm
        return "order";  
    }

    // Các phương thức khác của controller


    @PostMapping("/create")
    public String createOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        // Tính tổng giá trị đơn hàng
        BigDecimal total = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity));

        // Tạo đơn hàng
        Order order = new Order();
        order.setUser(user);
        order.setTotal(total);
        orderRepository.save(order);

        // Lưu chi tiết đơn hàng
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(BigDecimal.valueOf(product.getPrice()));
        orderItemRepository.save(orderItem);

        // Chuyển hướng đến trang chi tiết đơn hàng
        return "redirect:/order/" + order.getOrderId(); // Chuyển hướng đến trang chi tiết đơn hàng
    }

    // Hiển thị chi tiết đơn hàng
    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        // Tìm đơn hàng theo orderId
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại"));
        
        // Lấy danh sách các sản phẩm trong đơn hàng
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        
        // Thêm thông tin vào model
        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        
        return "order_detail"; // Trả về trang chi tiết đơn hàng
    }
    
    
    // Xử lý thanh toán
    @GetMapping("/pay")
    public String processPayment(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity, Model model) {
        // Lấy người dùng từ userId
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        model.addAttribute("user", user);

        // Lấy sản phẩm từ productId
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);

        // Tính tổng giá trị đơn hàng
        BigDecimal total = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity));
        model.addAttribute("total", total);

        // Trả về trang thanh toán
        return "payment";  // Chuyển đến trang thanh toán
    }

    // Xử lý xác nhận thanh toán và lưu đơn hàng vào cơ sở dữ liệu
    @PostMapping("/complete")
    public String completePayment(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity, @RequestParam BigDecimal total, Model model) {
        // Xử lý thanh toán và tạo đơn hàng
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        // Lấy sản phẩm từ productId
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        // Tạo đơn hàng
        Order order = new Order();
        order.setUser(user);  // Liên kết với người dùng
        order.setTotal(total);  // Cập nhật tổng giá trị
        order.setStatus("Completed");  // Đánh dấu đơn hàng đã hoàn thành
        orderRepository.save(order);  // Lưu đơn hàng vào cơ sở dữ liệu

        // Lưu thông tin sản phẩm vào bảng đơn hàng (không sử dụng OrderItem)
        // Bạn có thể lưu thông tin sản phẩm trực tiếp vào bảng orders hoặc tạo bảng chi tiết đơn hàng nếu cần
        // Cập nhật bảng orders với thông tin chi tiết sản phẩm, quantity
        // Lưu thông tin vào bảng order (điều chỉnh cho phù hợp với database của bạn)
        // Ví dụ có thể lưu giá trị sản phẩm vào trường nào đó trong bảng orders

        order.setTotal(total);
        order.setStatus("Completed"); // Thêm logic liên quan đến đơn hàng nếu cần.
        orderRepository.save(order);

        // Chuyển hướng đến trang thông báo thanh toán thành công
        model.addAttribute("message", "Thanh toán thành công!");
        return "payment_success";  // Trang thông báo thanh toán thành công
    }
}

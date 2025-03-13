package com.foodstore.controller;

import com.foodstore.model.Product;
import com.foodstore.repository.ProductRepository;

import org.springframework.ui.Model; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin") // Đảm bảo rằng các đường dẫn bắt đầu với /admin
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Hiển thị danh sách sản phẩm
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();  // Lấy tất cả sản phẩm từ CSDL
        model.addAttribute("products", products);  // Truyền danh sách sản phẩm cho view
        return "products";  // Chỉ đường dẫn tới trang products.html
    }

    // API trả về dữ liệu sản phẩm dưới dạng JSON (không dùng Thymeleaf)
    @RequestMapping("/api/products")
    @ResponseBody
    public List<Product> getProductsApi() {
        return productRepository.findAll(); // Trả về dữ liệu dưới dạng JSON
    }

    // Hiển thị trang dashboard quản lý sản phẩm
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Product> products = productRepository.findAll(); // Lấy tất cả sản phẩm từ CSDL
        model.addAttribute("products", products); // Truyền danh sách sản phẩm cho view
        return "dashboard"; // Trả về trang dashboard.html
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/product/add")
    public String showAddForm() {
        return "add_product"; // Trả về trang add_product.html
    }

    // Thêm sản phẩm mới
    @PostMapping("/product/add")
    public String addProduct(@RequestParam String name, 
                             @RequestParam String description, 
                             @RequestParam Double price, 
                             @RequestParam String imageUrl, 
                             @RequestParam String category) {
        Product product = new Product(name, description, price, imageUrl, category);
        productRepository.save(product); // Lưu sản phẩm vào cơ sở dữ liệu
        return "redirect:/admin/dashboard"; // Chuyển hướng về dashboard
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        model.addAttribute("product", product);
        return "edit_product"; // Trả về trang edit_product.html
    }

    // Sửa thông tin sản phẩm
    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable Long id, 
                                @RequestParam String name, 
                                @RequestParam String description, 
                                @RequestParam Double price, 
                                @RequestParam String imageUrl, 
                                @RequestParam String category) {
        // Tìm sản phẩm cần sửa
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        productRepository.save(product); // Cập nhật sản phẩm vào cơ sở dữ liệu
        return "redirect:/admin/dashboard"; // Chuyển hướng về dashboard
    }

    // Xóa sản phẩm
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        // Tìm sản phẩm cần xóa
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        productRepository.delete(product); // Xóa sản phẩm khỏi cơ sở dữ liệu
        return "redirect:/admin/dashboard"; // Chuyển hướng về dashboard
    }
}

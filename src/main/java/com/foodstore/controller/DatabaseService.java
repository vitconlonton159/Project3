package com.foodstore.controller;
import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
@Service
public class DatabaseService {

    @PostConstruct
    public void checkDatabaseConnection() {
        try {
            // Thử kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore_db01", "root", "ongco13123");
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

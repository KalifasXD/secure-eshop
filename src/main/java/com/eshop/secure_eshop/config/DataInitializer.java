package com.eshop.secure_eshop.config;

import com.eshop.secure_eshop.model.EshopUser;
import com.eshop.secure_eshop.model.Product;
import com.eshop.secure_eshop.repository.ProductRepository;
import com.eshop.secure_eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        EshopUser user = new EshopUser();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin123"));
        userRepository.save(user);

        Product p1 = new Product();
        p1.setProductName("Laptop");
        p1.setPrice(new BigDecimal("999.99"));
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setProductName("Headphones");
        p2.setPrice(new BigDecimal("59.99"));
        productRepository.save(p2);

        Product p3 = new Product();
        p3.setProductName("Mouse");
        p3.setPrice(new BigDecimal("49.99"));
        productRepository.save(p3);

        Product p4 = new Product();
        p4.setProductName("Keyboard");
        p4.setPrice(new BigDecimal("19.99"));
        productRepository.save(p4);
    }
}

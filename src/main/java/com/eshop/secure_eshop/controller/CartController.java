package com.eshop.secure_eshop.controller;

import com.eshop.secure_eshop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(@RequestParam Long productId, HttpSession session) {

        if (productId == null || productId <= 0 || !productRepository.existsById(productId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid product"));
        }

        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart == null)
            cart = new ArrayList<>();

        cart.add(productId);
        session.setAttribute("cart", cart);

        return ResponseEntity.ok(Map.of("cartCount", cart.size()));
    }
}

package com.eshop.secure_eshop.controller;

import com.eshop.secure_eshop.model.Product;
import com.eshop.secure_eshop.model.ShippingData;
import com.eshop.secure_eshop.repository.ProductRepository;
import com.eshop.secure_eshop.service.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EmailService emailService;

    @GetMapping("/payment")
    public String payment(HttpSession session, Model model) {

        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if(cart == null)
            cart = new ArrayList<>();

        List<Product> products = productRepository.findAllById(cart);

        // add them to the model
        model.addAttribute("productsInCart", products);

        return "payment";
    }

    @PostMapping("/payment/confirm")
    @ResponseBody
    public Map<String, Boolean> confirmOrder(@Valid @RequestBody ShippingData shippingData, HttpSession session){
        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if(cart == null)
            cart = new ArrayList<>();

        List<Product> products = productRepository.findAllById(cart);

        emailService.sendOrderEmail(products, shippingData);

        session.setAttribute("cart", null);
        return Map.of("success", true);
    }

}


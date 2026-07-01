package com.eshop.secure_eshop.controller;

import com.eshop.secure_eshop.model.Product;
import com.eshop.secure_eshop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/catalogue")
    public String catalogue(Model model, HttpSession session){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        List<Long> cart = (List<Long>) session.getAttribute("cart");
        int cartCount = (cart != null) ? cart.size() : 0;
        model.addAttribute("cartCount", cartCount);

        return "catalogue";
    }

    @GetMapping("/catalogue/search")
    @ResponseBody
    public List<Product> search(@RequestParam String query){
        return productRepository.findByProductNameContainingIgnoreCase(query);
    }

    @GetMapping("/catalogue/all")
    @ResponseBody
    public List<Product> allProducts(){
        return productRepository.findAll();
    }
}

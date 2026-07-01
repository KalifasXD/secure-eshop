package com.eshop.secure_eshop.service;

import com.eshop.secure_eshop.model.Product;
import com.eshop.secure_eshop.model.ShippingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderEmail(List<Product> products, ShippingData shippingData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("vasilis944@gmail.com");  // the shop admin's email
        message.setSubject("New Order");
        StringBuilder body = new StringBuilder();
        body.append("New Order Received!\n\n");
        body.append("Ship to: ").append(shippingData.getFullName()).append("\n");
        body.append("Address: ").append(shippingData.getStreet()).append(", ")
                .append(shippingData.getCity()).append(" ")
                .append(shippingData.getPostalCode()).append("\n\n");
        body.append("Products:\n");

        for (Product product : products) {
            body.append("- ").append(product.getProductName())
                    .append(" : ").append(product.getPrice()).append("\n");
        }

        message.setText(body.toString());
        mailSender.send(message);
    }
}
package com.example.coffeeshop.service;

import com.example.coffeeshop.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final List<Product> cart = new ArrayList<>();
    private int beverageCount = 0;

    public void addProduct(String productName) {
        Product product = switch (productName) {
            case "Small Coffee" -> new Beverage("Small Coffee", 2.50);
            case "Medium Coffee" -> new Beverage("Medium Coffee", 3.00);
            case "Large Coffee" -> new Beverage("Large Coffee", 3.50);
            case "Bacon Roll" -> new Snack("Bacon Roll", 4.50);
            case "Orange Juice" -> new Product("Orange Juice (0.25L)", 3.95);
            case "Extra Milk" -> new Extra("Extra Milk", 0.30);
            case "Foamed Milk" -> new Extra("Foamed Milk", 0.50);
            case "Special Roast Coffee" -> new Extra("Special Roast Coffee", 0.90);
            default -> throw new IllegalArgumentException("Invalid product: " + productName);
        };



        // Count the number of beverages for "every 5th beverage free" logic
        if (product instanceof Beverage) {
            beverageCount++;
            if (beverageCount % 5 == 0) {
                System.out.println("Beverage " + beverageCount + " is FREE!");
                product.setPrice(0.00); // Set the price of every 5th beverage to 0
            }
        }
        cart.add(product);
    }

    public ReceiptResponse getReceipt() {

        double total = 0.0;
        for (Product product : cart) {
            total += product.getPrice();
        }

        return new ReceiptResponse(generateReceiptText(), total);
    }

    public void clearCart() {
        cart.clear();
        beverageCount = 0;
    }

    private String generateReceiptText() {
        StringBuilder receipt = new StringBuilder("Charlene's Coffee Corner\n");
        receipt.append("------------------------------\n");

        for (Product product : cart) {
            receipt.append(product.getName())
                    .append(" - CHF ")
                    .append(String.format("%.2f", product.getPrice()))
                    .append("\n");
        }

        receipt.append("------------------------------");
        return receipt.toString();
    }

}

package com.example.coffeeshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptResponse {
    private String receiptText;
    private double totalPrice;
}

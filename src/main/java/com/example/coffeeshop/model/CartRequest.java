package com.example.coffeeshop.model;

import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private List<String> products; // e.g., "Large Coffee", "Extra Milk", "Bacon Roll"
}

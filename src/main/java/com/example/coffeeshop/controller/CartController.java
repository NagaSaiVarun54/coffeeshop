package com.example.coffeeshop.controller;
;
import com.example.coffeeshop.model.CartRequest;
import com.example.coffeeshop.model.ReceiptResponse;
import com.example.coffeeshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody CartRequest cartRequest) {
        for (String product : cartRequest.getProducts()) {
            cartService.addProduct(product);
        }
        return ResponseEntity.ok("Products added to cart.");
    }

    @GetMapping("/receipt")
    public ResponseEntity<ReceiptResponse> getReceipt() {
        return ResponseEntity.ok(cartService.getReceipt());
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared.");
    }
}

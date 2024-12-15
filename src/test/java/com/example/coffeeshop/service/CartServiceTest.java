package com.example.coffeeshop.service;

import com.example.coffeeshop.model.ReceiptResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }

    @Test
    void testAddProduct() {
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Medium Coffee");
        cartService.addProduct("Bacon Roll");

        assertEquals(10, cartService.getReceipt().getTotalPrice(), "Total price should be correct");
    }

    @Test
    void testEveryFifthBeverageIsFree() {
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Medium Coffee");
        cartService.addProduct("Large Coffee");
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Medium Coffee");

        ReceiptResponse receipt = cartService.getReceipt();
        assertTrue(receipt.getTotalPrice() < 15, "Total price should reflect 5th beverage being free.");
    }

    @Test
    void testGetReceipt() {
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Large Coffee");
        cartService.addProduct("Bacon Roll");

        ReceiptResponse receipt = cartService.getReceipt();

        assertEquals(10.50, receipt.getTotalPrice(), "Total price should be correct");
        assertTrue(receipt.getReceiptText().contains("Charlene's Coffee Corner"));
    }

    @Test
    void testClearCart() {
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Large Coffee");

        cartService.clearCart();

        assertEquals(0, cartService.getReceipt().getTotalPrice(), "Total price should be 0 after clearing the cart");
    }

    @Test
    void testInvalidProductThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cartService.addProduct("Invalid Product"));
    }
}

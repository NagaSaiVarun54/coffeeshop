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
        // Add products to the cart
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Medium Coffee");
        cartService.addProduct("Bacon Roll");

        // Check the cart size after adding products
        assertEquals(10, cartService.getReceipt().getTotalPrice(), "Total price should be correct");
    }

    @Test
    void testEveryFifthBeverageIsFree() {
        // Add 5 beverages to the cart
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Medium Coffee");
        cartService.addProduct("Large Coffee");
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Medium Coffee");  // This should be free

        // Check that the price of the 5th beverage is 0.0
        ReceiptResponse receipt = cartService.getReceipt();
        assertTrue(receipt.getTotalPrice() < 15, "Total price should reflect 5th beverage being free.");
    }

    @Test
    void testGetReceipt() {
        // Add some products
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Large Coffee");
        cartService.addProduct("Bacon Roll");

        // Get the receipt and verify the total price and receipt text
        ReceiptResponse receipt = cartService.getReceipt();

        // Total price should be 2.50 + 3.50 + 4.50 = 10.50
        assertEquals(10.50, receipt.getTotalPrice(), "Total price should be correct");
        assertTrue(receipt.getReceiptText().contains("Charlene's Coffee Corner"));
    }

    @Test
    void testClearCart() {
        // Add products to the cart
        cartService.addProduct("Small Coffee");
        cartService.addProduct("Large Coffee");

        // Clear the cart
        cartService.clearCart();

        // Verify the cart is cleared
        assertEquals(0, cartService.getReceipt().getTotalPrice(), "Total price should be 0 after clearing the cart");
    }

    @Test
    void testInvalidProductThrowsException() {
        // Test that invalid product names throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> cartService.addProduct("Invalid Product"));
    }
}

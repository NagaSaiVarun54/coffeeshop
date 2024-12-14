package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.CartRequest;
import com.example.coffeeshop.model.ReceiptResponse;
import com.example.coffeeshop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    public void testAddProducts() throws Exception {
        CartRequest cartRequest = new CartRequest();
        cartRequest.setProducts(List.of("Small Coffee", "Bacon Roll", "Medium Coffee", "Large Coffee", "Small Coffee"));

        // Act
        MvcResult result = mockMvc.perform(post("/api/cart/add")
                        .contentType("application/json")
                        .content("{\"products\":[\"Small Coffee\", \"Bacon Roll\", \"Medium Coffee\", \"Large Coffee\", \"Small Coffee\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Products added to cart."))
                .andReturn();

        // Verify the interaction with the service for the 5th beverage logic
        verify(cartService, times(5)).addProduct(anyString());
    }

    @Test
    public void testGetReceipt() throws Exception {
        // Creating a mock receipt with a free beverage scenario
        ReceiptResponse receiptResponse = new ReceiptResponse("Charlene's Coffee Corner\nSmall Coffee - CHF 2.50\n", 2.50);
        System.out.println(receiptResponse);
        // Mock service response
        when(cartService.getReceipt()).thenReturn(receiptResponse);

        // Act & Assert
        mockMvc.perform(get("/api/cart/receipt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(2.50))
                .andExpect(jsonPath("$.receiptText").value("Charlene's Coffee Corner\nSmall Coffee - CHF 2.50\n"));
    }

    @Test
    public void testClearCart() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/cart/clear"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cart cleared."))
                .andReturn();

        // Verify the interaction with the service
        verify(cartService, times(1)).clearCart();
    }
}

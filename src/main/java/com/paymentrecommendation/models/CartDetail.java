package com.paymentrecommendation.models;

import java.util.List;

public class CartDetail {
    private Double cartAmount;

    private List<CartItem> cartItems;

    public Double getCartAmount() {
        return cartAmount;
    }

    public CartDetail(Double cartAmount, List<CartItem> cartItems) {
        this.cartAmount = cartAmount;
        this.cartItems = cartItems;
    }

    public void setCartAmount(Double cartAmount) {
        this.cartAmount = cartAmount;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}

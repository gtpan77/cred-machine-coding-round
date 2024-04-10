package com.paymentrecommendation.models;

public class CartItem {
    private String id;

    private String name;

    private Double amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CartItem(String id, String name, Double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}

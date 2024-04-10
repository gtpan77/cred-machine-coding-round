package com.paymentrecommendation.models;

import com.paymentrecommendation.enums.LineOfBusiness;

public class Cart {
    private LineOfBusiness lineOfBusiness;

    private CartDetail cartDetail;

    public Cart(LineOfBusiness lineOfBusiness, CartDetail cartDetail) {
        this.lineOfBusiness = lineOfBusiness;
        this.cartDetail = cartDetail;
    }

    public LineOfBusiness getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public CartDetail getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(CartDetail cartDetail) {
        this.cartDetail = cartDetail;
    }
}

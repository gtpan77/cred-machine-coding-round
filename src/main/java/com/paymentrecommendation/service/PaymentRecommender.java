package com.paymentrecommendation.service;

import java.util.List;
import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.PaymentInstrument;
import com.paymentrecommendation.models.User;

public interface PaymentRecommender {
    List<PaymentInstrument> recommendPaymentInstruments(final User user, final Cart cart);
}

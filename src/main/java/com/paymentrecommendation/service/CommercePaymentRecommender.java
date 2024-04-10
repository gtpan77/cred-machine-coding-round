package com.paymentrecommendation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.CartDetail;
import com.paymentrecommendation.models.PaymentInstrument;
import com.paymentrecommendation.models.User;
import com.paymentrecommendation.models.UserContext;
import com.paymentrecommendation.models.UserPaymentInstrument;

public class CommercePaymentRecommender implements PaymentRecommender {
  HashMap<PaymentInstrumentType, Integer> paymentInstrumentRelevanceScore = new HashMap<>();
  HashMap<PaymentInstrumentType, Double> paymentInstrumentLimit = new HashMap<>();

  CommercePaymentRecommender() {
    paymentInstrumentRelevanceScore.put(PaymentInstrumentType.CREDIT_CARD, 1);
    paymentInstrumentRelevanceScore.put(PaymentInstrumentType.UPI, 2);
    paymentInstrumentRelevanceScore.put(PaymentInstrumentType.DEBIT_CARD, 3);
    paymentInstrumentRelevanceScore.put(PaymentInstrumentType.NETBANKING, -1); // not allowed
    
    paymentInstrumentLimit.put(PaymentInstrumentType.CREDIT_CARD, 250000.0);
    paymentInstrumentLimit.put(PaymentInstrumentType.UPI, 100000.0);
    paymentInstrumentLimit.put(PaymentInstrumentType.DEBIT_CARD, 200000.0);
    paymentInstrumentLimit.put(PaymentInstrumentType.NETBANKING, -1.0); // not allowed
  }

  @Override
  public List<PaymentInstrument> recommendPaymentInstruments(User user, Cart cart) {
    // sanitise inputs
    UserPaymentInstrument userPaymentInstrument = user.getUserPaymentInstrument();
    List<PaymentInstrument> paymentInstruments = userPaymentInstrument.getPaymentInstruments();
    // basic line of business check
    if(cart.getLineOfBusiness() != LineOfBusiness.COMMERCE) {
      // System.out.println("not matching");
      // throw "The line of business is not supported";
      return new ArrayList<>();
    }
    Boolean upiEnabled = false;
    UserContext userContext = user.getUserContext();
    if(userContext != null) {
      upiEnabled = user.getUserContext().getDeviceContext().isUpiEnabled();
    }
    Double cartAmount = 0.0;
    CartDetail cartDetail = cart.getCartDetail();
    if(cartDetail != null) {
      cartAmount = cart.getCartDetail().getCartAmount();
    }
    // filter instruments - cart amount and upi enabled
    List<PaymentInstrument> paymentInstruments2 = PaymentInstrumentsUtil.filter(paymentInstruments, paymentInstrumentLimit, upiEnabled, cartAmount);
    // sort instruments
    return PaymentInstrumentsUtil.sort(paymentInstruments2, paymentInstrumentRelevanceScore);
  }

}

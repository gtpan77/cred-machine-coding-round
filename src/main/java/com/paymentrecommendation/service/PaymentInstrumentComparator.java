package com.paymentrecommendation.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.models.PaymentInstrument;

public class PaymentInstrumentComparator implements Comparator<PaymentInstrument> {
  private final Map<PaymentInstrumentType, Integer> paymentInstrumentRelevanceScore;

  public PaymentInstrumentComparator(HashMap<com.paymentrecommendation.enums.PaymentInstrumentType, Integer> paymentInstrumentRelevanceScore2) {
      this.paymentInstrumentRelevanceScore = paymentInstrumentRelevanceScore2;
  }

  @Override
  public int compare(PaymentInstrument instrument1, PaymentInstrument instrument2) {
      // Get the relevance scores for the payment instruments from the HashMap
      PaymentInstrumentType type1 = instrument1.getPaymentInstrumentType();
      PaymentInstrumentType type2 = instrument2.getPaymentInstrumentType();
      if(type1 == type2) {
        Double relevanceScore1 = instrument1.getRelevanceScore();
        Double relevanceScore2 = instrument2.getRelevanceScore();
        return Double.compare(relevanceScore2, relevanceScore1); // Descending order
      } else {
        int score1 = paymentInstrumentRelevanceScore.getOrDefault(type1, 0);
        int score2 = paymentInstrumentRelevanceScore.getOrDefault(type2, 0);
        return Integer.compare(score1, score2); // Ascending order
      }
  }
}
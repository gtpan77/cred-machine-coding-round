package com.paymentrecommendation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.models.PaymentInstrument;

public class PaymentInstrumentsUtil {
  public static List<PaymentInstrument> filter(List<PaymentInstrument> paymentInstruments,
      HashMap<PaymentInstrumentType, Double> paymentInstrumentLimit, Boolean upiEnabled, Double cartAmount) {
    List<PaymentInstrument> filteredList = new ArrayList<>();
    
    for (PaymentInstrument paymentInstrument : paymentInstruments) {
      PaymentInstrumentType paymentInstrumentType = paymentInstrument.getPaymentInstrumentType();
      if (paymentInstrumentType == PaymentInstrumentType.UPI && upiEnabled == false) {
        continue;
      }
      if (paymentInstrumentLimit.getOrDefault(paymentInstrumentType, -1.0) == -1.0
          || paymentInstrumentLimit.getOrDefault(paymentInstrumentType, 0.0) < cartAmount) { // not allowed
        continue;
      }
      filteredList.add(paymentInstrument);
    }
    return filteredList;
  }

  public static List<PaymentInstrument> sort(List<PaymentInstrument> paymentInstruments,
      HashMap<PaymentInstrumentType, Integer> paymentInstrumentRelevanceScore) {
    List<PaymentInstrument> sortedList = new ArrayList<>(paymentInstruments);
    PaymentInstrumentComparator comparator = new PaymentInstrumentComparator(paymentInstrumentRelevanceScore);
    Collections.sort(sortedList, comparator);
    return sortedList;
  }
}


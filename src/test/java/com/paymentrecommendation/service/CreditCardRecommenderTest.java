package com.paymentrecommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.google.common.collect.Lists;
import com.paymentrecommendation.enums.Issuer;
import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.CartDetail;
import com.paymentrecommendation.models.CartItem;
import com.paymentrecommendation.models.DeviceContext;
import com.paymentrecommendation.models.PaymentInstrument;
import com.paymentrecommendation.models.User;
import com.paymentrecommendation.models.UserContext;
import com.paymentrecommendation.models.UserPaymentInstrument;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class CreditCardRecommenderTest {
    private PaymentRecommender paymentRecommender = new CreditCardPaymentRecommender();

    @BeforeEach
    void setup() {
    }

    @Test
    void testLineOfBusinessNotSupported() {
        final User user = new User(UUID.randomUUID().toString(), null, null);
        final Cart cart = new Cart(null, null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> paymentRecommender.recommendPaymentInstruments(user, cart));
        assertEquals("The line of business is not supported", ex.getMessage());
    }

    @Test
    void testCreditCardWithNoInstruments() {
        final User user = new User(UUID.randomUUID().toString(), null, new UserPaymentInstrument(new ArrayList<>()));
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, new CartDetail(100d, Collections.singletonList(getCartItem(100d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testCreditCardWithCreditCardInstrument() {
        final PaymentInstrument creditCardPaymentInstrument = createCreditCardPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), null,
                new UserPaymentInstrument(Collections.singletonList(creditCardPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, new CartDetail(100d, Collections.singletonList(getCartItem(100d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testCreditCardWithUPIInstrumentButNotUPIEnabled() {
        final PaymentInstrument upiPaymentInstrument = createUPIPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(false)),
                new UserPaymentInstrument(Collections.singletonList(upiPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, new CartDetail(100d, Collections.singletonList(getCartItem(100d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testCreditCardWithUPIInstrumentButLimitBreached() {
        final PaymentInstrument upiPaymentInstrument = createUPIPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(true)),
                new UserPaymentInstrument(Collections.singletonList(upiPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT,
                new CartDetail(1000001d, Collections.singletonList(getCartItem(1000001d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testCreditCardWithNetBankingButLimitBreached() {
        final PaymentInstrument netBankingPaymentInstrument = createNetBankingPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(false)),
                new UserPaymentInstrument(Collections.singletonList(netBankingPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT,
                new CartDetail(2000001d, Collections.singletonList(getCartItem(2000001d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testCreditCardWithDebitCardButLimitBreached() {
        final PaymentInstrument debitCardInstrument = createDebitCardInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(false)),
                new UserPaymentInstrument(Collections.singletonList(debitCardInstrument)));
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT,
                new CartDetail(2000001d, Collections.singletonList(getCartItem(2000001d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testCreditCardHappyScenario() {
        final PaymentInstrument cardInstrument1 = createCreditCardPaymentInstrument();
        final PaymentInstrument upiPaymentInstrument1 = createUPIPaymentInstrument();
        final PaymentInstrument upiPaymentInstrument2 = createUPIPaymentInstrument();
        upiPaymentInstrument2.setIssuer(Issuer.SBI);
        upiPaymentInstrument2.setRelevanceScore(0.88);
        final PaymentInstrument netBankingPaymentInstrument = createNetBankingPaymentInstrument();
        final PaymentInstrument debitCardInstrument = createDebitCardInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(true)), new UserPaymentInstrument(
                Arrays.asList(cardInstrument1, upiPaymentInstrument1, upiPaymentInstrument2, netBankingPaymentInstrument, debitCardInstrument)));
        final List<PaymentInstrument> expectedInstrumentList = Lists.newArrayList(upiPaymentInstrument2, upiPaymentInstrument1,
                netBankingPaymentInstrument, debitCardInstrument);
        final Cart cart = new Cart(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, new CartDetail(1000d, Collections.singletonList(getCartItem(1000d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertEquals(4, actualList.size());
        assertEquals(expectedInstrumentList, actualList);
    }

    private PaymentInstrument createCreditCardPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.CREDIT_CARD, "credit_card_number", Issuer.HDFC, "HDFC Diner", 0.7);
    }

    private PaymentInstrument createUPIPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.UPI, "upi_handle", Issuer.SBI, "SBI UPI", 0.85);
    }

    private PaymentInstrument createNetBankingPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.NETBANKING, "bank_account_number", Issuer.SBI, "Savings Account", 0.86);
    }

    private PaymentInstrument createDebitCardInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.DEBIT_CARD, "debit_card_number", Issuer.SBI, "SBI Debit Card", 0.91);
    }

    private CartItem getCartItem(final Double amount) {
        return new CartItem(UUID.randomUUID().toString(), "Amex CC Bill Payment", amount);
    }
}
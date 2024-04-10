package com.paymentrecommendation.models;

public class User {
    private String userId;
    // There can be other attributes of user like: name, phone number gender, date
    // of birth..

    private UserContext userContext;

    private UserPaymentInstrument userPaymentInstrument;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public User(String userId, UserContext userContext, UserPaymentInstrument userPaymentInstrument) {
        this.userId = userId;
        this.userContext = userContext;
        this.userPaymentInstrument = userPaymentInstrument;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    public UserPaymentInstrument getUserPaymentInstrument() {
        return userPaymentInstrument;
    }

    public void setUserPaymentInstrument(UserPaymentInstrument userPaymentInstrument) {
        this.userPaymentInstrument = userPaymentInstrument;
    }
}

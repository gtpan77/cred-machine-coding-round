package com.paymentrecommendation.models;

import java.util.Objects;
import com.paymentrecommendation.enums.Issuer;
import com.paymentrecommendation.enums.PaymentInstrumentType;

public class PaymentInstrument {
    private PaymentInstrumentType paymentInstrumentType;

    private String identifier;

    private Issuer issuer;

    private String name;

    private Double relevanceScore;

    public PaymentInstrument(PaymentInstrumentType paymentInstrumentType, String identifier, Issuer issuer, String name,
            Double relevanceScore) {
        this.paymentInstrumentType = paymentInstrumentType;
        this.identifier = identifier;
        this.issuer = issuer;
        this.name = name;
        this.relevanceScore = relevanceScore;
    }

    public PaymentInstrumentType getPaymentInstrumentType() {
        return paymentInstrumentType;
    }

    public void setPaymentInstrumentType(PaymentInstrumentType paymentInstrumentType) {
        this.paymentInstrumentType = paymentInstrumentType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentInstrument)) {
            return false;
        }
        PaymentInstrument that = (PaymentInstrument) o;
        return paymentInstrumentType == that.paymentInstrumentType && Objects.equals(identifier,
                that.identifier) && issuer == that.issuer && Objects.equals(name, that.name)
                && Objects.equals(relevanceScore, that.relevanceScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentInstrumentType, identifier, issuer, name, relevanceScore);
    }

    @Override
    public String toString() {
        return "PaymentInstrument{" + "paymentInstrumentType=" + paymentInstrumentType + ", identifier='" + identifier
                + '\'' + ", issuer=" + issuer + ", name='" + name + '\'' + ", relevanceScore=" + relevanceScore + '}';
    }
}

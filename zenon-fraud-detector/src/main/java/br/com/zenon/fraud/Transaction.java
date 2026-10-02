package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(int step, Type type, BigDecimal amount, String nameOrig, BigDecimal oldbalanceOrg,
                          BigDecimal newbalanceOrig, String nameDest, BigDecimal oldbalanceDest, BigDecimal newbalanceDest,
                          boolean isFraud, boolean isFlaggedFraud) {

    public enum Type{
        CASH_IN, CASH_OUT, DEBIT, PAYMENT, TRANSFER
    }
}

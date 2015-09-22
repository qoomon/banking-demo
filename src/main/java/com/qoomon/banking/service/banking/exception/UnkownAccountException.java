package com.qoomon.banking.service.banking.exception;

import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;

public class UnkownAccountException extends BankingException {

    private static final long serialVersionUID = 1L;

    public UnkownAccountException(SimpleIBAN iban) {
        super("unkown account " + iban);
    }

}

package com.qoomon.banking.repository;

import com.google.common.base.Preconditions;
import com.qoomon.banking.service.banking.domain.object.BankAccount;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;

import java.util.HashMap;
import java.util.Map;

public class BankAccountRepository {

    private final Map<SimpleIBAN, BankAccount> bankAccountRepository = new HashMap<>();

    /**
     * @param bankAccount <br>
     *                    &emsp;<b>require</b> not null
     */
    public void save(BankAccount bankAccount) {
        Preconditions.checkNotNull(bankAccount);
        Preconditions.checkState(!bankAccountRepository.containsKey(bankAccount.getIban()), "IBAN already exist");
        bankAccountRepository.put(bankAccount.getIban(), bankAccount);
    }

    /**
     * @param sourceIban <br>
     *                   &emsp;<b>require</b> not null
     * @return bank account or null
     */
    public BankAccount findByIban(SimpleIBAN sourceIban) {
        return bankAccountRepository.get(sourceIban);
    }
}

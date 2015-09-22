package com.qoomon.banking.service.banking;

import org.joda.money.BigMoney;

import com.google.common.base.Preconditions;
import com.qoomon.banking.repository.BankAccountRepository;
import com.qoomon.banking.service.banking.domain.object.BankAccount;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;
import com.qoomon.banking.service.banking.exception.UnkownAccountException;

public class BankingService {
    private final BankAccountRepository bankAccountRepository;

    public BankingService(BankAccountRepository bankAccountRepository) {
        super();
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * 
     * @param destinationIban
     *            <br>
     *            &emsp;<b>require</b> not null
     * @param amountToDeposit
     *            <br>
     *            &emsp;<b>require</b> not null
     *            <br>
     *            &emsp;<b>require</b> {@link BigMoney#isPositive()}
     *            <br>
     *            &emsp;<b>require</b> currency to match {@link BankAccount#getCurrency()}
     */
    public void deposit(SimpleIBAN destinationIban, BigMoney amountToDeposit) {
        Preconditions.checkNotNull(destinationIban);
        Preconditions.checkNotNull(amountToDeposit);
        Preconditions.checkArgument(amountToDeposit.isPositive());

        BankAccount destinationBankAccount = bankAccountRepository.findByIban(destinationIban);
        if (destinationBankAccount == null) {
            throw new UnkownAccountException(destinationIban);
        }
        destinationBankAccount.deposit(amountToDeposit);

    }

    /**
     * 
     * @param sourceIban
     *            <br>
     *            &emsp;<b>require</b> not null
     * @param amountToWithdraw
     *            <br>
     *            &emsp;<b>require</b> not null
     *            <br>
     *            &emsp;<b>require</b> {@link BigMoney#isPositive()}
     *            <br>
     *            &emsp;<b>require</b> currency to match {@link BankAccount#getCurrency()}
     */
    public void withdraw(SimpleIBAN sourceIban, BigMoney amountToWithdraw) {
        Preconditions.checkNotNull(sourceIban);
        Preconditions.checkNotNull(amountToWithdraw);
        Preconditions.checkArgument(amountToWithdraw.isPositive());

        BankAccount sourceBankAccount = bankAccountRepository.findByIban(sourceIban);
        if (sourceBankAccount == null) {
            throw new UnkownAccountException(sourceIban);
        }

        sourceBankAccount.withdraw(amountToWithdraw);

    }

    /**
     * 
     * @param sourceIban
     *            <br>
     *            &emsp;<b>require</b> not null
     * @param destinationIban
     *            <br>
     *            &emsp;<b>require</b> not null
     *            <br>
     *            &emsp;<b>require</b> currency to match {@link BankAccount#getCurrency()}
     * @param amountToTransfere
     *            <br>
     *            &emsp;<b>require</b> not null
     *            <br>
     *            &emsp;<b>require</b> {@link BigMoney#isPositive()}
     *            <br>
     *            &emsp;<b>require</b> currency to match {@link BankAccount#getCurrency()}
     */
    public void transfer(SimpleIBAN sourceIban, SimpleIBAN destinationIban, BigMoney amountToTransfere) {
        Preconditions.checkNotNull(sourceIban);
        Preconditions.checkNotNull(destinationIban);
        Preconditions.checkNotNull(amountToTransfere);
        Preconditions.checkArgument(amountToTransfere.isPositive());

        BankAccount sourceBankAccount = bankAccountRepository.findByIban(sourceIban);
        if (sourceBankAccount == null) {
            throw new UnkownAccountException(sourceIban);
        }
        BankAccount destinationBankAccount = bankAccountRepository.findByIban(destinationIban);
        if (destinationBankAccount == null) {
            throw new UnkownAccountException(destinationIban);
        }

        sourceBankAccount.withdraw(amountToTransfere);
        destinationBankAccount.deposit(amountToTransfere);
    }

    /**
     * 
     * @param accountIban
     *            <br>
     *            &emsp;<b>require</b> not null
     * @return bank account or null
     */
    public BankAccount getAccountByIban(SimpleIBAN accountIban) {
        Preconditions.checkNotNull(accountIban);
        return bankAccountRepository.findByIban(accountIban);

    }
}

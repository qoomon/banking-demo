package com.qoomon.banking.service.banking.domain.object;

import com.google.common.base.Preconditions;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * represent a bank account
 *
 * @author qoomon
 */
public class BankAccount {

    private final Bank bank;
    private final SimpleIBAN iban;
    private final CurrencyUnit currency;
    private BigMoney balance;

    public BankAccount(Bank bank, SimpleIBAN iban, CurrencyUnit currency) {
        super();
        this.bank = checkNotNull(bank);
        this.iban = checkNotNull(iban);
        this.currency = checkNotNull(currency);
        this.balance = BigMoney.of(currency, 0.0);
    }

    public SimpleIBAN getIban() {
        return this.iban;
    }

    public Bank getBank() {
        return this.bank;
    }

    public CurrencyUnit getCurrency() {
        return this.currency;
    }

    public BigMoney getBalance() {
        return this.balance;
    }

    /**
     * Deposit given amount
     *
     * @param amountToDeposit <br>
     *                        &emsp;<b>require</b> not null
     *                        <br>
     *                        &emsp;<b>require</b> to match {@link #getCurrency()}
     */
    public void deposit(BigMoney amountToDeposit) {
        Preconditions.checkNotNull(amountToDeposit);
        this.balance = this.balance.plus(amountToDeposit);
    }

    /**
     * Withdraw given amount
     *
     * @param amountToWithdraw <br>
     *                         &emsp;<b>require</b> not null
     *                         <br>
     *                         &emsp;<b>require</b> to match {@link #getCurrency()}
     */
    public void withdraw(BigMoney amountToWithdraw) {
        Preconditions.checkNotNull(amountToWithdraw);
        this.balance = this.balance.minus(amountToWithdraw);
    }

}

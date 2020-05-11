package com.qoomon.banking.service.banking;

import com.qoomon.banking.repository.BankAccountRepository;
import com.qoomon.banking.service.banking.domain.object.Bank;
import com.qoomon.banking.service.banking.domain.object.BankAccount;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyMismatchException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.CurrencyUnit.USD;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankingServiceTest {

    @Test
    public void deposit() throws Exception {
        // GIVEN
        SimpleIBAN destinationIban = SimpleIBAN.of("DE" + "21" + "123456");
        BankAccount destinationBankAcount = new BankAccount(mock(Bank.class), destinationIban, EUR);
        BigMoney startBalance = BigMoney.of(EUR, 666.66);
        destinationBankAcount.deposit(startBalance);

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        when(bankAccountRepository.findByIban(destinationIban)).thenReturn(destinationBankAcount);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToDeposit = BigMoney.of(EUR, 1337.13);

        // WHEN
        bankingService.deposit(destinationIban, ammountToDeposit);

        // THEN
        assertThat(destinationBankAcount.getBalance()).isEqualTo(startBalance.plus(ammountToDeposit));
    }

    @Test
    public void deposit_negetiveAmount() throws Exception {
        // GIVEN
        SimpleIBAN destinationIban = SimpleIBAN.of("DE" + "21" + "123456");

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToDeposit = BigMoney.of(EUR, -1337.13);

        // WHEN
        Throwable exception = catchThrowable(() -> {
            bankingService.deposit(destinationIban, ammountToDeposit);
        });

        // THEN
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deposit_wrongCurrency() throws Exception {
        // GIVEN
        SimpleIBAN destinationIban = SimpleIBAN.of("DE" + "21" + "123456");
        BankAccount destinationBankAcount = new BankAccount(mock(Bank.class), destinationIban, EUR);
        BigMoney startBalance = BigMoney.of(EUR, 666.66);
        destinationBankAcount.deposit(startBalance);

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        when(bankAccountRepository.findByIban(destinationIban)).thenReturn(destinationBankAcount);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToDeposit = BigMoney.of(USD, 1337.13);

        // WHEN
        Throwable exception = catchThrowable(() -> {
            bankingService.deposit(destinationIban, ammountToDeposit);
        });

        // THEN
        assertThat(exception).isInstanceOf(CurrencyMismatchException.class);
    }

    @Test
    public void withdraw() throws Exception {
        // GIVEN
        SimpleIBAN sourceIban = SimpleIBAN.of("DE" + "21" + "123456");
        BankAccount sourceBankAcount = new BankAccount(mock(Bank.class), sourceIban, EUR);
        BigMoney startBalance = BigMoney.of(EUR, 666.66);
        sourceBankAcount.deposit(startBalance);

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        when(bankAccountRepository.findByIban(sourceIban)).thenReturn(sourceBankAcount);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToWithdraw = BigMoney.of(EUR, 1337.13);

        // WHEN
        bankingService.withdraw(sourceIban, ammountToWithdraw);

        // THEN
        assertThat(sourceBankAcount.getBalance()).isEqualTo(startBalance.minus(ammountToWithdraw));
    }

    @Test
    public void withdraw_negetiveAmount() throws Exception {
        // GIVEN
        SimpleIBAN sourceIban = SimpleIBAN.of("DE" + "21" + "123456");

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToWithdraw = BigMoney.of(EUR, -1337.13);

        // WHEN
        Throwable exception = catchThrowable(() -> {
            bankingService.withdraw(sourceIban, ammountToWithdraw);
        });

        // THEN
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void withdraw_wrongCurrency() throws Exception {
        // GIVEN
        SimpleIBAN sourceIban = SimpleIBAN.of("DE" + "21" + "123456");
        BankAccount sourceBankAcount = new BankAccount(mock(Bank.class), sourceIban, EUR);
        BigMoney startBalance = BigMoney.of(EUR, 666.66);
        sourceBankAcount.deposit(startBalance);

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        when(bankAccountRepository.findByIban(sourceIban)).thenReturn(sourceBankAcount);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToDeposit = BigMoney.of(USD, 1337.13);

        // WHEN
        Throwable exception = catchThrowable(() -> {
            bankingService.withdraw(sourceIban, ammountToDeposit);
        });

        // THEN
        assertThat(exception).isInstanceOf(CurrencyMismatchException.class);
    }

    @Test
    public void transfer() throws Exception {
        // GIVEN
        SimpleIBAN sourceIban = SimpleIBAN.of("DE" + "21" + "123456");
        BankAccount sourceBankAcount = new BankAccount(mock(Bank.class), sourceIban, EUR);
        BigMoney sourceStartBalance = BigMoney.of(EUR, 666.66);
        sourceBankAcount.deposit(sourceStartBalance);

        SimpleIBAN destinationIban = SimpleIBAN.of("DE" + "22" + "123457");
        BankAccount destinationBankAcount = new BankAccount(mock(Bank.class), destinationIban, EUR);
        BigMoney destinationStartBalance = BigMoney.of(EUR, 333.33);
        destinationBankAcount.deposit(destinationStartBalance);

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        when(bankAccountRepository.findByIban(sourceIban)).thenReturn(sourceBankAcount);
        when(bankAccountRepository.findByIban(destinationIban)).thenReturn(destinationBankAcount);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToTransfer = BigMoney.of(EUR, 1337.13);

        // WHEN
        bankingService.transfer(sourceIban, destinationIban, ammountToTransfer);

        // THEN
        assertThat(sourceBankAcount.getBalance()).isEqualTo(sourceStartBalance.minus(ammountToTransfer));
        assertThat(destinationBankAcount.getBalance()).isEqualTo(destinationStartBalance.plus(ammountToTransfer));
    }

    @Test
    public void transfer_negetiveAmount() throws Exception {
        // GIVEN
        SimpleIBAN sourceIban = SimpleIBAN.of("DE" + "21" + "123456");

        SimpleIBAN destinationIban = SimpleIBAN.of("DE" + "22" + "123457");

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);

        BankingService bankingService = new BankingService(bankAccountRepository);

        BigMoney ammountToTransfer = BigMoney.of(EUR, -1337.13);

        // WHEN
        Throwable exception = catchThrowable(() -> {
            bankingService.transfer(sourceIban, destinationIban, ammountToTransfer);
        });

        // THEN
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getAccountByIban() throws Exception {
        // GIVEN
        SimpleIBAN sourceIban = SimpleIBAN.of("DE" + "21" + "123456");
        BankAccount sourceBankAcount = new BankAccount(mock(Bank.class), sourceIban, EUR);
        BigMoney startBalance = BigMoney.of(EUR, 666.66);
        sourceBankAcount.deposit(startBalance);

        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        when(bankAccountRepository.findByIban(sourceIban)).thenReturn(sourceBankAcount);

        BankingService bankingService = new BankingService(bankAccountRepository);

        // WHEN
        BankAccount accountByIban = bankingService.getAccountByIban(sourceIban);

        // THEN
        assertThat(accountByIban).isEqualToComparingFieldByField(sourceBankAcount);

    }

}

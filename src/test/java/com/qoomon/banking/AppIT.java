package com.qoomon.banking;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.qoomon.banking.controller.BankingController;
import com.qoomon.banking.repository.BankAccountRepository;
import com.qoomon.banking.service.banking.BankingService;
import com.qoomon.banking.service.banking.domain.object.Bank;
import com.qoomon.banking.service.banking.domain.object.BankAccount;
import com.qoomon.banking.service.banking.domain.value.SimpleBIC;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;

public class AppIT {

    @Test
    public void transferMoney() throws Exception {

        // GIVEN

        BankAccountRepository bankAccountRepository = new BankAccountRepository();
        BankingService bookingService = new BankingService(bankAccountRepository);
        BankingController bankingController = new BankingController(bookingService);

        Bank bank = new Bank(SimpleBIC.of("XXXX" + "DE" + "12" + "789"), "QooBank");
        BankAccount bankAccount1 = new BankAccount(bank, SimpleIBAN.of("DE" + "21" + "123456"), EUR);
        bankAccountRepository.save(bankAccount1);
        BankAccount bankAccount2 = new BankAccount(bank, SimpleIBAN.of("DE" + "28" + "1234567"), EUR);
        bankAccountRepository.save(bankAccount2);

        ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
        PrintStream testOutput = new PrintStream(dataOut);

        String input = "";
        input += "transfer account:DE21123456 destination:DE281234567 amount:EUR1.67" + System.lineSeparator();
        input += "no" + System.lineSeparator();
        InputStream testInput = new ByteArrayInputStream(input.getBytes());

        App app = new App(bankingController, testInput, testOutput);

        // WHEN

        app.run();

        // THEN
        assertThat(dataOut.toString()).containsSequence(
                "balance:EUR -1.67",
                "destination balance:EUR 1.67",
                "Anything else i can do for you Sir? ",
                "Goodbye Sir.");

    }
}

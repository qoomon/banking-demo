package com.qoomon.banking.controller;

import com.google.common.base.Preconditions;
import com.qoomon.banking.service.banking.BankingService;
import com.qoomon.banking.service.banking.domain.object.BankAccount;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;
import org.joda.money.BigMoney;

import java.util.Map;

public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        super();
        this.bankingService = Preconditions.checkNotNull(bankingService);
    }

    public StringBuilder getBalance(Map<String, String> args) {
        StringBuilder response = new StringBuilder();
        boolean argsAreValid = true;

        String accountText = args.get("account");
        SimpleIBAN accountIban = null;
        if (SimpleIBAN.isValid(accountText)) {
            accountIban = SimpleIBAN.of(accountText);
        } else {
            argsAreValid = false;
            response.append("account is not valid").append(System.lineSeparator());
        }

        if (argsAreValid) {
            BankAccount bankAccount = bankingService.getAccountByIban(accountIban);
            response.append("balance: " + bankAccount.getBalance()).append(System.lineSeparator());
        }

        return response;
    }

    public StringBuilder deposit(Map<String, String> args) {
        StringBuilder response = new StringBuilder();
        boolean argsAreValid = true;

        String destinationText = args.get("account");
        SimpleIBAN destinationIban = null;
        if (SimpleIBAN.isValid(destinationText)) {
            destinationIban = SimpleIBAN.of(destinationText);
        } else {
            argsAreValid = false;
            response.append("account is not valid").append(System.lineSeparator());
        }

        String amountText = args.get("amount");
        BigMoney amountToDeposit = validateBigMoney(amountText);
        if (amountToDeposit == null) {
            argsAreValid = false;
            response.append("amount is not valid").append(System.lineSeparator());
        }

        if (argsAreValid) {
            bankingService.deposit(destinationIban, amountToDeposit);
            BankAccount bankAccount = bankingService.getAccountByIban(destinationIban);
            response.append("balance:" + bankAccount.getBalance()).append(System.lineSeparator());
        }

        return response;
    }

    public StringBuilder withdraw(Map<String, String> args) {
        StringBuilder response = new StringBuilder();
        boolean argsAreValid = true;

        String sourceText = args.get("account");
        SimpleIBAN sourceIban = null;
        if (SimpleIBAN.isValid(sourceText)) {
            sourceIban = SimpleIBAN.of(sourceText);
        } else {
            argsAreValid = false;
            response.append("account is not valid").append(System.lineSeparator());
        }

        String amountText = args.get("amount");
        BigMoney amountToDeposit = validateBigMoney(amountText);
        if (amountToDeposit == null) {
            argsAreValid = false;
            response.append("amount is not valid").append(System.lineSeparator());
        }

        if (argsAreValid) {
            bankingService.withdraw(sourceIban, amountToDeposit);
            BankAccount bankAccount = bankingService.getAccountByIban(sourceIban);
            response.append("balance:" + bankAccount.getBalance()).append(System.lineSeparator());
        }

        return response;
    }

    public StringBuilder transfer(Map<String, String> args) {
        StringBuilder response = new StringBuilder();
        boolean argsAreValid = true;

        String sourceText = args.get("account");
        SimpleIBAN sourceIban = null;
        if (SimpleIBAN.isValid(sourceText)) {
            sourceIban = SimpleIBAN.of(sourceText);
        } else {
            argsAreValid = false;
            response.append("account is not valid").append(System.lineSeparator());
        }

        String destinationText = args.get("destination");
        SimpleIBAN destinationIban = null;
        if (SimpleIBAN.isValid(destinationText)) {
            destinationIban = SimpleIBAN.of(destinationText);
        } else {
            argsAreValid = false;
            response.append("destination is not valid").append(System.lineSeparator());
        }

        String amountText = args.get("amount");
        BigMoney amountToDeposit = validateBigMoney(amountText);
        if (amountToDeposit == null) {
            argsAreValid = false;
            response.append("amount is not valid").append(System.lineSeparator());
        }

        if (argsAreValid) {
            bankingService.transfer(sourceIban, destinationIban, amountToDeposit);
            BankAccount bankAccount = bankingService.getAccountByIban(sourceIban);
            response.append("balance:" + bankAccount.getBalance()).append(System.lineSeparator());
            BankAccount destinationBankAccount = bankingService.getAccountByIban(destinationIban);
            response.append("destination balance:" + destinationBankAccount.getBalance()).append(System.lineSeparator());
        }

        return response;
    }

    private BigMoney validateBigMoney(String amountText) {
        try {
            return BigMoney.parse(amountText);
        } catch (Exception e) {
            return null;
        }
    }

}

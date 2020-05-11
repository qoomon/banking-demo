package com.qoomon.banking;

import com.qoomon.banking.controller.BankingController;
import com.qoomon.banking.repository.BankAccountRepository;
import com.qoomon.banking.service.banking.BankingService;
import com.qoomon.banking.service.banking.domain.object.Bank;
import com.qoomon.banking.service.banking.domain.object.BankAccount;
import com.qoomon.banking.service.banking.domain.value.SimpleBIC;
import com.qoomon.banking.service.banking.domain.value.SimpleIBAN;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.joda.money.CurrencyUnit.EUR;

/**
 * Bank Demo Application
 *
 * <ul>
 * <li><b>deposit</b> account:<b>IBAN</b> amount:<b>AMOUNT_CURRENCY</b></li>
 * <li><b>withdraw</b> account:<b>IBAN</b> amount:<b>AMOUNT_CURRENCY</b></li>
 * <li><b>transfer</b> account:<b>IBAN</b> destination:<b>IBAN</b> amount:
 * <b>AMOUNT_CURRENCY</b></li>
 * <li><b>balance</b> account:<b>IBAN</b></li>
 * </ul>
 * Account 1: <b>DE21123456</b> <br>
 * Account 2: <b>DE281234567</b>
 */
public class App {

    public static void main(String[] args) {

        // Setup
        BankAccountRepository bankAccountRepository = new BankAccountRepository();
        BankingService bookingService = new BankingService(bankAccountRepository);
        BankingController bankingController = new BankingController(bookingService);

        // DEMO DATA
        Bank bank = new Bank(SimpleBIC.of("XXXX" + "DE" + "12" + "789"), "QooBank");
        BankAccount bankAccount1 = new BankAccount(bank, SimpleIBAN.of("DE" + "21" + "123456"), EUR);
        bankAccountRepository.save(bankAccount1);
        BankAccount bankAccount2 = new BankAccount(bank, SimpleIBAN.of("DE" + "28" + "1234567"), EUR);
        bankAccountRepository.save(bankAccount2);

        // Run
        App app = new App(bankingController, System.in, System.out);
        app.run();

    }

    private final BankingController bankingController;
    private final PrintStream consoleOutpuStream;
    private final InputStream consoleInputStream;

    public App(BankingController bankingController, InputStream consoleInputStream, PrintStream consoleOutpuStream) {
        this.bankingController = bankingController;
        this.consoleInputStream = consoleInputStream;
        this.consoleOutpuStream = consoleOutpuStream;
    }

    public void run() {
        try (Scanner consoleInputScanner = new Scanner(consoleInputStream)) {
            String lastCommand = null;
            consoleOutpuStream.println("How can i help you Sir? ");
            boolean exit = false;
            while (!exit) {
                // read command
                String command = consoleInputScanner.next();
                String arguments = consoleInputScanner.nextLine();
                HashMap<String, String> argumentMap = parseArguments(arguments);

                // process command
                CharSequence response = "";
                switch (command) {
                    case "deposit":
                        lastCommand = command;
                        response = bankingController.deposit(argumentMap);
                        break;
                    case "withdraw":
                        lastCommand = command;
                        response = bankingController.withdraw(argumentMap);
                        break;
                    case "transfer":
                        lastCommand = command;
                        response = bankingController.transfer(argumentMap);
                        break;
                    case "balance":
                        lastCommand = command;
                        response = bankingController.getBalance(argumentMap);
                        break;
                    case "no":
                    case "bye":
                        lastCommand = command;
                        response = "Goodbye Sir.";
                        exit = true;
                        break;
                    default:
                        lastCommand = null;
                        response = "Pardon Sir?";
                        break;
                }
                consoleOutpuStream.println(response);

                if (!exit && lastCommand != null) {
                    consoleOutpuStream.println("Anything else i can do for you Sir? ");
                }
            }
        }
    }

    private static HashMap<String, String> parseArguments(String arguments) {
        HashMap<String, String> argumentMap = new HashMap<>();
        for (String argument : arguments.split(" ")) {
            String[] argumentSplit = argument.split(":");
            String key = argumentSplit[0];
            String value = argumentSplit.length > 1 ? argumentSplit[1] : "";
            argumentMap.put(key, value);
        }
        return argumentMap;
    }

}

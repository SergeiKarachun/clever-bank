package by.sergey.controller;

import by.sergey.model.BankOpenAccountModel;
import by.sergey.model.Currency;
import by.sergey.model.User;
import by.sergey.model.UserBankAccount;
import by.sergey.view.BankOpenAccountView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class BankOpenAccount {
    private BankOpenAccount() {
    }

    protected static void run(User user) throws SQLException {
        UserBankAccount newAccount;
        int currencyID;
        BigDecimal balance = BigDecimal.valueOf(takeBalance());
        Currency currencyType;
        currencyID = takeAccountCurrency();
        currencyType = Currency.typeInIndex(currencyID - 1);
        Long randomNumber = Long.parseLong(String.format("%08d", new Random().nextInt(90000000)));
        newAccount = new UserBankAccount(user.getUsername(), randomNumber, balance, currencyType, null, LocalDate.now().toString());
        BankOpenAccountModel.saveNewAccount(user, newAccount);
    }

    private static Integer takeAccountCurrency() {
        Currency.showAll();
        String currencyID;
        do {
            currencyID = BankOpenAccountView.takeCurrency();
        } while (!currencyID.matches("[1-4]"));
        return Integer.valueOf(currencyID);
    }

    private static Integer takeBalance() {
        String balance;
        do {
            balance = BankOpenAccountView.takeInitialBalance();
        } while (!balance.matches("[0-9]+"));
        return Integer.valueOf(balance);
    }
}

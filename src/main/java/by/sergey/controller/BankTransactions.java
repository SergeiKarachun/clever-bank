package by.sergey.controller;

import by.sergey.model.BankTransactionsModel;
import by.sergey.model.Transaction;
import by.sergey.model.UserBankAccount;
import by.sergey.view.BankTransactionsView;

import java.sql.SQLException;
import java.util.List;

public class BankTransactions {

    private static final Integer LIMIT = 5;

    private BankTransactions() {
    }


    public static void run(UserBankAccount userBankAccount) throws SQLException {
        boolean wantMore;
        Integer numberOfTransaction = BankTransactionsModel.getNumberOfTransactions(userBankAccount.getUsername());
        Integer offsetNumber = 0;
        do {
            List<Transaction> transactions = BankTransactionsModel.collectTransactions(userBankAccount.getUsername(),
                    LIMIT, offsetNumber);
            BankTransactionsView.displayTransaction(transactions);
            offsetNumber += 5;
            wantMore = checkWantMoreTransactions();
        } while (wantMore && offsetNumber <= numberOfTransaction);
    }

    private static boolean checkWantMoreTransactions() {
        String respond;
        do {
            respond = BankTransactionsView.askMoretransactions();
        } while (!respond.equals("y") && !respond.equals("n"));
        return respond.equals("y");
    }
}

package by.sergey.view;

import by.sergey.model.BankUtil;
import by.sergey.model.Transaction;

import java.util.List;

public class BankTransactionsView {
    public static void displayTransaction(List<Transaction> transactions) {
        BankUtil.createHeader("My transactions");
        for (int i = 0; i < transactions.size(); ++i) {
            BankUtil.createMessage("Account number (" + (i + 1) + ")");
            System.out.println(transactions.get(i));
        }
    }

    public static String askMoretransactions() {
        BankUtil.createMessage("Want more transactions? y(yes), n(no)");
        return BankUtil.scanner.nextLine();
    }
}

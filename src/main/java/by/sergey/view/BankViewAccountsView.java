package by.sergey.view;

import by.sergey.model.BankUtil;
import by.sergey.model.UserBankAccount;

import java.util.List;

public class BankViewAccountsView {

    public static void showUserBankAccountFunctions() {
        BankUtil.createHeader("What do you want to do?");
        BankUtil.createOrderedList(new String[]{
                "Deposit",
                "Withdraw",
                "Transfer",
                "Show transactions",
                "Close bank account",
                "Exit"
        });
    }

    public static String takeAccountNumber() {
        System.out.println("Enter bank account number from the list to do this function on: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeChoice() {
        BankUtil.showTakeFunctionNumber();
        return BankUtil.scanner.nextLine();
    }

    public static void printAccounts(List<UserBankAccount> userBankAccounts) {
        BankUtil.createHeader("My Bank Accounts");
        for (int i = 0; i < userBankAccounts.size(); ++i) {
            BankUtil.createMessage("Account number (" + (i + 1) + ")");
            System.out.println(userBankAccounts.get(i));
        }
    }

    public static void showEmptyAccounts() {
        BankUtil.createMessage("You don't have any account yet!");
    }
}

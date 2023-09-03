package by.sergey.view;

import by.sergey.model.BankUtil;

public class BankOpenAccountView {

    public static String takeCurrency() {
        System.out.print("Enter the currency number: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeInitialBalance() {
        System.out.print("Enter the initial balance for this bank account: ");
        return BankUtil.scanner.nextLine();
    }
}

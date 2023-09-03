package by.sergey.view;

import by.sergey.model.BankUtil;

public class BankApplicationView {
    public static void displayMainWindow() {
        BankUtil.createHeader("Please, choose what you want:");
        BankUtil.createOrderedList(new String[]{"Login", "Register", "Exit"});
    }

    public static String takeOption() {
        BankUtil.showTakeFunctionNumber();
        return BankUtil.scanner.nextLine();
    }
}

package by.sergey.view;

import by.sergey.model.BankUtil;

public class BankTransferView {
    public static String takeMoney() {
        System.out.print("Enter money to be transferred: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeOtherAccount() {
        System.out.print("Enter the account number of the other user bank account(-1 to exit): ");
        return BankUtil.scanner.nextLine();
    }

    public static void showInvalidAccount() {
        BankUtil.createMessage("This bank account number doesn't exist!");
    }

    public static void showSameAccount() {
        BankUtil.createMessage("You cannot transfer to the same bank account!");
    }
}

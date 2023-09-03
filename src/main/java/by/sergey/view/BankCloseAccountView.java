package by.sergey.view;

import by.sergey.model.BankUtil;

public class BankCloseAccountView {

    public static void showSuccessfulClosing() {
        BankUtil.createMessage("Successfully closed!");
    }
}

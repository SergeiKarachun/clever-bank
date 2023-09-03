package by.sergey.controller;

import by.sergey.model.BankCloseAccountModel;
import by.sergey.model.UserBankAccount;
import by.sergey.view.BankCloseAccountView;

import java.sql.SQLException;

public class BankCloseAccount {
    private BankCloseAccount() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        BankCloseAccountModel.closeAccount(userBankAccount);
        BankCloseAccountView.showSuccessfulClosing();
    }
}

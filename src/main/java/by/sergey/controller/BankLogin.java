package by.sergey.controller;

import by.sergey.model.BankLoginModel;
import by.sergey.model.User;
import by.sergey.view.BankLoginView;

import java.sql.SQLException;

public class BankLogin {
    private BankLogin(){}

    public static void run() throws SQLException {
        String[] credentials = BankLoginView.displayLoginWindow();
        String username = credentials[0];
        String password = credentials[1];
        checkLogin(username, password);
    }

    private static void checkLogin(String username, String password) throws SQLException {
        String resultPassword = BankLoginModel.getPasswordOf(username);
        if (resultPassword == null || !resultPassword.equals(password)) {
            BankLoginView.showWrongPassword();
        } else {
            User user = BankLoginModel.collectUserData(username, password);
            BankUserProfile.run(user);
        }
    }
}

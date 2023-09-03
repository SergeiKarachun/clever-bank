package by.sergey.controller;

import by.sergey.model.BankRegistrationModel;
import by.sergey.model.BankUtil;
import by.sergey.model.User;
import by.sergey.view.BankRegistrationView;

import java.sql.SQLException;

public class BankRegistration {
    private BankRegistration() {
    }

    public static void run() throws SQLException {
        String username, password, phoneNumber, address, email;
        String[] fullName;
        User user;
        username = registerUsername();
        password = registerPassword();
        fullName = registerName();
        email = registerEmail();
        phoneNumber = registerPhoneNumber();
        user = new User(username, password, fullName[0], fullName[1], email, phoneNumber);
        BankRegistrationModel.saveNewUser(user);
    }

    public static String registerUsername() throws SQLException {
        String username;
        do {
            username = BankRegistrationView.takeUsername();
        } while (!checkUsername(username));
        return username;
    }

    public static Boolean checkUsername(String username) throws SQLException {
        if (username.length() > 50 || !BankRegistrationModel.checkUniqueUsername(username)) {
            BankRegistrationView.showWrongUsername();
            return false;
        } else {
            return true;
        }
    }

    public static String registerPassword() {
        String password;
        do {
            password = BankRegistrationView.takePassword();
        } while (!BankUtil.checkPassword(password));
        return password;
    }

    public static String[] registerName() {
        String[] fullName = new String[2];
        do {
            fullName[0] = BankRegistrationView.takeFirstName();
        } while (!BankUtil.checkName(fullName[0]));

        do {
            fullName[1] = BankRegistrationView.takeLastName();
        } while (!BankUtil.checkName(fullName[1]));

        return fullName;
    }

    public static String registerPhoneNumber() {
        String phoneNumber;
        do {
            phoneNumber = BankRegistrationView.takePhoneNumber();
        } while (!BankUtil.checkPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    public static String registerEmail() throws SQLException {
        String email;
        boolean uniqueEmail;
        do {
            email = BankRegistrationView.takeEmail();
            uniqueEmail = BankRegistrationModel.checkUniqueEmail(email);
            if (!uniqueEmail) {
                BankRegistrationView.showUnUniqueEmail();
            }
        } while (!BankUtil.checkEmail(email) || !uniqueEmail);
        return email;
    }
}

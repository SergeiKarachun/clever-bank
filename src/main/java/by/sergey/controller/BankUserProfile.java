package by.sergey.controller;

import by.sergey.model.BankUtil;
import by.sergey.model.User;
import by.sergey.view.BankUserProfileView;

import java.sql.SQLException;

public class BankUserProfile {
    private BankUserProfile() {}

    public static void run(User user) throws SQLException {
        BankUserProfileView.showWelcome(user);
        Integer choice;
        do {
            BankUserProfileView.showProfileFunctions();
            choice = takeProfileFunctionChoice(user);
        } while (!choice.equals(4));
    }

    private static Integer takeProfileFunctionChoice(User user) throws SQLException {
        String choice;
        do {
            choice = BankUserProfileView.takeChoice();
        } while (!choice.matches("[1-4]"));
        runProfileFunction(user, Integer.valueOf(choice));
        return Integer.valueOf(choice);
    }

    private static void runProfileFunction(User user, Integer choice) throws SQLException {
        switch (choice) {
            case 1 -> BankModifyProfile.run(user);
            case 2 -> BankViewAccounts.run(user);
            case 3 -> BankOpenAccount.run(user);
            case 4 -> logout(user);

        }
    }

    private static void logout(User user) {
        BankUtil.createMessage("You successfully logout");
    }
}

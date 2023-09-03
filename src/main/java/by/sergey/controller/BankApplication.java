package by.sergey.controller;

import by.sergey.model.BankUtil;
import by.sergey.view.BankApplicationView;

import java.sql.SQLException;

public class BankApplication {
    private boolean state;

    public BankApplication(){
        this.state = true;
    }
    public void run() throws SQLException {
        do {
            BankApplicationView.displayMainWindow();
            takeMainWindowOption();
        } while (state);

        BankUtil.scanner.close();
        BankUtil.connection.close();
    }

    private void takeMainWindowOption() throws SQLException {
        String option;
        do {
            option = BankApplicationView.takeOption();
        } while (!option.matches("[1-3]"));
        runMainWindowOption(Integer.valueOf(option));
    }

    private void runMainWindowOption(Integer option) throws SQLException {
        switch (option) {
            case 1 -> BankLogin.run();
            case 2 -> BankRegistration.run();
            case 3 -> state = false;
        }
    }
}

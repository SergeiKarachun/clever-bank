package by.sergey;

import by.sergey.controller.BankApplication;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        BankApplication app = new BankApplication();
        app.run();
    }
}
package by.sergey.controller;

import by.sergey.model.BankUtil;

import java.sql.SQLException;

public class BankException extends SQLException {
    public void run() {
        BankUtil.createMessage("There is an error occurred");
    }
}

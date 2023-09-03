package by.sergey.controller;

import by.sergey.model.BankDepositModel;
import by.sergey.model.BankUtil;
import by.sergey.model.UserBankAccount;
import by.sergey.view.BankDepositView;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BankDeposit {
    private BankDeposit() {}

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        BigDecimal addedMoney = takeMoneyToDeposit();
        // if user want to exit this action
        if (addedMoney.equals(-1)) return;
        BankDepositModel.deposit(addedMoney, userBankAccount);
    }

    private static BigDecimal takeMoneyToDeposit() {
        String addedMoney;
        do {
            addedMoney = BankDepositView.takeMoney();
            // if user want to exit this action
            if (addedMoney.equals("-1")) return BigDecimal.valueOf(-1L);
        } while (!addedMoney.matches("[0-9]+"));
        return BigDecimal.valueOf(Long.parseLong(addedMoney));
    }
}

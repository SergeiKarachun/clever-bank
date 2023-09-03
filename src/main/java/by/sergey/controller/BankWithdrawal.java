package by.sergey.controller;

import by.sergey.model.BankWithdrawalModel;
import by.sergey.model.UserBankAccount;
import by.sergey.view.BankWithdrawalView;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BankWithdrawal {
    private BankWithdrawal() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        BigDecimal withdrawalMoney = takeMoneyToWithdraw(userBankAccount);
        // if user want to exit this action
        if (withdrawalMoney.equals(-1)) return;
        BankWithdrawalModel.withdraw(withdrawalMoney, userBankAccount);
    }

    private static BigDecimal takeMoneyToWithdraw(UserBankAccount userBankAccount) {
        String withdrawalMoney;
        BigDecimal decimalMoney;
        do {
            withdrawalMoney = BankWithdrawalView.takeMoney();
            decimalMoney = new BigDecimal(withdrawalMoney);
            // if user want to exit this action
            if (withdrawalMoney.equals("-1")) return BigDecimal.valueOf(-1L);
        } while (!withdrawalMoney.matches("[0-9]+") ||
                 -1 >= (decimalMoney.compareTo(userBankAccount.getBalance())));
        return decimalMoney;
    }
}

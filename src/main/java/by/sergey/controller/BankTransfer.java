package by.sergey.controller;

import by.sergey.model.BankTransferModel;
import by.sergey.model.UserBankAccount;
import by.sergey.view.BankTransferView;

import java.sql.SQLException;

public class BankTransfer {
    private BankTransfer() {}
    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        Long otherUserBankAccountNumber = connectToAnotherUserBankAccount(userBankAccount);
        // if user want to exit this action
        if (otherUserBankAccountNumber.equals(-1)) return;
        BankTransferModel.transfer(userBankAccount, otherUserBankAccountNumber);
    }

    private static Long connectToAnotherUserBankAccount(UserBankAccount userBankAccount) throws SQLException {
        String otherUserBankAccountNumber;
        do {
            otherUserBankAccountNumber = BankTransferView.takeOtherAccount();
            // if user want to exit this action
            if (otherUserBankAccountNumber.equals("-1")) return -1L;
        } while (!checkValidOtherUserBankAccountID(userBankAccount, otherUserBankAccountNumber));
        return Long.valueOf(otherUserBankAccountNumber);
    }

    private static boolean checkValidOtherUserBankAccountID(UserBankAccount userBankAccount,
                                                            String otherUserBankAccountID) throws SQLException {
        if (!otherUserBankAccountID.matches("[0-9]+")) {
            return false;
        }
        if (Long.valueOf(otherUserBankAccountID).equals(userBankAccount.getBankAccountNumber())) {
            BankTransferView.showSameAccount();
            return false;
        }
        return BankTransferModel.checkValidAccount(userBankAccount, otherUserBankAccountID);
    }
}

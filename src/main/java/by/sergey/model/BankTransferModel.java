package by.sergey.model;

import by.sergey.controller.BankException;
import by.sergey.view.BankTransferView;
import by.sergey.view.BankWithdrawalView;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransferModel {
    public static void transfer(UserBankAccount userBankAccount, Long otherUserBankAccountNumber)
            throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            transferMoney(userBankAccount, otherUserBankAccountNumber);
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    private static void transferMoney(UserBankAccount userBankAccount, Long otherUserBankAccountNumber)
            throws SQLException {
        BigDecimal transferredMoney = takeMoneyToBeTransferred(userBankAccount);
        transferMoneyToOtherUserBankAccount(transferredMoney, otherUserBankAccountNumber);
        withdrawMoneyFromThisUserBankAccount(userBankAccount, transferredMoney);
        // insert this transaction
        BankUtil.insertIntoTransactionsTable(new Transaction(
                userBankAccount.getBankAccountNumber(), otherUserBankAccountNumber, transferredMoney, TransactionType.TRANSFER
        ));
    }

    private static BigDecimal takeMoneyToBeTransferred(UserBankAccount userBankAccount) {

        String moneyToBeTransferred;
        BigDecimal decimalMoneyToBeTransferred;
        do {
            moneyToBeTransferred = BankTransferView.takeMoney();
            decimalMoneyToBeTransferred = new BigDecimal(moneyToBeTransferred);
            // if user want to exit this action
            if (moneyToBeTransferred.equals("-1")) return BigDecimal.valueOf(-1L);
        } while (!moneyToBeTransferred.matches("[0-9]+") ||
                 -1 >= (decimalMoneyToBeTransferred.compareTo(userBankAccount.getBalance())));
        return decimalMoneyToBeTransferred;

    }

    private static void transferMoneyToOtherUserBankAccount(BigDecimal transferredMoney,
                                                            Long otherUserBankAccountNumber) throws SQLException {
        String SQLStatement = """
                                update user_bank_account set balance = balance + ?
                                where  bank_account_number = ?
                """;
        updateUserBankAccountTable(SQLStatement, transferredMoney, otherUserBankAccountNumber);
    }

    private static void withdrawMoneyFromThisUserBankAccount(UserBankAccount userBankAccount,
                                                             BigDecimal transferredMoney) throws SQLException {
        String SQLStatement = """
                                update user_bank_account set balance = balance - ?
                                where  bank_account_number = ?
                """;
        updateUserBankAccountTable(SQLStatement, transferredMoney, userBankAccount.getBankAccountNumber());
    }

    private static void updateUserBankAccountTable(String SQLStatement, BigDecimal transferredMoney,
                                                   Long bankAccountNumber) throws SQLException {
        try {
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setBigDecimal(1, transferredMoney);
            statement.setLong(2, bankAccountNumber);
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    public static boolean checkValidAccount(UserBankAccount userBankAccount, String otherUserBankAccountID)
            throws SQLException {
        Boolean isValidBankAccount = null;
        try {
            String SQLStatement = """
                    select exists(select bank_account_number from user_bank_account where user_bank_account = ?) as isValidBankAccount
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, otherUserBankAccountID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            isValidBankAccount = resultSet.getBoolean("isValidBankAccount");
            if (!isValidBankAccount) {
                BankTransferView.showInvalidAccount();
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return isValidBankAccount;
    }
}

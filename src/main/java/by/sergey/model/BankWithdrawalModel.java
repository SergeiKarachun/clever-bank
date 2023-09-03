package by.sergey.model;

import by.sergey.controller.BankException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankWithdrawalModel {
    public static void withdraw(BigDecimal withdrawalMoney, UserBankAccount userBankAccount) throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            withdrawMoneyFromUserBankAccount(withdrawalMoney, userBankAccount);
            // insert this transaction
            BankUtil.insertIntoTransactionsTable(new Transaction(
                    userBankAccount.getBankAccountNumber(), withdrawalMoney, TransactionType.WITHDRAWAL
            ));
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    private static void withdrawMoneyFromUserBankAccount(BigDecimal withdrawalMoney, UserBankAccount userBankAccount)
            throws SQLException {
        try {
            String SQLStatement = """
                                    update user_bank_account set balance = balance -?
                                    where  bank_account_number = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setBigDecimal(1, withdrawalMoney);
            statement.setLong(2, userBankAccount.getBankAccountNumber());
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}

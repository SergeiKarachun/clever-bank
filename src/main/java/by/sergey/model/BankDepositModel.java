package by.sergey.model;

import by.sergey.controller.BankException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BankDepositModel {
    public static void deposit(BigDecimal addedMoney, UserBankAccount userBankAccount) throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            depositMoneyToUserBankAccount(addedMoney, userBankAccount);
            BankUtil.insertIntoTransactionsTable(new Transaction(
                    userBankAccount.getBankAccountNumber(), addedMoney, TransactionType.DEPOSIT
            ));
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    public static void depositMoneyToUserBankAccount(BigDecimal addedMoney, UserBankAccount userBankAccount)
            throws SQLException {
        try {
            String SQLStatement = """
                                    update user_bank_account set balance = balance + ?
                                    where  bank_account_number = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setBigDecimal(1, addedMoney);
            statement.setLong(2, userBankAccount.getBankAccountNumber());
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}

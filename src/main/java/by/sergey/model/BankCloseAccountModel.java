package by.sergey.model;

import by.sergey.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankCloseAccountModel {
    public static void closeAccount(UserBankAccount userBankAccount) throws SQLException {
        try {
            String SQLStatement = """
                    delete 
                    from user_bank_account
                    where username = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setLong(1, userBankAccount.getBankAccountNumber());
            statement.executeUpdate();
        } catch (BankException e) {
            e.run();
        }
    }
}

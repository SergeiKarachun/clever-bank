package by.sergey.model;

import by.sergey.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankOpenAccountModel {
    public static void saveNewAccount(User user, UserBankAccount newAccount) throws SQLException {
        try {
            String SQLStatement = """
                    insert into user_bank_account (username, bank_account_number, balance, currency_id, bank_id)
                    values (?,?,?,?,?)
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            statement.setLong(2, newAccount.getBankAccountNumber());
            statement.setBigDecimal(3, newAccount.getBalance());
            statement.setInt(4, newAccount.getCurrencyId().ordinal() + 1);
            statement.setInt(5,1);
            statement.execute();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}

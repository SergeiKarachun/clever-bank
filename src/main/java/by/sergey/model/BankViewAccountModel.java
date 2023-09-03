package by.sergey.model;

import by.sergey.controller.BankException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankViewAccountModel {

    public static List<UserBankAccount> collectUserBankAccounts(User user) throws SQLException {
        List<UserBankAccount> bankAccounts = new LinkedList<>();
        try {
            String SQLStatement = """
                    select id, username, bank_account_number, balance, currency_id, bank_id, created_at
                    from user_bank_account
                    where username = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Long bankAccountNumber = resultSet.getLong("bank_account_number");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                Integer currencyId = resultSet.getInt("currency_id");
                Long bankId = resultSet.getLong("bank_id");
                String createdAt = resultSet.getString("created_at");

                UserBankAccount bankAccount = new UserBankAccount(user.getUsername(),
                        bankAccountNumber,
                        balance,
                        getCurrency(currencyId),
                        bankId,
                        createdAt);
                bankAccounts.add(bankAccount);
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return bankAccounts;
    }

    private static Currency getCurrency(Integer currencyId) {
        switch (currencyId) {
            case 1 -> {
                return Currency.USD;
            }
            case 2 -> {
                return Currency.EUR;
            }
            case 3 -> {
                return Currency.BYN;
            }
        }
        return null;
    }
}

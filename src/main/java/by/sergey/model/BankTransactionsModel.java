package by.sergey.model;

import by.sergey.controller.BankException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class BankTransactionsModel {
    public static List<Transaction> collectTransactions(String username, Integer limitNumber,
                                                        Integer offsetNumber) throws SQLException {
        List<Transaction> transactions = new LinkedList<>();
        try {
            String SQLStatement = """
                    select *
                    from transaction
                    join user_bank_account usb on transaction.payment_sender_account = usb.bank_account_number
                    where username = ?
                    limit ? offset ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            statement.setInt(2, limitNumber);
            statement.setInt(3, offsetNumber);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Long id = result.getLong("id");
                LocalDateTime createdAt = result.getTimestamp("created_At").toLocalDateTime();
                TransactionType transactionType = getTransactionType(result.getInt("transaction_type"));
                Long fromBankId = result.getLong("from_bank_id");
                Long toBankId = result.getLong("to_bank_id");
                Long sender = result.getLong("payment_sender_account");
                Long receiver = result.getLong("payment_receiver_account");
                BigDecimal amount = result.getBigDecimal("amount");
                transactions.add(new Transaction(id,
                        createdAt,
                        transactionType,
                        fromBankId,
                        toBankId,
                        sender,
                        receiver,
                        amount
                ));
            }
        } catch (BankException e) {
            e.run();
        }
        return transactions;
    }

    private static TransactionType getTransactionType(Integer typeID) {
        TransactionType[] transactionTypes = TransactionType.values();
        return transactionTypes[typeID - 1];
    }


    public static Integer getNumberOfTransactions(String username) throws SQLException {
        Integer numberOfTransactions = null;
        try {
            String SQLStatement = """
                    select count() as number_of_transactions
                    from transaction
                    join user_bank_account usb on transaction.payment_sender_account = usb.bank_account_number
                    where username = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            numberOfTransactions = resultSet.getInt("number_of_transactions");
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return numberOfTransactions;
    }
}

package by.sergey.model;

import by.sergey.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankRegistrationModel {
    public static void saveNewUser(User user) throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            insertIntoUsersTable(user);
            insertIntoUsersInfoTable(user);
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    private static void insertIntoUsersTable(User user) throws SQLException {
        try {
            String SQLStatement = """
                    insert into users (username, password) values (?,?)
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    private static void insertIntoUsersInfoTable(User user) throws SQLException {
        try {

            String SQLStatement = """
                    insert into users_info (username, first_name, last_name, phone_number, email) 
                    values (?,?,?,?,?)
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    public static boolean checkUniqueUsername(String username) throws SQLException {
        Boolean isUniqueUsername = null;
        try {
            String SQLStatement = """
                    select password
                    from users 
                    where username = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            isUniqueUsername = !statement.executeQuery().next();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
        return isUniqueUsername;
    }

    public static boolean checkUniqueEmail(String email) throws SQLException {
        Boolean isUniqueEmail = null;
        try {
            String SQLStatement = """
                    select  not exists(select email from users_info where email = ?) as is_unique_email
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            isUniqueEmail = resultSet.getBoolean("is_unique_email");
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return isUniqueEmail;
    }
}

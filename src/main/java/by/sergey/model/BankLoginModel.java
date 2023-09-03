package by.sergey.model;

import by.sergey.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankLoginModel {
    public static String getPasswordOf(String username) throws SQLException {
        String password = null;
        try {
            String SQLStatement = """
                                            select password 
                                            from users 
                                            where username = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return password;
    }

    public static User collectUserData(String username, String password) throws SQLException {
        User user = null;
        try {
            String SQLStatement = """
                    select * from users_info 
                    WHERE username = ?
                    """;
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(username,
                        password,
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"));
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return user;
    }
}

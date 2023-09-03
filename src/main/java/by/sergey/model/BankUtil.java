package by.sergey.model;

import by.sergey.controller.BankException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static java.time.chrono.JapaneseEra.values;

public class BankUtil {

    private BankUtil() {}

    public static Scanner scanner;
    public static Connection connection;

    static {
        scanner = new Scanner(System.in);
        try {
            String url = "jdbc:postgresql://localhost:5433/postgres";
            String username = "postgres";
            String password = "postgres";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createMessage(String message) {
        int width = 200;
        int part = (width/2) - (message.length()/2);
        String bothSides = "-".repeat(part);
        String spaceArea = " ".repeat(3);
        System.out.println(bothSides + spaceArea + message + spaceArea + bothSides);
    }

    public static void createHeader(String header) {
        int width = 200;
        int part = ((width / 2) - (header.length() / 2));
        String rightSide = "<".repeat(part);
        String leftSide = ">".repeat(part);
        String spaceArea = " ".repeat(3);
        System.out.println(rightSide + spaceArea + header + spaceArea + leftSide);
    }

    public static void createOrderedList(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            System.out.println((i + 1) + "- " + list[i]);
        }
    }

    public static void showTakeFunctionNumber() {
        System.out.print("Enter function number from the list: ");
    }

    public static boolean checkPassword(String password) {
        if (password.length() > 50) {
            createMessage("Password isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkName(String name) {
        if (name.length() > 50) {
            createMessage("Name isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }
    public static boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() > 18 || !phoneNumber.matches("(\\+?(375|80)?\\s?)?\\(?(17|29|33|44|25)\\)?\\s?(\\d{3})[-|\\s]?(\\d{2})[-|\\s]?(\\d{2})")) {
            BankUtil.createMessage("Phone number isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkEmail(String email) {
        if (email.length() > 255 || !email.matches("^(.+)@(.+)$")) {
            BankUtil.createMessage("Email isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static void insertIntoTransactionsTable(Transaction transaction) throws SQLException {
        try {
            String SQLStatement = """
                    insert into transaction (payment_receiver_account, payment_sender_account, amount, transaction_type) 
                    values (?,?,?,?)
                    """;
            PreparedStatement statement = connection.prepareStatement(SQLStatement);
            statement.setLong(1, transaction.getPaymentReceiverAccount());
            statement.setLong(2, transaction.getPaymentReceiverAccount());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setInt(4, transaction.getTransactionType().ordinal() + 1);
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}

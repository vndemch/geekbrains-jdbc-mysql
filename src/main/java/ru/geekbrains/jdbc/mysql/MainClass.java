package ru.geekbrains.jdbc.mysql;

import java.sql.*;

public class MainClass {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        connect();
        clearTableUsers();
        createTableUsers();
        fillTableUsers();
        showUsers();
        showUsersByAge(25,32);
        deleteUserByName("Piter2");
        showUsers();
        disconnect();
    }

    public static void showUsersByAge(int min, int max) {
        System.out.println("showUsersByAge");
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM `users` WHERE age >= "+min+" AND age <= "+max+";");
            while (rs.next()) {
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+
                        rs.getInt(3)+"   "+rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void showUsers() {
        System.out.println("showUsers");
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
            while (rs.next()) {
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+
                        rs.getInt(3)+"   "+rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void deleteUserByName(String name){
        try {
            stmt.executeUpdate("DELETE FROM `users` WHERE name = '"+name+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void clearTableUsers(){
        try {
            stmt.executeUpdate("DELETE FROM `users`;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createTableUsers(){
        try {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `users` (`id` INT(5) NOT NULL AUTO_INCREMENT, `name` VARCHAR(15), `age` INT(4), `email` VARCHAR(40), PRIMARY KEY (`id`));");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void fillTableUsers(){
        String[] userslist = {"Ivan1 25 ivan125@mail.ru",
                "Ivan2 35 ivan235@mail.ru",
                "Bob1 20 bob120@mail.ru",
                "Piter1 32 piter132@mail.ru",
                "Piter2 18 piter218@mail.ru",
                "Nata1 19 nata119@mail.ru",
        };
        String[] items;
        try {
            connection.setAutoCommit(false);
            for (String str: userslist) {
                items = str.split(" ");
                stmt.executeUpdate("INSERT INTO `users` (`name`, `age`, `email`) VALUES ('"+
                        items[0] + "',"+items[1]+",'"+items[2]+"');");

            }
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void connect() {
        // opening database connection to MySQL server
        try {
            // Строка ?serverTimezone=Europe/Moscow нужна для устранения ошибки TimeZone
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysqltest?serverTimezone=Europe/Moscow",
                    "root", "Vovchik72");
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect to database");
        }
    }

    public static void disconnect() {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}

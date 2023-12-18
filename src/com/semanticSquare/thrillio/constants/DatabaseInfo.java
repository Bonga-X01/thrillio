package com.semanticSquare.thrillio.constants;

public class DatabaseInfo {
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/jid_thrillio";
    private static String username = "root";
    private static String password = "mySQL@2001"; /*or mySQL5957*/

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}

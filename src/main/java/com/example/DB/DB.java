package com.example.DB;

import com.example.customer.Customer;
//import com.example.settings.docType.DocType;
//import com.example.settings.salesOrg.SalesOrg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DB {
    private static final String dbURL = "jdbc:sqlserver://dbsys.cs.vsb.cz\\\\SQLDB:52124;database=FOU0027;encrypt=true;trustServerCertificate=true;";
    private static Connection connection;
    private static final String user = "FOU0027";
    private static final String password = "";



    public static void connectDB(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(dbURL, user, password);
            System.out.println("Connection successfull");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet dbExecuteSelect(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            //connectDB();
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(queryStmt);

        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
        }

        return resultSet;
    }

    public static ResultSet dbExecuteInsert(String queryStmt) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //connectDB();
            ps = connection.prepareStatement(queryStmt, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            resultSet = ps.getGeneratedKeys();

        } catch (SQLException e) {
            System.out.println("Problem occurred at insert operation : " + e);
        }

        return resultSet;
    }

    private static void disconnectDB(){
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}

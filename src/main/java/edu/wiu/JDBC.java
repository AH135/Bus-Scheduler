package edu.wiu;

import java.sql.*;
import java.io.*;
import java.util.*;

public class JDBC {
    static Connection conn;
    static Statement stmt;
    static BufferedReader keyboard;

    public static void main(String args[]) throws IOException {
        String username = "Ora_(usernme)", password = "CS470_";
        String ename;
        int original_empno =0;
        int empno;

        keyboard = new BufferedReader(new InputStreamReader(System.in));

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("Registered the driver...");

            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle2.wiu.edu:1521/orclpdb1",
                    username,password);
            System.out.println("logged into oracle as " +username);

            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery("Select Example" + "stuff");

            while (rset.next()){
                System.out.println(rset.getString(1));
            }
            rset.close();


        } catch(SQLException e){
            System.out.println ("SQL Exception: " + e.getMessage());

        }


    }

}

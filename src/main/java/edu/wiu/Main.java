package edu.wiu;
//import statements for java sql
import java.sql.*;
import java.io.*;
import java.util.*;

public class Main {
    static Connection conn;
    static Statement stmt;
    static BufferedReader keyboard;




    public static void main(String Args[]) throws IOException{
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        String input_username;

        //System.out.println("this is the for the class CS 470");
        System.out.println("please login to your employee account:");
        input_username = keyboard.readLine();
        conn = makeConnection();
        stmt = createStatement(conn);
        Program main_program = new Program(conn, stmt, keyboard);





    }
    //uses a string for username and password, and returns a connection object for
    public static Connection makeConnection(){
        String username = "ORA_ach135", password = "CS470_9404";
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("Registered the driver...");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle2.wiu.edu:1521/orclpdb1",
                    username,password);
            System.out.println("logged into oracle as " +username);


        }catch(SQLException e){
            System.out.println ("SQL Exception: " + e.getMessage());
        }

        return conn;
    }
    public static Statement createStatement(Connection con) {
        Statement return_statement = null;
        try {
            return_statement = con.createStatement();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return return_statement;
    }
}

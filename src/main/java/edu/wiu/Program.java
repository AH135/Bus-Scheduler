package edu.wiu;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Program {

    private Connection conn;
    private Statement stmt;
    private BufferedReader keyboard;

    ArrayList<User> employeeList = new ArrayList<>();
    //program constructor
    public Program(Connection input_conn, Statement input_stat, BufferedReader input_keyboard){
        conn = input_conn;
        stmt = input_stat;
        keyboard = input_keyboard;


    }

    public Connection getConn() {
        return this.conn;
    }
    public Statement getStmt(){
        return this.stmt;
    }
    public BufferedReader getKeyboard(){
        return this.keyboard;
    }

    //input is a statement variable and the string name of the table
    public Integer fetch_table_size(Statement stmt, String table) {
        Integer output = null;
        try {
            ResultSet result = stmt.executeQuery("Select count(*) from " + table);
            result.next();
            output = Integer.parseInt(result.getString(1));

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_table_size");
        }
        return output;

    }





}

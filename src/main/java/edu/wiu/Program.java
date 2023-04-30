package edu.wiu;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Program {

    private Connection conn;
    private Statement stmt;
    private BufferedReader keyboard;
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



}

package edu.wiu;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Program {

    private Connection conn;
    private Statement stmt;
    private BufferedReader keyboard;

    //these arraylists should list ALL the entries in the db
    ArrayList<User> employeeList = new ArrayList<>();
    ArrayList<Route> routeList = new ArrayList<>();
    ArrayList<Bus> busList = new ArrayList<>();

    //this list should be using the previous lists to keep the objects consistent, and comparing to the db to get the right entries
    ArrayList<Run> runList = new ArrayList<>();
    //program constructor
    public Program(Connection input_conn, Statement input_stat, BufferedReader input_keyboard){
        conn = input_conn;
        stmt = input_stat;
        keyboard = input_keyboard;
        employeeList = fetch_employeeList(getStmt());
        routeList = fetch_routelist(getStmt());
        busList = fetch_buslist(getStmt());


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

    public ArrayList<User> getEmployeeList() {
        return employeeList;
    }

    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public ArrayList<Bus> getBusList() {
        return busList;
    }

    public ArrayList<Run> getRunList() {
        return runList;
    }

    //fetch for each list
    private ArrayList<User> fetch_employeeList(Statement stmt){
        ArrayList<User> output = new ArrayList<>();
        for(int i = 1; i <= fetch_table_size(stmt, "Employee"); i++){
            output.add(new User(i,stmt));
        }
        return output;
    }
    private ArrayList<Route> fetch_routelist (Statement stmt){
        ArrayList<Route> output = new ArrayList<>();
        for(int i = 1; i <= fetch_table_size(stmt, "Route"); i++){
            output.add(new Route(i,stmt));
        }
        return output;

    }
    private ArrayList<Bus> fetch_buslist (Statement stmt){
        ArrayList<Bus> output = new ArrayList<>();
        for(int i = 1; i <= fetch_table_size(stmt, "Bus"); i++){
            output.add(new Bus(i,stmt));
        }
        return output;

    }

    /*private fetch_runlist(Statement stmt){

    }*/


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

    @Override
    public String toString() {
        return "Program{" +
                "conn=" + conn +
                ", stmt=" + stmt +
                ", keyboard=" + keyboard +
                ", employeeList=" + employeeList +
                ", routeList=" + routeList +
                ", busList=" + busList +
                ", runList=" + runList +
                '}';
    }
}

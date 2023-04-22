package edu.wiu;

import java.sql.*;
import java.io.*;
import java.util.*;

public class JDBC {
    static Connection conn;
    static Statement stmt;
    static BufferedReader keyboard;

    public static void main(String args[]) throws IOException {
        //this logs into my(alex) account
        String username = "ORA_ach135", password = "CS470_9404";
        String ename;
        int original_empno =0;
        int empno;
        String condition_1;
        String condition_2;
        String condition_3;
        String column_edit;
        String value_input;


        //create arraylist for invalid strings
        ArrayList<String> valid_inputs1 = new ArrayList<String>();
        valid_inputs1.add("view");
        valid_inputs1.add("update");
        valid_inputs1.add("insert");

        //keyboard object for user input
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


            while(true) {

                //-ask user for a new entry or an update to an existing entry
                String input = null;
                while (true) {
                    System.out.println("Welcome. What would you like to do? [view] tables, [update] tables, or [insert] new entries?");

                    input = keyboard.readLine();

                    //check for errors, works with lower case
                    boolean input_check = check_string_errors(input.toLowerCase(Locale.ROOT), valid_inputs1);
                    //System.out.println(input_check);
                    if (input_check == true)
                        break;
                    else
                        System.out.println("Wrong input, please try again");
                }

                //switch branch for different options
                switch (input) {
                    //this needs to show view options and then print them, don't need this for lab4 I think
                    case ("view"):

                        break;
                    //this needs to allow user to update an existing table
                    case("update"):
                        get_table_names();
                        System.out.println("Enter a table to update:");
                        String table_input = keyboard.readLine();

                        //add input checker here
                        System.out.println("conditions: [1], [2], [3]");
                        input = keyboard.readLine();

                        //each allows the amount of arguments for "conditions" on the update operation
                        switch(input){
                            case("1"):
                                getCurrentTable(table_input);
                                System.out.println("please enter a column to edit:)");
                                column_edit = keyboard.readLine();
                                System.out.println("please enter a value to update it with:");
                                value_input = keyboard.readLine();

                                System.out.println("please enter a condition:");
                                condition_1 = keyboard.readLine();
                                break;
                            case("2"):
                                System.out.println("please enter a column to edit:");
                                column_edit = keyboard.readLine();
                                System.out.println("please enter a value to update it with:");
                                value_input = keyboard.readLine();

                                System.out.println("please enter condition 1");
                                condition_1 = keyboard.readLine();
                                System.out.println("please enter condition 2");
                                condition_2 = keyboard.readLine();
                                break;
                            case("3"):
                                System.out.println("please enter a column to edit:");
                                column_edit = keyboard.readLine();
                                System.out.println("please enter a value to update it with:");
                                value_input = keyboard.readLine();

                                System.out.println("please enter condition 1");
                                condition_1 = keyboard.readLine();
                                System.out.println("please enter condition 2");
                                condition_2 = keyboard.readLine();
                                System.out.println("please enter condition 3");
                                condition_3 = keyboard.readLine();
                                break;
                        }


                        /*while (rset.next()) {
                            System.out.println(rset.getString(1));
                        }*/
                        break;
                    //this needs to allow the user to insert into an existing table
                    case("insert"):
                        break;
                }

                //current breakpoint, for debugging
                break;


            }


            //this is the table object created from a select statement
            /*
            ///ResultSet rset = stmt.executeQuery("Select Example" + "stuff");
            ResultSet rset = stmt.executeQuery("Select * from Doctors");

            //example of printed table, columns concatenated together while it loops.
            while (rset.next()){
                System.out.println(rset.getString(1) +" "+  rset.getString(2));
            }

            rset.close();*/


        } catch(SQLException e){
            System.out.println ("SQL Exception: " + e.getMessage());

        }


    }

    //method that takes in a string and an arraylist, returns a fail if the "original"
    //string is not within the arraylist
    public static boolean check_string_errors(String original, ArrayList<String> check){

        for(int i = 0; i < check.size(); i++){
            //System.out.println(check.get(i));
            if(original.equals(check.get(i))){
                //System.out.println("check passes");
                return true;
            }
        }
        //System.out.println("check fail");
        return false;

    }

    //method just prints out the current user's tables that they have created.
    //for the future, an arraylist is uitilized for manipulation if needed.
    public static void get_table_names(){
        ArrayList<String> table_names = new ArrayList<>();

        try {
            ResultSet rset = stmt.executeQuery("Select table_name from user_tables");
            while (rset.next()) {
                System.out.println(rset.getString(1));
                table_names.add(rset.getString(1));
            }
        }catch (SQLException e){

            System.out.println ("SQL Exception: " + e.getMessage());

        }

    }
    //returns the specific table called with the function and its
    public static void getCurrentTable(String a){
        ArrayList<String> table_names = new ArrayList<>();
        int count = 0;

        try {
            //ResultSet column_names = stmt.executeQuery("select column_name from ALL_TAB_COLUMNS where table_name ='"+ a+ "'");
            ResultSet column_count = stmt.executeQuery("select count(*) from user_tables where table_name = '" + a+"'");
            ResultSet rset = stmt.executeQuery("Select * from "+a);
            ResultSetMetaData rsetmd = rset.getMetaData();
            for(int v = 0; v < rsetmd.getColumnCount(); v++){
                System.out.print(rsetmd.getColumnName(v+1)+" | ");
            }
            System.out.println();
            while (rset.next()) {
                for(int i = 0; i < rsetmd.getColumnCount(); i++) {
                    System.out.print(rset.getString(i+1)+" | ");
                    //System.out.println();
                }
                System.out.println();
            }
        }catch (SQLException e){

            System.out.println ("SQL Exception: " + e.getMessage());

        }

    }

}

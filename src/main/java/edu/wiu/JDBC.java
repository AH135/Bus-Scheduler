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
        //String condition_2;
        //String condition_3;
        String column_edit;
        String value_input;
        String table_input;
        ResultSet rset;
        ResultSetMetaData rsetmd;


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
                        System.out.println("select view:");
                        System.out.println("[join tables] [2] [3]");
                        String view_input = keyboard.readLine();
                        switch(view_input){
                            case("join tables"):
                                printTableNames();
                                System.out.println("How many tables to join?");
                                String table_join_count = keyboard.readLine();
                                int table_join_count_int = Integer.parseInt(table_join_count);
                                ArrayList<String> join_string_list = new ArrayList<>();

                                for(int i = 1; i <= table_join_count_int; i++){
                                    System.out.println("table to join "+i);
                                    join_string_list.add(keyboard.readLine());

                                }
                                String join_statement = "Select * from ";
                                for(int i = 0; i < join_string_list.size(); i++){
                                    if(i == join_string_list.size()-1)
                                        join_statement = join_statement + join_string_list.get(i)+"";
                                    else
                                        join_statement = join_statement + join_string_list.get(i)+", ";

                                }
                                System.out.println(join_statement);

                                ResultSet join_rs = stmt.executeQuery(join_statement);

                                print_resultset(join_rs);
                                break;
                            case("2"):
                                break;
                            case("3"):
                                break;
                        }



                        break;
                    //this needs to allow user to update an existing table
                    case("update"):
                        printTableNames();
                        System.out.println("Enter a table to update:");
                        table_input = keyboard.readLine();
                        rset = stmt.executeQuery("Select * from "+table_input);
                        rsetmd = rset.getMetaData();

                        //add input checker here
                        //this should be changed to have the rset and rsetmd as input variables
                        printCurrentTable(table_input);


                        System.out.println("[update table] or [exit]");
                        input = keyboard.readLine();


                        //each allows the amount of arguments for "conditions" on the update operation
                        switch(input){
                            case("update table"):
                                //printCurrentTable(table_input);
                                System.out.println("please enter a column to edit:");
                                column_edit = keyboard.readLine();
                                System.out.println("please enter a value to update it with:");
                                value_input = keyboard.readLine();

                                System.out.println("please enter a row index (ID Number):");
                                condition_1 = keyboard.readLine();

                                //do sql statement here
                                System.out.println("update "+table_input+" set "+column_edit+" = "+value_input+" where "
                                        +rsetmd.getColumnLabel(1)+" = "+condition_1);
                                stmt.executeUpdate("update "+table_input+" set "+column_edit+" = '"+value_input+"' where "
                                        +rsetmd.getColumnLabel(1)+" = "+condition_1);
                                printCurrentTable(table_input);
                                conn.commit();
                                //break;
                            case("exit"):

                                break;
                        }



                        /*while (rset.next()) {
                            System.out.println(rset.getString(1));
                        }*/
                        break;
                    //this needs to allow the user to insert into an existing table
                    case("insert"):
                        printTableNames();
                        System.out.println("Enter a table to update:");
                        table_input = keyboard.readLine();
                        rset = stmt.executeQuery("Select * from "+table_input);
                        rsetmd = rset.getMetaData();
                        ArrayList<String> input_value_list = new ArrayList<>();
                        String insert_statement;

                        //add input checker here
                        //this should be changed to have the rset and rsetmd as input variables
                        printCurrentTable(table_input);


                        System.out.println("[add entry] or [exit]");
                        input = keyboard.readLine();
                        switch(input){
                            case("add entry"):
                                for(int i = 2; i <= rsetmd.getColumnCount(); i++){
                                    System.out.println("enter value for "+rsetmd.getColumnName(i));
                                    input_value_list.add(keyboard.readLine());
                                    //System.out.println(input_value_list.get(i-2));
                                }
                                insert_statement = "insert into "+table_input+" (";
                                for(int i = 2;i<=rsetmd.getColumnCount(); i++){
                                    if(i == rsetmd.getColumnCount())
                                        insert_statement = insert_statement+rsetmd.getColumnName(i);
                                    else
                                        insert_statement = insert_statement+rsetmd.getColumnName(i)+", ";
                                }
                                insert_statement = insert_statement+") values (";
                                for(int i = 0; i < input_value_list.size(); i++){
                                    if(i == input_value_list.size()-1)
                                        insert_statement = insert_statement+"'"+input_value_list.get(i)+"'";
                                    else
                                        insert_statement = insert_statement+"'"+input_value_list.get(i)+"'"+", ";
                                }
                                insert_statement = insert_statement+")";
                                //System.out.println(insert_statement);
                                stmt.executeUpdate(insert_statement);
                                conn.commit();
                                printCurrentTable(table_input);







                                break;
                            case("exit"):
                                break;
                        }

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
    public static void printTableNames(){
        //ArrayList<String> table_names = new ArrayList<>();

        try {
            ResultSet rset = stmt.executeQuery("Select table_name from user_tables");
            while (rset.next()) {
                System.out.println(rset.getString(1));
                //table_names.add(rset.getString(1));
            }
        }catch (SQLException e){

            System.out.println ("SQL Exception: " + e.getMessage());

        }

    }
    //returns the specific table called with the function and its
    public static void printCurrentTable(String a){
        //ArrayList<String> table_names = new ArrayList<>();
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
    public static void print_resultset(ResultSet input){
        try {
            ResultSetMetaData input_md = input.getMetaData();
            for(int i = 1; i <= input_md.getColumnCount(); i++){
                System.out.print(input_md.getColumnName(i)+" | ");
            }
            while (input.next()) {
                for(int i = 0; i < input_md.getColumnCount(); i++) {
                    System.out.print(input.getString(i+1)+" | ");
                    //System.out.println();
                }
                System.out.println();
            }
        }catch (SQLException e) {

            System.out.println("SQL Exception: " + e.getMessage());
        }


    }

}

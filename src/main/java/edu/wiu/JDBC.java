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
                    //this needs to show view options and then print them
                    case ("view"):
                        break;
                    //this needs to allow user to update an existing table
                    case("update"):
                        break;
                    //this needs to allow the user to insert into an existing table
                    case("insert"):
                        break;
                }

                //current breakpoint, for debugging
                break;


            }


            //this is the table object created from a select statement
            //ResultSet rset = stmt.executeQuery("Select Example" + "stuff");
            ResultSet rset = stmt.executeQuery("Select * from Doctors");

            //example of printed table, columns concatenated together while it loops.
            while (rset.next()){
                System.out.println(rset.getString(1) +" "+  rset.getString(2));
            }

            rset.close();


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

}

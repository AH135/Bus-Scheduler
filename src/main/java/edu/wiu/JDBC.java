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




            //code here for program flow
            //condition for loop end
            boolean program_end = false;
            //while program_end is = false, loop will continue
            while(program_end == false) {
                //create arraylist for invalid strings
                ArrayList<String> valid_inputs1 = new ArrayList<String>();
                valid_inputs1.add("view");
                valid_inputs1.add("update");
                valid_inputs1.add("insert");
                Boolean input_check = false;

                //-ask user for a new entry or an update to an existing entry
                String input = null;
                while (input_check == false) {
                    System.out.println("Welcome. What would you like to do? [view] tables, [update] tables, or [insert] new entries?");

                    input = keyboard.readLine();

                    //check for errors
                    input_check = check_string_errors(input, valid_inputs1);
                    //System.out.println(input_check);
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

                //current breakpoint, for debugging currently
                break;


            }

            /*demo for user prompts
            //Scanner scan = new Scanner(System.in);
            String input = keyboard.readLine();
            System.out.println(input);*/

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
        //boolean check_fail = false;
        //System.out.println(original);
        //System.out.println(check.toString());

        for(int i = 0; i < check.size(); i++){
            //System.out.println(check.get(i));
            if(original.equals(check.get(i))){
                System.out.println("check passes");
                return true;
                //break;
            }
        }
        System.out.println("check fail");
        return false;

    }

}

package edu.wiu;
//import statements for java sql
import java.sql.*;
import java.io.*;

public class Main {
    static Connection conn;
    static Statement stmt;
    static BufferedReader keyboard;




    public static void main(String Args[]) throws IOException{
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("this is the for the class CS 470");
        //System.out.println("please login to your employee account:");
        //input_username = keyboard.readLine();

        //objects for passing on to the main program
        conn = makeConnection();
        stmt = createStatement(conn);

        //program object houses functions that bring the rest of the objects together
        System.out.println("Loading, please wait.");
        Program main_program = new Program(conn, stmt, keyboard);

        //loops through the welcome screen until a user is found
        main_program.setCurrent_User(main_program.user_login(main_program.getEmployeeList(), keyboard));
        System.out.println("Welcome "+main_program.getCurrent_User().getUsername()+ ".");
        System.out.println("Logged in as"+ main_program.getCurrent_User().getRankString());

        while(true) {

            switch (main_program.getCurrent_User().getRankString()) {
                case ("Employee"):
                    boolean check_e1 = false;
                    while(check_e1 == false) {
                        System.out.println("Would you like to [view] or [exit]");
                        switch (keyboard.readLine()) {
                            case ("view"):
                                main_program.viewMenu(keyboard);
                                break;
                            case ("exit"):
                                check_e1 = true;
                                break;
                            default:
                                System.out.println("Invalid input, try again.");

                        }
                    }


                    break;

                case ("Manager"):
                    boolean check_m1 = false;
                    while (check_m1 == false) {
                        System.out.println("Would you like to [view], [manage],  or [exit]");
                        switch(keyboard.readLine()){
                            case("view"):
                                main_program.viewMenu(keyboard);
                                break;
                            case("manage"):
                                
                                break;
                            case("exit"):
                                check_m1 = true;
                                break;
                            default:
                                System.out.println("invalid input, try again.");
                        }

                    }

                case ("Admin"):
                    System.out.println("not implemented");
                    break;


            }
            break;
        }



        /* test  zone
        User test2 = new User (4, stmt);
        System.out.println(test2.toString());

        Bus test_bus = new Bus(1, stmt);
        System.out.println(test_bus);

        Stop stop_test = new Stop(1, stmt);
        System.out.println(stop_test);
        System.out.println(main_program.fetch_table_size(stmt,"employee"));

        Route route_test = new Route(1,stmt);
        System.out.println(route_test.toString());

        System.out.println(main_program.toString());
        */



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

package edu.wiu;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Program {

    private Connection conn;
    private Statement stmt;
    private BufferedReader keyboard;

    private User current_User;

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
        runList = fetch_runlist(stmt, routeList, employeeList, busList);




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

    public User getCurrent_User() {
        return current_User;
    }

    public void setCurrent_User(User current_User) {
        this.current_User = current_User;
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

    private ArrayList<Run> fetch_runlist(Statement stmt, ArrayList<Route> Routelist, ArrayList<User> userlist, ArrayList<Bus> buslist){
        ArrayList<Run> output = new ArrayList<>();
        int Run_ID;
        String Start_Time;
        String Finish_Time;
        int Day_ID;
        int Emp_ID;
        int Bus_ID;

        User runemployee = null;
        Bus  runbus = null;


        try{
            ResultSet result = stmt.executeQuery("Select * from run");
            while(result.next()){
                Run_ID = Integer.parseInt(result.getString(1));
                Start_Time = result.getString(2);
                Finish_Time = result.getString(3);
                Day_ID = Integer.parseInt(result.getString(4));

                //this should search the employee list for the given index
                Emp_ID = Integer.parseInt(result.getString(5));
                for(int i = 0; i < userlist.size(); i++){
                    if(Emp_ID == userlist.get(i).getEmp_id()) {
                        runemployee = userlist.get(i);
                    }
                }
                //this should search the bus list for the given index
                Bus_ID = Integer.parseInt(result.getString(6));
                for(int i = 0; i < buslist.size(); i++){
                    if(Bus_ID == buslist.get(i).getBus_ID()) {
                        runbus = buslist.get(i);
                    }
                }
                output.add(new Run(Run_ID, Start_Time,Finish_Time, Day_ID,runemployee, runbus, Routelist));

            }


        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_rank");
        }
       //System.out.println(output);
        return output;



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

    //methods for user Interaction

    //checks for a valid user in the userlist. should function recursively if input is not a match
    public User user_login(ArrayList<User> input_list, BufferedReader keyboard) throws IOException {
        System.out.println("Welcome! please login with a username:");
        String input = keyboard.readLine();
        String find_user = null;
        User output = new User();

        while(find_user == null) {
            for (int i = 0; i < input_list.size(); i++) {
                if (input.equals(input_list.get(i).getUsername())) {
                    find_user = input_list.get(i).getRankString();
                    output = input_list.get(i);
                    break;
                }
            }
            if (find_user != null){
                break;
            }else{
                System.out.println("no known user, try again.");
                input = keyboard.readLine();

            }

        }

        return output;
    }
    //can add a list of stops if we want, would take a while, would need a arraylist of just the stops in program.
    public void viewMenu(BufferedReader keyboard) throws IOException {
        Boolean check_view_done = false;
        while(check_view_done == false) {
            System.out.println("would you like to view: ");
            System.out.println("[run] [bus] [route] [user] | [go back]");
            switch (keyboard.readLine()) {
                case ("run"):
                    System.out.println("Current Runs:");
                    System.out.println(getRunList().toString());
                    break;
                case("bus"):
                    System.out.println("Current Buses:");
                    System.out.println(getBusList().toString());
                    break;
                case("route"):
                    System.out.println("Current Routes:");
                    System.out.println(getRouteList().toString());
                    break;
                case("user"):
                    System.out.println("Current Users:");
                    System.out.println(getEmployeeList().toString());
                    break;
                case("go back"):
                    check_view_done = true;
                    break;
                default:
                    System.out.println("Invalid input, try again.");

            }
        }
    }

    //handles the manage screen, should house all options for that tree.
    public void viewManage(BufferedReader keyboard) throws IOException {
        Boolean check_view_done = false;
        Boolean check_view_done2 = false;
        while(check_view_done == false) {
            System.out.println("would you like to manage: ");
            System.out.println(" [user] | [go back]");
            switch (keyboard.readLine()) {

                case("user"):
                    System.out.println("Current Users:");
                    System.out.println(getEmployeeList().toString());
                    while(check_view_done2 == false){
                        System.out.println("What would you like to do to users?");
                        System.out.println("[add user] [remove user] [edit user] | [go back]");
                        switch(keyboard.readLine()){
                            case("add user"):
                                User new_user = ((User) addObject("User", keyboard));
                                new_user.uploadnewUser(stmt);
                                this.getEmployeeList().add(new_user);
                                break;
                            case("remove user"):
                                User user_to_delete = ((User) this.removeObject("User", keyboard));
                                user_to_delete.deleteUser(stmt);
                                for(int i = 0; i< getEmployeeList().size(); i++){
                                    if(user_to_delete.getEmp_id() == getEmployeeList().get(i).getEmp_id()){
                                        getEmployeeList().remove(i);
                                    }
                                }
                                break;
                            case("edit user"):
                                break;
                            case("go back"):
                                check_view_done2 = true;
                                break;
                            default:
                                System.out.println("Invalid input, try again.");
                        }
                    }
                    break;
                case("go back"):
                    check_view_done = true;
                    break;
                default:
                    System.out.println("Invalid input, try again.");

            }
        }
    }

    //inputs an object, and then outputs the created object, should be scalable to different classes
    public Object addObject(String input,BufferedReader keyboard) throws IOException {
        switch(input) {
            case ("User"):
                String name = null;
                int rank = -1;
                System.out.println("Name of new user?");
                name = keyboard.readLine();
                System.out.println("rank of new user?");
                System.out.println("Employee [0]\n Manager [1]\n Admin [2]");
                Boolean rank_check = false;
                while (rank_check == false) {
                    switch (keyboard.readLine()) {
                        case ("0"):
                            rank = 0;
                            rank_check= true;
                            break;
                        case ("1"):
                            rank = 1;
                            rank_check= true;
                            break;
                        case ("2"):
                            rank = 2;
                            rank_check= true;
                            break;
                        default:
                            System.out.println("Wrong input, try again.");

                    }
                }
                User output_user = new User(name, rank);
                return output_user;

            default:
                System.out.println("invalid object ERROR");
                return null;
        }
    }
    public Object removeObject(String input,BufferedReader keyboard) throws IOException {
        boolean does_it_exist = false;
        switch(input) {
            case ("User"):
                User object_to_delete = null;
                int emp_id = -1;
                System.out.println("ID of user to delete:");
                while(does_it_exist == false) {
                    while (emp_id == -1) {
                        try {
                            emp_id = Integer.parseInt(keyboard.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("not a valid input, please enter a number");
                        }
                        if(emp_id != -1) {
                            for (int i = 0; i < this.getEmployeeList().size(); i++) {
                                if (this.getEmployeeList().get(i).getEmp_id() == emp_id) {
                                    does_it_exist = true;
                                    object_to_delete = this.getEmployeeList().get(i);
                                    break;
                                } else {
                                    System.out.println("not an existing employee, please try again.");
                                    emp_id = -1;
                                }
                            }
                        }
                    }
                }
            return object_to_delete;



            default:
                System.out.println("invalid object ERROR");
                return null;

        }
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

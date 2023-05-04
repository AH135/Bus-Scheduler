package edu.wiu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Route {
    private int route_ID;
    private String route_name;
    private String route_Time;
    ArrayList<Stop> Stop_list = new ArrayList<>();
    private String sql_tablename = "route";

    //constructor needs a row, a statement, and a main program for the functions in that class/.
    public Route(Statement stmt, int route_ID, Program main_program ){
        this.route_ID = route_ID;
        this.route_name = fetch_Route_name(route_ID, stmt);
        this.route_Time = fetch_Route_name(route_ID, stmt);
        this.fetch_Stop_list(route_ID, stmt, main_program);
        this.Stop_list = fetch_Stop_list(route_ID, stmt, main_program);





    }
    //route constructor
    public Route(){}

    public int getRoute_ID() {
        return route_ID;
    }

    public String getRoute_name() {
        return route_name;
    }

    public String getRoute_Time() {
        return route_Time;
    }

    public ArrayList<Stop> getStop_list() {
        return Stop_list;
    }

    public String getSql_tablename() {
        return sql_tablename;
    }

    //sql methods

    private String fetch_Route_name(int rowNumber, Statement stmt) {
        //code here
        String output = null;
        try {
            ResultSet result_User = stmt.executeQuery("Select route_name From Route Where route_ID = " + rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            output = result_User.getString(1);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_Route_name");
        }

        return output;
    }

    private String fetch_Route_Time(int rowNumber, Statement stmt) {
        //code here
        String output = null;
        try {
            ResultSet result = stmt.executeQuery("Select route_time From Route Where route_ID = " + rowNumber);
            result.next();
            //System.out.println(result_User.getString("name"));
            output = result.getString(1);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_Route_Time");
        }

        return output;
    }
    //this should populate an arraylist of stops and return it for the given route
    //omg i got it to work, needed a second statement so it wouldn't break when running the stops
    public ArrayList<Stop> fetch_Stop_list(int rowNumber, Statement stmt, Program M_program) {
        ArrayList<Stop> output = new ArrayList<>();
        Statement stmt2 = Main.createStatement(Main.conn);

        try{

            ResultSet result = stmt.executeQuery("select Stop_ID from route join route_stop_list on " +
                    "route_stop_list.route_id = route.route_id where route.route_id = "+rowNumber);
            while(result.next()){
                //System.out.println(result.getString(1));
                //this should add a stop into the output arraylist
                output.add(new Stop(Integer.parseInt(result.getString(1)),stmt2));
            }
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("Problem in fetch_stop_List");
        }


        return output;
    }


    @Override
    public String toString() {
        //String listString = Stop_list.stream().map(Object::toString).collect(Collectors.joining(", "));
        return "Route{" +
                "route_ID=" + route_ID +
                ", route_name='" + route_name + '\'' +
                ", route_Time='" + route_Time + '\'' +
                ", Stop_list=" + Stop_list +
                ", sql_tablename='" + sql_tablename + '\'' +
                '}';
    }
}


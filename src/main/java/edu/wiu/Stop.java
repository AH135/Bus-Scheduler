package edu.wiu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stop {
    private int stop_id;
    private String street;
    private String stop_location;
    private String pickup_time;

    //stop constructor, needs the index from the database to pull from and a statment variable
    public Stop(int stop_Id, Statement stmt){
        this.stop_id = stop_Id;
        street = fetch_Street(stop_id,stmt);
        stop_location = fetch_Stop_location(stop_id,stmt);
        pickup_time = fetch_pickup_time(stop_id, stmt);
    }

    //empty constructor
    public Stop(){}

    public int getStop_id() {
        return stop_id;
    }

    public String getStreet() {
        return street;
    }

    public String getStop_location() {
        return stop_location;
    }

    public String getPickup_time() {
        return pickup_time;
    }


    //sql methods

    private String fetch_Street(int rowNumber, Statement stmt){
        //code here
        String output = null;
        try {
            ResultSet result_User = stmt.executeQuery("Select street From stops Where stop_id = "+rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            output = result_User.getString("street");
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_street");
        }
        return output;
    }
    private String fetch_Stop_location(int rowNumber, Statement stmt){
        //code here
        String output = null;
        try {
            ResultSet result_User = stmt.executeQuery("Select stop_location From stops Where stop_id = "+rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            output = result_User.getString("stop_location");
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_street");
        }
        return output;
    }
    private String fetch_pickup_time(int rowNumber, Statement stmt){
        //code here
        String output = null;
        try {
            ResultSet result_User = stmt.executeQuery("Select pickup_Time From stops Where stop_id = "+rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            output = result_User.getString("pickup_Time");
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_street");
        }
        return output;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stop_id=" + stop_id +
                ", street='" + street + '\'' +
                ", stop_location='" + stop_location + '\'' +
                ", pickup_time='" + pickup_time + '\'' +
                '}';
    }
}

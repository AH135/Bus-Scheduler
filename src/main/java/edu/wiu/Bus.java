package edu.wiu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bus {
    private String license;
    private int bus_ID;

    //bus constructor requires the index ID to populate
    public Bus(int bus_ID, Statement stmt){
        this.bus_ID = bus_ID;
        this.license = this.fetch_License(bus_ID, stmt);
    }
    //empty constructor for a null bus
    public Bus(){}

    public String getLicense() {
        return license;
    }
    public int getBus_ID(){
        return bus_ID;
    }

    //this needs to contact the DB and return the string from the Bus "Table" for the license of the bus
    private String fetch_License(int rowNumber, Statement stmt) {
        //code here
        String output = null;
        try {
            ResultSet result_User = stmt.executeQuery("Select Bus_License From Bus Where Bus_ID = " + rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            output = result_User.getString("Bus_License");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_license");
        }
        return output;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "license='" + license + '\'' +
                ", bus_ID=" + bus_ID +
                '}';
    }
}

package edu.wiu;

import java.util.ArrayList;

public class Route {
    private int route_ID;
    private String route_name;
    private String route_Time;
    ArrayList<Stop> Stop_list = new ArrayList<>();

    //gotta figure out how to do the stops in the database

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

    //sql methods
    public int fetch_route_ID(){


        return route_ID;
    }

    public String fetch_Route_name() {


        return route_name;
    }

    public String fetch_Route_Time() {


        return route_Time;
    }

    public ArrayList<Stop> fetch_Stop_list() {


        return Stop_list;
    }
}

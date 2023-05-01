package edu.wiu;

public class Stop {
    private int stop_id;
    private String street;
    private String location;
    private String pickup_time;

    //stop constructor
    public Stop(){}

    public int getStop_id() {
        return stop_id;
    }

    public String getStreet() {
        return street;
    }

    public String getLocation() {
        return location;
    }

    public String getPickup_time() {
        return pickup_time;
    }


    //sql methods
    public Integer fetch_stop_id(){

        return stop_id;
    }

    public String fetch_Street(){

        return street;
    }
    public String fetch_location(){

        return location;
    }
    public String fetch_pickup_time(){

        return pickup_time;
    }


}

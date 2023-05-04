package edu.wiu;

import java.util.ArrayList;

public class Run {
    private Integer run_ID;
    private ArrayList<Route> route_list = new ArrayList<>();
    private String start_time;
    private String finish_time;
    private String day;
    private User run_worker;
    private Bus run_bus;

    private String sql_tablename = "run";


    //run constructor
    public Run(){

    }

    public Integer getRun_ID() {
        return run_ID;
    }

    public ArrayList<Route> getRoute_list() {
        return route_list;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getDay() {
        return day;
    }

    public User getRun_worker() {
        return run_worker;
    }

    public Bus getRun_bus() {
        return run_bus;
    }

    public String getSql_tablename() {
        return sql_tablename;
    }

    //sql fetch methods here


}

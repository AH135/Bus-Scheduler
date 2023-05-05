package edu.wiu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Run {
    private Integer run_ID;
    private ArrayList<Route> route_list = new ArrayList<>();
    private String start_time;
    private String finish_time;
    private int day;
    private User run_worker;
    private Bus run_bus;

    private String sql_tablename = "run";


    //run constructor
    public Run(Integer run_ID, String start_time, String finish_time,
               int day, User run_worker,Bus run_bus, ArrayList<Route> Routelist){
        this.run_ID = run_ID;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.day = day;
        this.run_worker = run_worker;
        this.run_bus = run_bus;

        Statement run_stmt = Main.createStatement(Main.conn);
        try {
            ResultSet Run_result = run_stmt.executeQuery("Select route_ID from run join run_route_list " +
                    "on run.run_id = run_route_list.run_id where run.run_Id ="+getRun_ID());
            while(Run_result.next()){
                for(int i = 0; i <= route_list.size();i++){
                    if(Integer.parseInt(Run_result.getString(1)) == Routelist.get(i).getRoute_ID()){
                        route_list.add(Routelist.get(i));
                    }
                }

            }

        }catch (SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in run constructor");

        }


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

    public int getDay() {
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

    @Override
    public String toString() {
        return "\nRun{" +
                "\nrun_ID=" + run_ID +
                "\n route_list=" + route_list +
                "\n start_time='" + start_time + '\'' +
                "\n finish_time='" + finish_time + '\'' +
                "\n day=" + day +
                "\n run_worker=" + run_worker +
                "\n run_bus=" + run_bus +
                "\n sql_tablename='" + sql_tablename + '\'' +
                '}';
    }
    //sql fetch methods here


}

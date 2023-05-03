package edu.wiu;

import java.sql.*;

public class User {
    private int emp_id;
    private String username;
    //private String[] rank_def = {"Employee", "Manager", "Admin"};
    //rank 0 == employee
    //rank 1 == manager
    //rank 2 == admin
    private int rank;
    private String rankString;

    //used to create new user
    public User(String name, int rank){
        if(rank > 2)
            this.rank = 2;
        else if(rank < 0)
            this.rank = 0;
        else
            this.rank = 1;
        this.username = name;

    }
    //constructor populates the variables using an inputed ID
    public User(int emp_id, Statement stmt){
        //need the sql methods
        this.emp_id = emp_id;
        fetch_Name(emp_id,stmt);
        fetch_Rank(emp_id,stmt);
        fetch_Rank_String(emp_id,stmt);


    }

    public User() {

    }

    public int getEmp_id(){
        return emp_id;
    }

    public int getRankInt() {
        return rank;
    }

    //this should be changed to a database method prolly, but this works for now
    public String getRankString(){
        return rankString;
    }

    public String getUsername() {
        return username;
    }

    public int setRank(int rank){
        if(rank > 2)
            this.rank = 2;
        else if(rank < 0)
            this.rank = 0;
        else
            this.rank = 1;
        return this.rank;
    }

    public String setUsername(String name){
        this.username = name;
        return this.username;
    }

    //sql functions

    //need function to upload a user into a new entry in the "employees" table
    public void uploadUser(){


    }

    //need a function that will populate a User object with a given row. this should only handle one user per function call

    //fetches the selected user from the database with the input value and puts it into the username variable
    public String fetch_Name(int rowNumber, Statement stmt){
        //code here
        try {
            ResultSet result_User = stmt.executeQuery("Select NAME From Employee Where Emp_ID = "+rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            this.username = result_User.getString("name");
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_name");
        }

        return username;
    }
    //returns the integer from the database and enters it into the
    public int fetch_Rank(int rowNumber, Statement stmt){
        //code here
        try {
            ResultSet result_User = stmt.executeQuery("Select rank_ID From Employee Where Emp_ID = "+rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("name"));
            this.rank = Integer.parseInt(result_User.getString("rank_ID"));
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_rank");
        }
        return rank;
    }
    //uses the input row index to return the rankstring of the given user
    public String fetch_Rank_String(int rowNumber, Statement stmt){
        //code here
        try {
            ResultSet result_User = stmt.executeQuery("Select emp_id, rankname From Employee inner join rank on " +
                    "employee.rank_ID = rank.rank_id where emp_id = "+rowNumber);
            result_User.next();
            //System.out.println(result_User.getString("rankname"));
            this.rankString = result_User.getString("rankname");
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("problem in fetch_rank_String");
        }
        return rankString;
    }

    @Override
    public String toString() {
        return "User{" +
                "emp_id=" + emp_id +
                ", username='" + username + '\'' +
                ", rank=" + rank +
                ", rankString='" + rankString + '\'' +
                '}';
    }
}

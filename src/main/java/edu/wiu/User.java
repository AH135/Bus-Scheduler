package edu.wiu;

public class User {
    private int emp_id;
    private String username;
    private String[] rank_def = {"Employee", "Manager", "Admin"};
    //rank 0 == employee
    //rank 1 == manager
    //rank 2 == admin
    private int rank;

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
    //use this to fetch user from database
    public User(){
        //need the sql methods



    }
    public int getEmp_id(){
        return emp_id;
    }

    public int getRankInt() {
        return rank;
    }

    //this should be changed to a database method prolly, but this works for now
    public String getRankString(){
        return rank_def[rank];
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
    public User populateUser(int rowNumber){
        //code here



        return this;
    }
}

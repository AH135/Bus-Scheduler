package edu.wiu;

public class Bus {
    private String license;
    private int bus_ID;
    //bus constructor
    public Bus(int bus_ID){
        //this.license = populateLicense(bus_ID)
    }

    public String getLicense() {
        return license;
    }
    public int getBus_ID(){
        return bus_ID;
    }

    //this needs to contact the DB and return the string from the Bus "Table" for the license of the bus
    public String fetch_License(int bus_ID){


        return "not implemented";
    }
    public int fetch_bus_ID(int bus_ID){


        return 0;
    }

}

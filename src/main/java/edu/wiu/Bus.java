package edu.wiu;

public class Bus {
    private String license;
    //bus constructor
    public Bus(int bus_ID){
        //this.license = populateLicense(bus_ID)
    }

    public String getLicense() {
        return license;
    }

    //this needs to contact the DB and return the string from the Bus "Table" for the license of the bus
    public String populateLicense(int bus_ID){


        return "not implemented";
    }
}


package com.mycompany.charts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainClass {

  
    public static void main(String[] args) throws IOException {
        
        TitanicPassengersDao p1 = new  TitanicPassengersDao();
        List<TitanicPassengers> passengersInfo = new ArrayList<TitanicPassengers> ();     
        passengersInfo=p1.getPassengersFromJsonFile();
        
        p1.graphPassengerAges(passengersInfo);
        p1.graphPassengerClass(passengersInfo);
        p1.graphPassengerSurvived(passengersInfo);
        p1.graphPassengerSurvivedGender(passengersInfo);
    }
    
}

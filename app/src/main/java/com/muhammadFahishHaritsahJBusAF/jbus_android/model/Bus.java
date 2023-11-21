package com.muhammadFahishHaritsahJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Serializable {
    public int accountId;
    public String name;
    public List<Facility> facilities;
    public String price;
    public int capacity;
    public BusType busType;
    public String departure;
    public String arrival;
    public List<Schedule> schedules;
    public String departureTime;
    public String arrivalTime;

    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.name = "Buzzes Laju " + i;
            bus.busType = BusType.REGULER;
            bus.departureTime = i + ".30";
            bus.arrivalTime = (i+1) + ".30";
            bus.departure = "Kelapa Gading";
            bus.arrival = "Pasteur";
            bus.price = i + "" + (i+3) + "000";

            busList.add(bus);
        }

        return busList;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

package com.muhammadFahishHaritsahJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Serializable {
    public int accountId;
    public String name;
    public List<Facility> facilities;
    public Price price;
    public int capacity;
    public BusType busType;
    public Station departure;
    public Station arrival;
    public List<Schedule> schedules;

    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.name = "Buzzes Laju " + i;
            bus.busType = BusType.REGULER;
//            bus.departureTime = i + ".30";
//            bus.arrivalTime = (i+1) + ".30";
            bus.departure.stationName = "Kelapa Gading";
            bus.arrival.stationName = "Pasteur";
            bus.price.price = 100 + i;

            busList.add(bus);
        }

        return busList;
    }

    @NonNull
    @Override
    public String toString() {
        String printLine = "Bus Details = " + " | ID : " + this.id + " | Name : " + this.name + " | Facility : " + this.facilities + " | Price : " + price.toString() + " | Capacity : " + String.valueOf(capacity) + " | Bus Type : " + this.busType + " | Departure : " + this.departure + " | Arrival : " + this.arrival + " | ";
        return printLine;
    }
}

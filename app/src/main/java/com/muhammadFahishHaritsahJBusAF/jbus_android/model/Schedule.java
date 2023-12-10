package com.muhammadFahishHaritsahJBusAF.jbus_android.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    //field
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;

    public Schedule(Timestamp departureSchedule, int numberOfSeats){
        this.departureSchedule = departureSchedule;

        initializeSeatAvailability(numberOfSeats);
    }

    private void initializeSeatAvailability(int numberOfSeats){
        seatAvailability = new LinkedHashMap<>();
        for (int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++){
            String sn = seatNumber < 10 ? "0" + seatNumber : "" + seatNumber;
            seatAvailability.put("AF" + sn, true);
        }
    }

    public void printSchedule(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(this.departureSchedule.getTime());

        // Print tanggal keberangkatan
        System.out.println("\nTanggal Keberangkatan: " + formattedDate);

        System.out.println("Daftar kursi dan ketersediaan kursinya: \n");
        int maxSeatsPerRow = 4; // Assuming there are 4 seats per row
        int currentSeat = 1;

        for (String seat : this.seatAvailability.keySet()){
            String symbol = this.seatAvailability.get(seat) ? "0" : "X";
            System.out.print(seat + " : " + symbol + "\t");

            if (currentSeat % maxSeatsPerRow == 0){
                System.out.println();
            }

            currentSeat++;
        }

        System.out.println("\n");
    }

    public boolean isSeatAvailable(String seat){
        if(seatAvailability.containsKey(seat)){
            return seatAvailability.get(seat);
        }
        return false;
    }

    public boolean isSeatAvailable(List<String> seat){
        for(String i : seat){
            if(seatAvailability.containsKey(i)){
                return seatAvailability.get(i);
            }
        }
        return false;
    }

    public void bookSeat(String seat){
        seatAvailability.put(seat, false);
    }

    public void bookSeat(List<String> seat){
        for(String i : seat){
            seatAvailability.put(i, false);
        }
    }


}

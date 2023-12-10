package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Facility;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

public class BusDetailMain extends AppCompatActivity {

    private TextView busName, busType, seats, departureCity, departureStation, arrivalCity, arrivalStation;
    private ListView scheduleListView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail_main);

        scheduleListView = findViewById(R.id.main_schedule_view);

        //API Service
        mContext = this;

        handleListSchedule();

        busName = findViewById(R.id.detail_bus_name);
        busType = findViewById(R.id.detail_bus_type);
        seats = findViewById(R.id.number_of_seat);
        departureCity = findViewById(R.id.departure_city);
        departureStation = findViewById(R.id.departure_station);
        arrivalCity = findViewById(R.id.arrival_city);
        arrivalStation = findViewById(R.id.arrival_station);

        Bus thisBus = MainActivity.mainClickedBus;
        busName.setText(thisBus.name);
        busType.setText(thisBus.busType.toString());
        seats.setText(thisBus.capacity + " seats");
        departureCity.setText(thisBus.departure.city.toString());
        departureStation.setText(thisBus.departure.stationName);
        arrivalCity.setText(thisBus.arrival.city.toString());
        arrivalStation.setText(thisBus.arrival.stationName);


    }

    public void handleListSchedule(){
        MainDetailScheduleAdapter arrayAdapter = new MainDetailScheduleAdapter(mContext, MainActivity.mainClickedBus.schedules);
        scheduleListView.setAdapter(arrayAdapter);
    }
    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Facility;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Schedule;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusDetailActivity extends AppCompatActivity {
    private TextView busName, busType, seats, departureCity, departureStation, arrivalCity, arrivalStation, addScheduleView;
    private ListView scheduleListView;
    private Context mContext;
    private BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        scheduleListView = findViewById(R.id.schedule_view);

        //API Service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        handleListSchedule();

        busName = findViewById(R.id.detail_bus_name);
        busType = findViewById(R.id.detail_bus_type);
        seats = findViewById(R.id.number_of_seat);
        departureCity = findViewById(R.id.departure_city);
        departureStation = findViewById(R.id.departure_station);
        arrivalCity = findViewById(R.id.arrival_city);
        arrivalStation = findViewById(R.id.arrival_station);
        addScheduleView = findViewById(R.id.add_schedule_view);

        Bus thisBus = ManageBusActivity.clickedBus;
        busName.setText(thisBus.name);
        busType.setText(thisBus.busType.toString());
        seats.setText(thisBus.capacity + " seats");
        departureCity.setText(thisBus.departure.city.toString());
        departureStation.setText(thisBus.departure.stationName);
        arrivalCity.setText(thisBus.arrival.city.toString());
        arrivalStation.setText(thisBus.arrival.stationName);

        AddScheduleActivity.busId = ManageBusActivity.clickedBus.id;
        addScheduleView.setOnClickListener(v -> {
            moveActivity(this, AddScheduleActivity.class);
        });
    }

    public void handleListSchedule(){
        DetailScheduleAdapter arrayAdapter = new DetailScheduleAdapter(mContext, ManageBusActivity.clickedBus.schedules);
        scheduleListView.setAdapter(arrayAdapter);
    }
    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
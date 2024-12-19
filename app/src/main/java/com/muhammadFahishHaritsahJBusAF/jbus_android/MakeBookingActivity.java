package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Invoice;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Payment;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Schedule;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeBookingActivity extends AppCompatActivity implements SelectSeatDialog.SelectedSeatDialogListener {

    private Context mContext;
    private BaseApiService mApiService;
    public static Schedule thisSchedule;
    private TextView dateView, timeView;
    private TextView departureCity, departureStation, arrivalCity, arrivalStation, busName, busType;
    private TextView buyerName, buyerEmail;
    private TextView triggerSeatSelect, selectedSeat;
    private TextView price;
    private Button makeBookingButton;

    // static var for dialog
    public static int maxSeats;
    public static List<String> selectedSeatsDialog;
    Timestamp timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        //API Service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        dateView = findViewById(R.id.date_schedule);
        timeView = findViewById(R.id.time_schedule);
        departureCity = findViewById(R.id.departure_city_book);
        departureStation = findViewById(R.id.departure_station_book);
        arrivalCity = findViewById(R.id.arrival_city_book);
        arrivalStation = findViewById(R.id.arrival_station_book);
        busName = findViewById(R.id.bus_name_book);
        busType = findViewById(R.id.bus_type_book);
        buyerName = findViewById(R.id.buyer_name);
        buyerEmail = findViewById(R.id.buyer_email);
        triggerSeatSelect = findViewById(R.id.trigger_seat_select);
        selectedSeat = findViewById(R.id.selected_seat);
        makeBookingButton = findViewById(R.id.booking_button);

        price = findViewById(R.id.booking_price);

        // SET VIEWS
        Bus thisBus = MainActivity.mainClickedBus;
        // date and time
        Date date = new Date(thisSchedule.departureSchedule.getTime());
        SimpleDateFormat formatter1 = new SimpleDateFormat("MMM dd, yyyy");
        dateView.setText(formatter1.format(date));
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        timeView.setText(formatter2.format(date));
        // bus information
        departureCity.setText(thisBus.departure.city.toString());
        departureStation.setText(thisBus.departure.stationName.toString());
        arrivalCity.setText(thisBus.arrival.city.toString());
        arrivalStation.setText(thisBus.arrival.stationName.toString());
        busName.setText(thisBus.name);
        busType.setText(thisBus.busType.toString());
        // buyer information
        if (MainActivity.mainLoggedAccount != null) {
            buyerName.setText(MainActivity.mainLoggedAccount.name);
            buyerEmail.setText(MainActivity.mainLoggedAccount.email);
        }
        // select seat dialog
        triggerSeatSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialog();
            }
        });
        // price view
        int priceInt = (int) thisBus.price.price;
        price.setText(String.valueOf(priceInt));

        // static var handling
        maxSeats = MainActivity.mainClickedBus.capacity;
//        for (Boolean b : thisSchedule.seatAvailability.values()) {
//            for (String s : thisSchedule.seatAvailability.keySet())
//                if (b == false) {
//                    selectedSeatsDialog.add(s);
//                }
//        }
//        System.out.println(selectedSeatsDialog);

        // make booking trigger
        makeBookingButton.setOnClickListener(e -> {
            handleBooking();
        });
    }

    public void handleBooking(){
        int buyerId = LoginActivity.loggedAccount.id;
        int renterId = MainActivity.mainLoggedAccount.company.id;
        int busId = MainActivity.mainClickedBus.id;
        // bus seats list
        String seats = selectedSeat.getText().toString();
        String[] seatsSplit = seats.split(",");
        List<String> busSeats = new ArrayList<>();
        for (String s : seatsSplit) {
            busSeats.add(s);
        }
        System.out.println(busSeats);
        String schedule = thisSchedule.departureSchedule.toString();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(schedule);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }

        mApiService.makeBooking(buyerId, renterId, busId, busSeats, timestamp).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Payment> res = response.body();

                if (res.success) {
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_LONG).show();
                System.out.println(call);
                System.out.println(t);
            }
        });
    }

    public void onDialog(){
        SelectSeatDialog dialog = new SelectSeatDialog();
        dialog.show(getSupportFragmentManager(), "select dialog");
    }
    @Override
    public void applyTexts(String seats) {
        selectedSeat.setText(seats);
    }
}
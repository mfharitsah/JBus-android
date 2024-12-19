package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Account;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Payment;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Schedule;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmWaitingPaymentActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    private TextView dateSchedule, timeSchedule, departureCity, departureStation, arrivalCity, arrivalStation, busName, busType;
    private TextView buyerName, buyerEmail;
    private TextView numberOfTicket, ticketPrice, buyerBalance;
    private Button cancelButton, acceptButton;

    public static Bus waitingBus;
    public static Payment waitingPayment;

    //handle balance
    private double totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_waiting_payment);

        //API Service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        dateSchedule = findViewById(R.id.date_schedule);
        timeSchedule = findViewById(R.id.time_schedule);
        departureCity = findViewById(R.id.departure_city_book);
        departureStation = findViewById(R.id.departure_station_book);
        arrivalCity = findViewById(R.id.arrival_city_book);
        arrivalStation = findViewById(R.id.arrival_station_book);
        busName = findViewById(R.id.bus_name_book);
        busType = findViewById(R.id.bus_type_book);
        buyerName = findViewById(R.id.buyer_name);
        buyerEmail = findViewById(R.id.buyer_email);
        numberOfTicket = findViewById(R.id.number_of_tickets);
        ticketPrice = findViewById(R.id.booking_price);
        buyerBalance = findViewById(R.id.account_balance);
        cancelButton = findViewById(R.id.cancel_button);
        acceptButton= findViewById(R.id.accept_button);

        // set view
        Date date = new Date(waitingPayment.departureDate.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateFix = formatter.format(date);
        Date time = new Date(waitingPayment.departureDate.getTime());
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        String timeFix = formatter2.format(time);
        dateSchedule.setText(dateFix);
        timeSchedule.setText(timeFix);
        // bus information
        departureCity.setText(waitingBus.departure.city.toString());
        departureStation.setText(waitingBus.departure.stationName);
        arrivalCity.setText(waitingBus.arrival.city.toString());
        arrivalStation.setText(waitingBus.arrival.stationName);
        busName.setText(waitingBus.name);
        busType.setText(waitingBus.busType.toString());
        // buyer information
        buyerName.setText(LoginActivity.loggedAccount.name);
        buyerEmail.setText(LoginActivity.loggedAccount.email);
        int balance = (int) LoginActivity.loggedAccount.balance;
        buyerBalance.setText("IDR " + String.valueOf(balance));
        // ticket price
        int ticketNum = 0;
        for (String s : waitingPayment.busSeat) {
            ticketNum++;
        }
        numberOfTicket.setText(ticketNum + " ticket");
        double viewTotalPrice = waitingBus.price.price * ticketNum;
        int price = (int) viewTotalPrice;
        ticketPrice.setText(String.valueOf(price));

        acceptButton.setOnClickListener(e -> {
            int ticketNumHandle = 0;
            for (String s : waitingPayment.busSeat) {
                ticketNumHandle++;
            }
            totalPrice = waitingBus.price.price * ticketNumHandle;
            handleAccept();
        });
        cancelButton.setOnClickListener(e -> {
            handleCancel();
        });

    }

    protected void handleTopDown(){

        mApiService.topDown(LoginActivity.loggedAccount.id, totalPrice).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Double> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success) {
                    LoginActivity.loggedAccount.balance -= totalPrice;
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "There is a problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleAccept() {

        if ((LoginActivity.loggedAccount.balance < totalPrice)) {
            Toast.makeText(mContext, "Insufficient funds- check your available balance.", Toast.LENGTH_LONG).show();
            return;
        }

        mApiService.accept(waitingPayment.id).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application Error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                handleTopDown();

                BaseResponse<Payment> res = response.body();
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                moveActivity(mContext, PaymentActivity.class);
                finish();

            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                System.out.println(call.toString());
                System.out.println(t.toString());
            }
        });

    }

    public void handleCancel(){
        mApiService.cancel(waitingPayment.id).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application Error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Payment> res = response.body();
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                moveActivity(mContext, PaymentActivity.class);
                finish();

            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
                System.out.println(call.toString());
                System.out.println(t.toString());
            }
        });
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
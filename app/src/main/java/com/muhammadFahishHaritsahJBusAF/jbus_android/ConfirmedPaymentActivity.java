package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Invoice;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Payment;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmedPaymentActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    private List<Payment> paymentList;
    private ListView confirmedPaymentView;
    private TextView waitingView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_payment);
        confirmedPaymentView = findViewById(R.id.confirmed_listview);

        //API Service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        handlePaymentList();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_payment);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                moveActivity(this, MainActivity.class);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                moveActivity(this, AboutMeActivity.class);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_payment) {
                return true;
            }
            return false;
        });

        waitingView = findViewById(R.id.waiting_view);
        waitingView.setOnClickListener(v -> {
            moveActivity(this, PaymentActivity.class);
        });
    }

    public void handlePaymentList(){
        mApiService.getAllPayments(LoginActivity.loggedAccount.id).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application Error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                paymentList = response.body();
                List<Payment> confirmedPayment = new ArrayList<>();
                for (Payment payment : paymentList) {
                    if (payment.status.equals(Invoice.PaymentStatus.SUCCESS) || payment.status.equals(Invoice.PaymentStatus.FAILED)) {
                        confirmedPayment.add(payment);
                    }
                }
                ConfirmedBookedTicketAdapter arrayAdapter = new ConfirmedBookedTicketAdapter(mContext, confirmedPayment);
                confirmedPaymentView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Server Error : " + t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
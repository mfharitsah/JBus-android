package com.muhammadFahishHaritsahJBusAF.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    private TextView initial, nameTop, emailTop, nameData, emailData, balanceData;
    private String name = "Muhammad Fahish Haritsah";
    private String email = "harristfhs@gmail.com";
    private String balance = "150000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        initial = findViewById(R.id.initial);
        nameTop = findViewById(R.id.name_top);
        emailTop = findViewById(R.id.email_top);
        nameData = findViewById(R.id.name_data);
        emailData = findViewById(R.id.email_data);
        balanceData = findViewById(R.id.balance);

        initial.setText(String.valueOf(name.charAt(0)));
        nameTop.setText(name);
        emailTop.setText(email);
        nameData.setText(name);
        emailData.setText(email);
        balanceData.setText("IDR" + balance);
    }
}
package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Account;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {

    private TextView initial, nameTop, emailTop, nameData, emailData, balanceData;
    private EditText inputAmount;
    private Button topUpButton;
    private String username;
    private String email;
    private String balance;
    private Context mContext;
    private BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        initial = findViewById(R.id.initial);
        nameTop = findViewById(R.id.name_top);
        emailTop = findViewById(R.id.email_top);
        nameData = findViewById(R.id.name_data);
        emailData = findViewById(R.id.email_data);
        balanceData = findViewById(R.id.balance);

        inputAmount = findViewById(R.id.topup_amount);
        topUpButton = findViewById(R.id.topup_button);


        if (LoginActivity.loggedAccount != null) {
            initial.setText(LoginActivity.loggedAccount.name.substring(0, 1).toUpperCase());
            nameTop.setText(LoginActivity.loggedAccount.name.toUpperCase());
            emailTop.setText(LoginActivity.loggedAccount.email);
            nameData.setText(LoginActivity.loggedAccount.name);
            emailData.setText(LoginActivity.loggedAccount.email);
            balanceData.setText("IDR " + String.valueOf(LoginActivity.loggedAccount.balance));
        }

        topUpButton.setOnClickListener(e -> {
            handleTopUp();
        });
    }

    protected void handleTopUp(){
        double topUpAmount = Double.parseDouble(inputAmount.getText().toString());
        Account account = LoginActivity.loggedAccount;
        if (topUpAmount == 0.0d) {
            Toast.makeText(mContext, "Input the amount!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.topUp(account.id, topUpAmount).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Double> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success) {
                    account.balance += topUpAmount;
                    balanceData.setText("IDR " + String.valueOf(account.balance));
                    Toast.makeText(mContext, "Successfully Top Up Balance", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "There is a problem with the server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
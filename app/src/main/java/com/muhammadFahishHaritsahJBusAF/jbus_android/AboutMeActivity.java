package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Account;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {

    private TextView initial, nameTop, emailTop, nameData, emailData, balanceData;
    private TextView registerRenter;
    private EditText inputAmount;
    private Button topUpButton;
    private Context mContext;
    private BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        //API service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        //User data
        initial = findViewById(R.id.initial);
        nameTop = findViewById(R.id.name_top);
        emailTop = findViewById(R.id.email_top);
        nameData = findViewById(R.id.name_data);
        emailData = findViewById(R.id.email_data);
        balanceData = findViewById(R.id.balance);

        //Top Up service
        inputAmount = findViewById(R.id.topup_amount);
        topUpButton = findViewById(R.id.topup_button);

        //Register Renter
        registerRenter = findViewById(R.id.button_manage);


        if (LoginActivity.loggedAccount != null) {
            initial.setText(LoginActivity.loggedAccount.name.substring(0, 1).toUpperCase());
            nameTop.setText(LoginActivity.loggedAccount.name.toUpperCase());
            emailTop.setText(LoginActivity.loggedAccount.email);
            nameData.setText(LoginActivity.loggedAccount.name);
            emailData.setText(LoginActivity.loggedAccount.email);
            balanceData.setText("IDR " + String.valueOf(LoginActivity.loggedAccount.balance));
        }

        //Check renter status
        if (LoginActivity.loggedAccount.company == null) {
            // Not a renter - Show a message prompting to register as a renter
            TextView textView = findViewById(R.id.status_text);
            textView.setText("Interest develop your own buses?");

            // Implement a listener for a component to navigate to the registration page
            Button buttonStatus = findViewById(R.id.button_manage);
            buttonStatus.setText("Register your company!");

            registerRenter.setOnClickListener(e -> {
                Intent intent = new Intent(this, RegisterRenterActivity.class);
                startActivity(intent);
            });
        } else {
            TextView textView = findViewById(R.id.status_text);
            textView.setText("Manage your buses now?");

            // Implement a listener for a component to navigate to the registration page
            Button buttonStatus = findViewById(R.id.button_manage);
            buttonStatus.setText("Manage now");

            registerRenter.setOnClickListener(e -> {
                Intent intent = new Intent(this, ManageBusActivity.class);
                startActivity(intent);
            });
        }

        topUpButton.setOnClickListener(e -> {
            handleTopUp();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                moveActivity(this, MainActivity.class);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                return true;
            } else if (item.getItemId() == R.id.nav_payment) {
                moveActivity(this, PaymentActivity.class);
                finish();
                return true;
            }
            return false;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        if (item.getItemId() == R.id.log_out) {
            LoginActivity.loggedAccount = null;
            moveActivity(this, LoginActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }



    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

}
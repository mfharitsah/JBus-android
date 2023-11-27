package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Account;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Renter;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {
    private EditText companyName, phoneNumber, address;
    private Button registerButton;
    private BaseApiService mApiService;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        //API service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        //Company information
        companyName = findViewById(R.id.company_name);
        phoneNumber = findViewById(R.id.company_phone);
        address = findViewById(R.id.company_address);
        registerButton = findViewById(R.id.register_button);

        //handling register button
        registerButton.setOnClickListener(e -> {
            handleRegisterRenter();
        });

    }

    protected void handleRegisterRenter() {
        // handling empty field
        Account account = LoginActivity.loggedAccount;
        String companyNameS = companyName.getText().toString();
        String phoneNumberS = phoneNumber.getText().toString();
        String addressS = address.getText().toString();

        if (companyNameS.isEmpty() || phoneNumberS.isEmpty() || addressS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.registerRenter(account.id, companyNameS, phoneNumberS, addressS).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Renter> res = response.body();

                if (res.success) {
                    moveActivity(mContext, MainActivity.class);
                    finish();
                }

                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, "There is a problem with the server", Toast.LENGTH_SHORT).show();
                System.out.println(call);
                System.out.println(t);
            }
        });
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
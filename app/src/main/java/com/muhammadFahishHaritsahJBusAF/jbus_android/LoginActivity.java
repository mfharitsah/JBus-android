package com.muhammadFahishHaritsahJBusAF.jbus_android;
import com.muhammadFahishHaritsahJBusAF.jbus_android.RegisterActivity;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Account;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private TextView registerNow = null;
    private Button loginButton = null;
    private EditText loginEmail, loginPassword;
    public static Account loggedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);


        registerNow.setOnClickListener(e -> {
            moveActivity(this, RegisterActivity.class);
        });

        loginButton.setOnClickListener(e -> {
           handleLogin();
        });

        getSupportActionBar().hide();
    }

    protected void handleLogin() {
        // handling empty field
        String emailS = loginEmail.getText().toString();
        String passwordS = loginPassword.getText().toString();
        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }

//        for (Account acc : Account.accounts) {
//            if (acc.email.equals(emailS) && acc.password.equals(passwordS)) {
//                loggedAccount = acc;
//                moveActivity(this, MainActivity.class);
//            }
//        }

        mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Account> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success) {
                    loggedAccount = res.payload;
                    MainActivity.mainLoggedAccount = loggedAccount;
                    moveActivity(mContext, MainActivity.class);
                    Toast.makeText(mContext, "Berhasil login", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "There is a problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    private void viewToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
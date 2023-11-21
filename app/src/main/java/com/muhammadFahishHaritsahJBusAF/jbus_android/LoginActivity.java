package com.muhammadFahishHaritsahJBusAF.jbus_android;
import com.muhammadFahishHaritsahJBusAF.jbus_android.RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView registerNow = null;
    private Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);

        registerNow.setOnClickListener(e -> {
            moveActivity(this, RegisterActivity.class);
        });

        loginButton.setOnClickListener(e -> {
            moveActivity(this, MainActivity.class);
        });

        getSupportActionBar().hide();
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    private void viewToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
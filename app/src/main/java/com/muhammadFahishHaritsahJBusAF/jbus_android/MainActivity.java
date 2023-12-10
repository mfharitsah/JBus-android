
package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Account;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Account mainLoggedAccount;
    private Context mContext;
    private BaseApiService mApiService;
    private ListView busListView;
    public static List<Bus> busList = new ArrayList<>();
    private int listSize;

    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 4; // kalian dapat bereksperimen dengan field ini
    private int noOfPages;
    private Button prevButton = null;
    private Button nextButton = null;
    private HorizontalScrollView pageScroll = null;

    //Clicked bus
    public static Bus mainClickedBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busListView = findViewById(R.id.listView);

        //API Service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        handleAllBus();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                moveActivity(this, AboutMeActivity.class);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_payment) {
                moveActivity(this, PaymentActivity.class);
                finish();
                return true;
            }
            return false;
        });

    }

    public void handleAllBus(){
        mApiService.getAllBus().enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                }

                busList = response.body();
                listSize = busList.size();

                BusArrayAdapter arrayAdapter = new BusArrayAdapter(mContext, busList);
                busListView.setAdapter(arrayAdapter);

            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
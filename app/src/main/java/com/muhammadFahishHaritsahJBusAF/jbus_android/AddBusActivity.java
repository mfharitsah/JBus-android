package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BusType;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Facility;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Station;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {

    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner, departureSpinner, arrivalSpinner;
    private List<Station> stationList = new ArrayList<>();
    private Station departureStation, arrivalStation;
    private int selectedDeptStationID, selectedArrStationID;
    private Context mContext;
    private BaseApiService mApiService;
    private List<Facility> selectedFacilities = new ArrayList<>();

    private EditText busName, busCapacity, busPrice;
    private CheckBox ac, wifi, toilet, lcd;
    private CheckBox coolbox, lunch, baggage, electric;
    private Button addBusButton = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //API Service
        mContext = this;
        mApiService = UtilsApi.getApiService();

        busName = findViewById(R.id.bus_name);
        busCapacity = findViewById(R.id.bus_capacity);
        busPrice = findViewById(R.id.bus_price);
        departureSpinner = findViewById(R.id.departure_dropdown);
        arrivalSpinner = findViewById(R.id.arrival_dropdown);
        addBusButton = findViewById(R.id.add_bus_button);

        ac = findViewById(R.id.ac_checkbox);
        wifi = findViewById(R.id.wifi_checkbox);
        toilet = findViewById(R.id.toilet_checkbox);
        lcd = findViewById(R.id.tv_checkbox);
        coolbox = findViewById(R.id.coolbox_checkbox);
        lunch = findViewById(R.id.lunch_checkbox);
        baggage = findViewById(R.id.baggage_checkbox);
        electric = findViewById(R.id.socket_checkbox);

        handleBusType();
        handleStations();

        addBusButton.setOnClickListener(view ->{
            handleFacilites();
            handleCreateBus();
        });

    }

    public void handleFacilites(){
        selectedFacilities.clear();
        if(ac.isChecked()){
            selectedFacilities.add(Facility.AC);
        }
        if(wifi.isChecked()){
            selectedFacilities.add(Facility.WIFI);
        }
        if(toilet.isChecked()){
            selectedFacilities.add(Facility.TOILET);
        }
        if(lcd.isChecked()){
            selectedFacilities.add(Facility.LCD_TV);
        }
        if(coolbox.isChecked()){
            selectedFacilities.add(Facility.COOL_BOX);
        }
        if(lunch.isChecked()){
            selectedFacilities.add(Facility.LUNCH);
        }
        if(baggage.isChecked()){
            selectedFacilities.add(Facility.LARGE_BAGGAGE);
        }
        if(electric.isChecked()){
            selectedFacilities.add(Facility.ELECTRIC_SOCKET);
        }

    }

    public void handleStations(){
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                stationList = response.body();
                ArrayAdapter<Station> stations = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
                stations.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                departureSpinner.setAdapter(stations);
                arrivalSpinner.setAdapter(stations);
                AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                        departureStation = stationList.get(position);
                        selectedDeptStationID = departureStation.id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                };
                departureSpinner.setOnItemSelectedListener(deptOISL);

                AdapterView.OnItemSelectedListener arrOISL = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                        arrivalStation = stationList.get(position);
                        selectedArrStationID = arrivalStation.id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                };
                arrivalSpinner.setOnItemSelectedListener(arrOISL);
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void handleBusType(){
        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);

        AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                selectedBusType = busType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
    public void handleCreateBus(){
        String nameS = busName.getText().toString();
        int capacity = Integer.parseInt(busCapacity.getText().toString());
        int price = Integer.parseInt(busPrice.getText().toString());


        mApiService.create(LoginActivity.loggedAccount.id, nameS, capacity, selectedFacilities, BusType.REGULER, price, selectedDeptStationID, selectedArrStationID )
                .enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "ERROR" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();
                if(res.success){
                    viewToast(mContext, "Success Add Bus");
                    moveActivity(mContext, ManageBusActivity.class);
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Server ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
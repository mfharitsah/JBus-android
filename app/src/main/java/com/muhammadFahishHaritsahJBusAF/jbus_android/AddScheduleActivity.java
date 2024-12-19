package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.BaseResponse;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private BaseApiService mApiService;
    private List<Bus> busList;
    private Context mContext;
    private TextView calendarPicker, timePicker;
    private TextView calendarInput, timeInput, idInput;
    private Button addSchedule;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateAPI, timeAPI, dateAndTime;
    public static int busId = 0;
    private boolean detailCheck = false;
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "Augu] . st", "September", "October", "November", "December"};
    Timestamp timestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        calendarPicker = findViewById(R.id.calendar_picker);
        timePicker = findViewById(R.id.time_picker);
        calendarInput = findViewById(R.id.calendar_input);
        timeInput = findViewById(R.id.time_input);
        idInput = findViewById(R.id.id_input);
        addSchedule = findViewById(R.id.add_schedule_btn);

        calendarPicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);

        handleAllBus();

        if (!(busId == 0)) {
            detailCheck = true;
            idInput.setText(String.valueOf(busId));
        }

        addSchedule.setOnClickListener(e -> {
            handleAddSchedule();
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
                addTable(busList);
                System.out.println(busList);

            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addTable(List<Bus> busList){
        TableLayout mainTable = (TableLayout) findViewById(R.id.main_table);

        for (Bus bus : busList) {
            if (bus.accountId == LoginActivity.loggedAccount.id){
                TableRow rowTable = new TableRow(this);
                TextView id = new TextView(this);
                TextView busName = new TextView(this);
                TextView deptArr = new TextView(this);

                id.setText(String.valueOf(bus.id));
                id.setTextColor(Color.WHITE);
                id.setTextSize(18);
                rowTable.addView(id);

                busName.setText(bus.name);
                busName.setTextColor(Color.WHITE);
                busName.setTextSize(18);
                rowTable.addView(busName);

                deptArr.setText(bus.departure.city.toString() + " - " + bus.arrival.city.toString());
                deptArr.setTextColor(Color.WHITE);
                deptArr.setTextSize(18);
                rowTable.addView(deptArr);

                mainTable.addView(rowTable);
            }


        }
    }

    public void handleAddSchedule() {
        busId = Integer.parseInt(idInput.getText().toString());
        dateAndTime = dateAPI + " " + timeAPI;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(dateAndTime);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        if (dateAndTime.isEmpty() || idInput.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.addSchedule(busId, timestamp).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Bus> res = response.body();

                // if success finish this activity (back to login activity)
                if (res.success) {
                    Toast.makeText(mContext, "Success adding schedule for bus id : " + busId, Toast.LENGTH_SHORT).show();

                    moveActivity(mContext, ManageBusActivity.class);
                    finish();
                }

                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v == calendarPicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendarInput.setText(dayOfMonth + ", " + months[month] + " " + year);
                            dateAPI = year + "-" + (month+1) + "-" + dayOfMonth;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == timePicker) {

            // Get Current Time
            final Calendar t = Calendar.getInstance();
            mHour = t.get(Calendar.HOUR_OF_DAY);
            mMinute = t.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timeInput.setText(hourOfDay + ":" + minute);
                            timeAPI = timeInput.getText().toString() + ":00";
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    private void moveActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
package com.muhammadFahishHaritsahJBusAF.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainDetailScheduleAdapter extends ArrayAdapter<Schedule> {
    public MainDetailScheduleAdapter(Context ctx, List<Schedule> scheduleList) {
        super(ctx, R.layout.main_schedules, scheduleList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // convertView which is recyclable view
//        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_schedules, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Schedule currentNumberPosition = getItem(position);

        TextView departureDate = convertView.findViewById(R.id.main_departure_date);
        TextView numberOfSeat = convertView.findViewById(R.id.main_number_of_seat);

        //date
        Date date = new Date(currentNumberPosition.departureSchedule.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy, HH:mm");
        String dateFix = formatter.format(date);

        //seat availability
        int i = 0;
        for (Boolean seatAvailability : currentNumberPosition.seatAvailability.values()) {
            if (seatAvailability == true) i++;
        }

        departureDate.setText(dateFix);
        numberOfSeat.setText(String.valueOf(i));

        convertView.setOnClickListener(v -> {
            MakeBookingActivity.thisSchedule = currentNumberPosition;
            Intent intent = new Intent(parent.getContext(), MakeBookingActivity.class);
            parent.getContext().startActivity(intent);
        });

        // then return the recyclable view
        return convertView;
    }
}

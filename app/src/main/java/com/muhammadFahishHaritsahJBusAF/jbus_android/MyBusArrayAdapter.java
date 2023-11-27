package com.muhammadFahishHaritsahJBusAF.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;

import java.util.List;

public class MyBusArrayAdapter extends ArrayAdapter<Bus> {
    public MyBusArrayAdapter(@NonNull Context ctx, List<Bus> busList) {
        super(ctx, 0, busList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.mybus_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Bus currentNumberPosition = getItem(position);

        TextView bus_name = currentItemView.findViewById(R.id.bus_name);
        TextView bus_type = currentItemView.findViewById(R.id.bus_type);
        TextView departureStation = currentItemView.findViewById(R.id.departure);
        TextView arrivalStation = currentItemView.findViewById(R.id.arrival);

        bus_name.setText(currentNumberPosition.name);
        bus_type.setText(currentNumberPosition.busType.toString());
        departureStation.setText(currentNumberPosition.departure);
        arrivalStation.setText(currentNumberPosition.arrival);


        // then return the recyclable view
        return currentItemView;
    }
}

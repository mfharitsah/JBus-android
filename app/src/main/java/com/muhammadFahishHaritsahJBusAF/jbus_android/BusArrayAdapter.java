package com.muhammadFahishHaritsahJBusAF.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import com.muhammadFahishHaritsahJBusAF.jbus_android.R;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;

public class BusArrayAdapter extends ArrayAdapter<Bus> {
    public BusArrayAdapter(@NonNull Context context, List<Bus> busList) {
        super(context, 0, busList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Bus currentNumberPosition = getItem(position);

        TextView bus_name = currentItemView.findViewById(R.id.bus_name);
        bus_name.setText(currentNumberPosition.name);

        TextView bus_type = currentItemView.findViewById(R.id.bus_type);
        bus_type.setText(currentNumberPosition.busType.toString());

        TextView departureTime = currentItemView.findViewById(R.id.departure_time);
        departureTime.setText(currentNumberPosition.departureTime);

        TextView arrivalTime = currentItemView.findViewById(R.id.arrival_time);
        arrivalTime.setText(currentNumberPosition.arrivalTime);

        TextView departureStation = currentItemView.findViewById(R.id.departure_station);
        departureStation.setText(currentNumberPosition.departure);

        TextView arrivalStation = currentItemView.findViewById(R.id.arrival_station);
        arrivalStation.setText(currentNumberPosition.arrival);

        TextView price = currentItemView.findViewById(R.id.price);
        price.setText(currentNumberPosition.price);


        // then return the recyclable view
        return currentItemView;
    }
}

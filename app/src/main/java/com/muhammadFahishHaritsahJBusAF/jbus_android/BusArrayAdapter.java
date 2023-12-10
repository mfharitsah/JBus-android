package com.muhammadFahishHaritsahJBusAF.jbus_android;

import android.content.Context;
import android.content.Intent;
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
    public BusArrayAdapter(@NonNull Context ctx, List<Bus> busList) {
        super(ctx, R.layout.mybus_view, busList);
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
        TextView bus_type = currentItemView.findViewById(R.id.bus_type);
        TextView departureCity = currentItemView.findViewById(R.id.departure_city);
        TextView arrivalCity = currentItemView.findViewById(R.id.arrival_city);
        TextView price = currentItemView.findViewById(R.id.price);

        bus_name.setText(currentNumberPosition.name);
        bus_type.setText(currentNumberPosition.busType.toString());
        departureCity.setText(currentNumberPosition.departure.city.toString());
        arrivalCity.setText(currentNumberPosition.arrival.city.toString());
        price.setText(String.valueOf(currentNumberPosition.price.price));

        currentItemView.setOnClickListener(v -> {
            MainActivity.mainClickedBus = currentNumberPosition;
            Intent intent = new Intent(parent.getContext(), BusDetailMain.class);
            parent.getContext().startActivity(intent);
        });

        // then return the recyclable view
        return currentItemView;
    }
}

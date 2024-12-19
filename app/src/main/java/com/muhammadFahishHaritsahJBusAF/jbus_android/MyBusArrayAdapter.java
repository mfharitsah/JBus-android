package com.muhammadFahishHaritsahJBusAF.jbus_android;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;

import java.util.List;

public class MyBusArrayAdapter extends ArrayAdapter<Bus> {
    public MyBusArrayAdapter(Context ctx, List<Bus> busList) {
        super(ctx, R.layout.mybus_view, busList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mybus_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Bus currentNumberPosition = getItem(position);

        TextView bus_name = convertView.findViewById(R.id.bus_name);
        TextView bus_type = convertView.findViewById(R.id.bus_type);
        TextView departureStation = convertView.findViewById(R.id.departure);
        TextView arrivalStation = convertView.findViewById(R.id.arrival);

        bus_name.setText(currentNumberPosition.name);
        bus_type.setText(currentNumberPosition.busType.toString());
        departureStation.setText(String.valueOf(currentNumberPosition.departure.city));
        arrivalStation.setText(String.valueOf(currentNumberPosition.arrival.city));

        convertView.setOnClickListener(v -> {
//            BusDetailActivity.thisBus = currentNumberPosition;
            ManageBusActivity.clickedBus = currentNumberPosition;
            Intent intent = new Intent(parent.getContext(), BusDetailActivity.class);
            parent.getContext().startActivity(intent);
        });

        // then return the recyclable view
        return convertView;
    }
}

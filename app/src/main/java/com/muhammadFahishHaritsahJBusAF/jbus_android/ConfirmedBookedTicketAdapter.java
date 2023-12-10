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

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Payment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConfirmedBookedTicketAdapter extends ArrayAdapter<Payment> {

    public ConfirmedBookedTicketAdapter(@NonNull Context context, List<Payment> paymentList) {
        super(context, R.layout.booked_ticket_view, paymentList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        // of the recyclable view is null then inflate the custom layout for the same
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.booked_ticket_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Payment currentNumberPosition = getItem(position);

        TextView dateSchedule = convertView.findViewById(R.id.date_schedule);
        TextView timeSchedule = convertView.findViewById(R.id.time_schedule);
        TextView departureCity = convertView.findViewById(R.id.departure_city_book);
        TextView departureStation = convertView.findViewById(R.id.departure_station_book);
        TextView arrivalCity = convertView.findViewById(R.id.arrival_city_book);
        TextView arrivalStation = convertView.findViewById(R.id.arrival_station_book);
        TextView busName = convertView.findViewById(R.id.bus_name_book);
        TextView busType = convertView.findViewById(R.id.bus_type_book);
        TextView paymentId = convertView.findViewById(R.id.payment_id);

        //date and time
        Date date = new Date(currentNumberPosition.departureDate.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateFix = formatter.format(date);
        Date time = new Date(currentNumberPosition.departureDate.getTime());
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        String timeFix = formatter2.format(time);
        dateSchedule.setText(dateFix);
        timeSchedule.setText(timeFix);
        //get bus from id
        Bus thisScheduleBus = null;
        for (Bus b : MainActivity.busList) {
            if (b.id == currentNumberPosition.getBusId()) {
                thisScheduleBus = b;
            }
        }
        departureCity.setText(thisScheduleBus.departure.city.toString());
        departureStation.setText(thisScheduleBus.departure.stationName);
        arrivalCity.setText(thisScheduleBus.arrival.city.toString());
        arrivalStation.setText(thisScheduleBus.arrival.stationName);
        busName.setText(thisScheduleBus.name);
        busType.setText(thisScheduleBus.busType.toString());
        paymentId.setText(String.valueOf(currentNumberPosition.id));

        Bus finalThisScheduleBus = thisScheduleBus;
        convertView.setOnClickListener(v -> {
            InvoiceActivity.invoicePayment = currentNumberPosition;
            InvoiceActivity.invoiceBus = finalThisScheduleBus;

            Intent intent = new Intent(parent.getContext(), InvoiceActivity.class);
            parent.getContext().startActivity(intent);
        });

        // then return the recyclable view
        return convertView;
    }
}

package com.muhammadFahishHaritsahJBusAF.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Bus;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Invoice;
import com.muhammadFahishHaritsahJBusAF.jbus_android.model.Payment;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.BaseApiService;
import com.muhammadFahishHaritsahJBusAF.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    private TextView dateSchedule, timeSchedule, departureCity, departureStation, arrivalCity, arrivalStation, busName, busType;
    private TextView buyerName, buyerEmail;
    private TextView totalPrice, numberOfTicket;
    private TextView paymentStatus, downloadInvoice;

    public static Bus invoiceBus;
    public static Payment invoicePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        dateSchedule = findViewById(R.id.date_schedule);
        timeSchedule = findViewById(R.id.time_schedule);
        departureCity = findViewById(R.id.departure_city_book);
        departureStation = findViewById(R.id.departure_station_book);
        arrivalCity = findViewById(R.id.arrival_city_book);
        arrivalStation = findViewById(R.id.arrival_station_book);
        busName = findViewById(R.id.bus_name_book);
        busType = findViewById(R.id.bus_type_book);
        numberOfTicket = findViewById(R.id.number_of_tickets);
        totalPrice = findViewById(R.id.total_price);
        buyerName = findViewById(R.id.buyer_name);
        buyerEmail = findViewById(R.id.buyer_email);
        paymentStatus = findViewById(R.id.payment_status);
        downloadInvoice = findViewById(R.id.download_invoice);

        // set view
        Date date = new Date(invoicePayment.departureDate.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateFix = formatter.format(date);
        Date time = new Date(invoicePayment.departureDate.getTime());
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        String timeFix = formatter2.format(time);
        dateSchedule.setText(dateFix);
        timeSchedule.setText(timeFix);
        // bus information
        departureCity.setText(invoiceBus.departure.city.toString());
        departureStation.setText(invoiceBus.departure.stationName);
        arrivalCity.setText(invoiceBus.arrival.city.toString());
        arrivalStation.setText(invoiceBus.arrival.stationName);
        busName.setText(invoiceBus.name);
        busType.setText(invoiceBus.busType.toString());
        // total price
        int ticketNum = 0;
        for (String s : invoicePayment.busSeat) {
            ticketNum++;
        }
        numberOfTicket.setText(ticketNum + " ticket");
        double viewTotalPrice = invoiceBus.price.price * ticketNum;
        int price = (int) viewTotalPrice;
        totalPrice.setText(String.valueOf(price));
        // buyer information
        buyerName.setText(LoginActivity.loggedAccount.name);
        buyerEmail.setText(LoginActivity.loggedAccount.email);
        // payment status
        paymentStatus.setText(String.valueOf(invoicePayment.status));
        if (invoicePayment.status.equals(Invoice.PaymentStatus.FAILED)) {
            paymentStatus.setTextColor(Color.RED);
        }

    }
}
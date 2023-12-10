package com.muhammadFahishHaritsahJBusAF.jbus_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

public class SelectSeatDialog extends AppCompatDialogFragment {
    private EditText inputSelectSeat;
    private TextView maxNumOfSeat, selectedSeats;
    private SelectedSeatDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_seat_dialog, null);

        builder.setView(view)
                .setTitle("AF[01-" + MakeBookingActivity.maxSeats + "]")
                .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selectedSeat = inputSelectSeat.getText().toString();
                                listener.applyTexts(selectedSeat);
                            }
                        });

        inputSelectSeat = view.findViewById(R.id.selected_seat_input);

        return builder.create();
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);

        try {
            listener = (SelectedSeatDialogListener) ctx;
        } catch (ClassCastException e) {
            throw new ClassCastException(ctx.toString() + " must implement SelectedSeatDialogListener");
        }
    }

    public interface SelectedSeatDialogListener{
        void applyTexts(String selectedSeat);
    }
}

package com.example.hp.eduapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.hp.eduapp.AddReminderActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by radman on 10/15/2016.
 */
public class DatePickerFragment extends DialogFragment {
    //private static final String LOG_TAG = DatePickerFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Date date = new Date();

        //return a new date picker
        return new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date newDate = new Date((i - 1900), i1, i2);
                        DateFormat df = new SimpleDateFormat("EEE, dd MMM, yyyy", Locale.getDefault());
                        AddReminderActivity.date = System.currentTimeMillis() +
                                (newDate.getTime() - System.currentTimeMillis());
                        AddReminderActivity.tv.setText(df.format(newDate));
                    }
                }, (date.getYear() + 1900), date.getMonth(), date.getDate()
        );
    }
}

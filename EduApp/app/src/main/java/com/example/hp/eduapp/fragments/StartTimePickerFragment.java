package com.example.hp.eduapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import com.example.hp.eduapp.AddCourseActivity;
import com.example.hp.eduapp.AddReminderActivity;
import com.example.hp.eduapp.utils.UtilityMethods;

import java.util.Date;

/**
 * Created by radman on 10/12/2016.
 */
public class StartTimePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = new Date();

        //return a new time picker
        return new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        if (getTag().equals("reminderStartTimePicker")){
                            setReminderStartTime(hour, minute);
                        } else if (getTag().equals("coursePeriodStartTimePicker")) {
                            setCoursePeriodStartTime(hour, minute);
                        }

                    }
                }, date.getHours(), date.getMinutes(), true
        );

    }

    private void setReminderStartTime(int hour, int minute) {
        long hrInMillis = UtilityMethods.convertHourToMillis(hour);
        long minInMillis = UtilityMethods.convertMinuteToMillis(minute);
        AddReminderActivity.startTime = AddReminderActivity.date + hrInMillis + minInMillis;

        String hr, min;

        if (hour < 10) hr = "0" + hour;
        else hr = Integer.toString(hour);

        if (minute < 10) min = "0" + minute;
        else min = Integer.toString(minute);

        String time = hr + ":" + min + "hrs";
        AddReminderActivity.tv1.setText(time);
    }

    private void setCoursePeriodStartTime(int hour, int minute) {
        long hrInMillis = UtilityMethods.convertHourToMillis(hour);
        long minInMillis = UtilityMethods.convertMinuteToMillis(minute);
        AddCourseActivity.startTime = hrInMillis + minInMillis;

        String hr, min;

        if (hour < 10) hr = "0" + hour;
        else hr = Integer.toString(hour);

        if (minute < 10) min = "0" + minute;
        else min = Integer.toString(minute);

        String time = hr + ":" + min + "hrs";
        AddCourseActivity.getP().setTimeFrom(time);
        AddCourseActivity.startsAtBtn.setText(AddCourseActivity.getP().getTimeFrom());
    }

}

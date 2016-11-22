package com.example.hp.eduapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import com.example.hp.eduapp.AddCourseActivity;
import com.example.hp.eduapp.AddReminderActivity;
import com.example.hp.eduapp.R;
import com.example.hp.eduapp.utils.UtilityMethods;

import java.util.Date;

/**
 * Created by radman on 10/24/2016.
 */
public class StopTimePickerFragment extends DialogFragment {

    //private static final String LOG_TAG = StopTimePickerFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = new Date();

        //return a new time picker
        return new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        if (getTag().equals("reminderStopTimePicker")){
                            setReminderStopTime(hour, minute);
                        } else if (getTag().equals("coursePeriodStopTimePicker")) {
                            setCoursePeriodStopTime(hour, minute);
                        }

                    }
                }, date.getHours(), date.getMinutes(), true
        );

    }

    private void setReminderStopTime(int hour, int minute) {
        long hrInMillis = UtilityMethods.convertHourToMillis(hour);
        long minInMillis = UtilityMethods.convertMinuteToMillis(minute);

        long pureStartTime = AddReminderActivity.startTime - AddReminderActivity.date;

        if (pureStartTime > (hrInMillis + minInMillis)){
            UtilityMethods.message(getActivity(), "Stop time should be\ngreater than start time");
            AddReminderActivity.tv2.setText(getResources().getString(R.string.reminder_stop_time_hint));
            return;
        }
        long tempStopTime = AddReminderActivity.date + hrInMillis + minInMillis;
        long timeDiff = tempStopTime - AddReminderActivity.startTime;

        AddReminderActivity.stopTime = AddReminderActivity.startTime + timeDiff;

        String hr, min;

        if (hour < 10) hr = "0" + hour;
        else hr = Integer.toString(hour);

        if (minute < 10) min = "0" + minute;
        else min = Integer.toString(minute);

        String time = hr + ":" + min + "hrs";
        AddReminderActivity.tv2.setText(time);
    }

    private void setCoursePeriodStopTime(int hour, int minute) {
        long hrInMillis = UtilityMethods.convertHourToMillis(hour);
        long minInMillis = UtilityMethods.convertMinuteToMillis(minute);

        if ((hrInMillis + minInMillis) < AddCourseActivity.startTime) {
            UtilityMethods.message(getActivity(), "Stop time should be\ngreater than start time");
            return;
        }

        AddCourseActivity.stopTime = hrInMillis + minInMillis;

        String hr, min;

        if (hour < 10) hr = "0" + hour;
        else hr = Integer.toString(hour);

        if (minute < 10) min = "0" + minute;
        else min = Integer.toString(minute);

        String time = hr + ":" + min + "hrs";
        AddCourseActivity.getP().setTimeTo(time);
        AddCourseActivity.stopsAtBtn.setText(AddCourseActivity.getP().getTimeTo());
    }


}

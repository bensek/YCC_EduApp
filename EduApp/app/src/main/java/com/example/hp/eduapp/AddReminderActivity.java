package com.example.hp.eduapp;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hp.eduapp.fragments.DatePickerFragment;
import com.example.hp.eduapp.fragments.StartTimePickerFragment;
import com.example.hp.eduapp.fragments.StopTimePickerFragment;
import com.example.hp.eduapp.utils.UtilityMethods;

import java.util.TimeZone;

public class AddReminderActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddReminderActivity.class.getSimpleName();

    public static long date, startTime, stopTime;
    private String alertType, title;

    public static TextView tv, tv1, tv2;
    private EditText et, et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        title = i.getStringExtra(Intent.EXTRA_TEXT);

        tv = (TextView) findViewById(R.id.reminder_date_textview);
        tv1 = (TextView) findViewById(R.id.reminder_start_time_textview);
        tv2 = (TextView) findViewById(R.id.reminder_stop_time_textview);
        et = (EditText) findViewById(R.id.reminder_title_edittext);
        et1 = (EditText) findViewById(R.id.reminder_alert_minutes_edittext);

        Spinner alertTypeSpinner = (Spinner) findViewById(R.id.reminder_alert_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.alert_types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alertTypeSpinner.setAdapter(adapter);

        alertTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alertType = (String) adapterView.getItemAtPosition(i);
                Log.v(LOG_TAG, "Alert type selected is " + alertType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDatePicker();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStartTimePicker();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStopTimePicker();
            }
        });

    }

    private long getCalendarID() {
        String[] projection = {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        };
        String selection =
                CalendarContract.Events.ACCOUNT_NAME + " = ? AND " +
                        CalendarContract.Events.ACCOUNT_TYPE + " = ? AND " +
                        CalendarContract.Events.OWNER_ACCOUNT + " = ?";
        String[] selectionArgs = {
                "williamkaos.kibirango76@gmail.com",
                "com.google",
                "williamkaos.kibirango76@gmail.com"
        };

        if (ActivityCompat.checkSelfPermission(AddReminderActivity.this, android.Manifest.permission
                .READ_CALENDAR) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddReminderActivity.this, new String[]{android.Manifest
                    .permission.READ_CALENDAR, android.Manifest.permission.WRITE_CALENDAR}, 1);
            return 0;
        }

        Cursor c = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, projection,
                selection,
                selectionArgs, null);

        long id = 0;
        if (c != null && c.moveToFirst()) id = c.getLong(c.getColumnIndex(CalendarContract.Calendars._ID));
        c.close();

        return id;
    }

    private long setReminderEvent(String title, long id, long startTime, long stopTime) {
//        Time t = new Time();
//        t.setToNow();
//        long startTime = t.toMillis(false) + UtilityMethods.convertMinuteToMillis(15);
//        long stopTime = startTime + UtilityMethods.convertMinuteToMillis(30);

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.DTSTART, startTime);
        cv.put(CalendarContract.Events.DTEND, stopTime);
        cv.put(CalendarContract.Events.TITLE, title);
        cv.put(CalendarContract.Events.DESCRIPTION,
                UtilityMethods.getReminderTag(AddReminderActivity.this, this.title));
        cv.put(CalendarContract.Events.CALENDAR_ID, id);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        if (ActivityCompat.checkSelfPermission(AddReminderActivity.this, android.Manifest.permission
                .READ_CALENDAR) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddReminderActivity.this, new String[]{android.Manifest
                    .permission.READ_CALENDAR, android.Manifest.permission.WRITE_CALENDAR}, 1);
            return 0;
        }

        Uri reminderUri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, cv);
        return reminderUri != null ? Long.parseLong(reminderUri.getLastPathSegment()) : 0;
//        Intent intent = new Intent(Intent.ACTION_INSERT)
//                .setData(CalendarContract.Events.CONTENT_URI)
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startTime)
//                .putExtra(CalendarContract.Events.TITLE, title)
//                .putExtra(CalendarContract.Events.DESCRIPTION, "#" + getString(R.string.app_name) +"_Reminder");
//        if (intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);
//        }
    }

    private Uri setReminder(long eventId, int minutes, int alarmType) {
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Reminders.MINUTES, minutes);
        cv.put(CalendarContract.Reminders.METHOD, alarmType);
        cv.put(CalendarContract.Reminders.EVENT_ID, eventId);

        if (ActivityCompat.checkSelfPermission(AddReminderActivity.this, android.Manifest.permission
                .READ_CALENDAR) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddReminderActivity.this, new String[]{android.Manifest
                    .permission.READ_CALENDAR, android.Manifest.permission.WRITE_CALENDAR}, 1);
            return null;
        }

        return getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, cv);
    }

    private void launchDatePicker() {
        date = 0;
        startTime = 0;
        stopTime = 0;
        DialogFragment dateFrag = new DatePickerFragment();
        dateFrag.show(getFragmentManager(), "reminderDatePicker");
    }

    private void launchStartTimePicker() {
        startTime = 0;
        stopTime = 0;
        DialogFragment startTimeFrag = new StartTimePickerFragment();
        startTimeFrag.show(getFragmentManager(), "reminderStartTimePicker");
    }

    private void launchStopTimePicker() {
        stopTime = 0;
        DialogFragment stopTimeFrag = new StopTimePickerFragment();
        stopTimeFrag.show(getFragmentManager(), "reminderStopTimePicker");
    }

    private int getAlarmType(String alertType) {
        int type;
        switch (alertType) {
            case "Alarm": type = CalendarContract.Reminders.METHOD_ALARM; break;
            case "Alert": type = CalendarContract.Reminders.METHOD_ALERT; break;
            case "Email": type = CalendarContract.Reminders.METHOD_EMAIL; break;
            case "SMS": type = CalendarContract.Reminders.METHOD_SMS; break;
            case "Default": type = CalendarContract.Reminders.METHOD_DEFAULT; break;
            default: type =  -1; break;
        }
        return type;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        et.setError(null);
        switch (id) {
            case R.id.action_accept:
                String title = et.getText().toString();
                if (!TextUtils.isEmpty(title)) {
                    Uri reminderUri = setReminder(
                            setReminderEvent(title, getCalendarID(), startTime, stopTime),
                            Integer.parseInt(et1.getText().toString()),
                            getAlarmType(alertType)
                    );
                    if (reminderUri != null)
                        UtilityMethods.message(AddReminderActivity.this, "Reminder set");
                    finish();
                } else if (TextUtils.isEmpty(title)) {
                    et.setError(getString(R.string.error_reminder_title_required));
                    et.requestFocus();
                }
                break;
            case R.id.action_discard:
                finish();
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

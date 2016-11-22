package com.example.hp.eduapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.eduapp.data_models.Period;
import com.example.hp.eduapp.fragments.CoursesFragment;
import com.example.hp.eduapp.fragments.StartTimePickerFragment;
import com.example.hp.eduapp.fragments.StopTimePickerFragment;

public class AddCourseActivity extends AppCompatActivity {

    //private static final String LOG_TAG = AddCourseActivity.class.getSimpleName();
    private String day = "";
    public static long startTime, stopTime;
    private EditText courseName;
    public static Button startsAtBtn, stopsAtBtn;
    private static Period p = new Period(null, null, null);
    public static Period getP() {
        return p;
    }
//    public static ArrayList<Period> getCoursePeriods() {
//        return coursePeriods;
//    }
    //private static ArrayList<Period> coursePeriods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseName = (EditText) findViewById(R.id.course_name_edittext);

        Spinner weekdays = (Spinner) findViewById(R.id.weekdays_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.week_days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekdays.setAdapter(adapter);

        startsAtBtn = (Button) findViewById(R.id.starts_at_btn);
        startsAtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStartTimePicker();
            }
        });

        stopsAtBtn = (Button) findViewById(R.id.stops_at_btn);
        stopsAtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStopTimePicker();
            }
        });

        weekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = (String) adapterView.getItemAtPosition(i);
                p.setDay(day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        courseName.setError(null);
        switch (id) {
            case R.id.action_accept:
                String course = courseName.getText().toString();
                if (!TextUtils.isEmpty(course)) {
                    CoursesFragment.courses.add(course);
                    CoursesFragment.adapter.notifyDataSetChanged();
                    Intent intent = new Intent(this, com.example.hp.eduapp.CourseActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, course);
                    startActivity(intent);
                    finish();
                } else if (TextUtils.isEmpty(course)) {
                    courseName.setError(getString(R.string.error_course_name_required));
                    courseName.requestFocus();
                }
                break;
            case R.id.action_discard:
                finish();
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void launchStartTimePicker() {
        startTime = 0; stopTime = 0;
        DialogFragment timeFrag = new StartTimePickerFragment();
        timeFrag.show(getFragmentManager(), "coursePeriodStartTimePicker");
    }

    private void launchStopTimePicker() {
        stopTime = 0;
        DialogFragment timeFrag = new StopTimePickerFragment();
        timeFrag.show(getFragmentManager(), "coursePeriodStopTimePicker");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

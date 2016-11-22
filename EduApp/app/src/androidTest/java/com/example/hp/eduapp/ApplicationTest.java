package com.example.hp.eduapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private static final String LOG_TAG = ApplicationTest.class.getSimpleName();
    public ApplicationTest() {
        super(Application.class);
    }

    public void testDateClass() {
        final Date date = new Date(System.currentTimeMillis());
        Log.e(LOG_TAG, "Date returned: " + date.toString());
        Log.e(LOG_TAG, "Date: " + date.getDate());
        Log.e(LOG_TAG, "Month: " + date.getMonth());
        Log.e(LOG_TAG, "Year: " + (date.getYear() + 1900)); //the zib was here. add 1900 to resolve it

//        java.util.Calendar cal = java.util.Calendar.getInstance();
//        Log.e(LOG_TAG, "Calendar year: " + (cal.getWeekYear()));

//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        Log.e(LOG_TAG, "Date returned: " + year + " " + month + " " + day);

    }
}
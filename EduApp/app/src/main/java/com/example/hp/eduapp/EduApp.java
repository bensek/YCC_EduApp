package com.example.hp.eduapp;

import android.app.Application;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;


/**
 * Created by HP on 9/3/2016.
 */
public class EduApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        //App Events let you measure installs on your mobile app ads,
        // create high value audiences for targeting, and view analytics
        // including user demographics.To automatically log app activation events, we add:
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
    }
}

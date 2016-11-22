package com.example.hp.eduapp.data_models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP on 10/12/2016.
 */
public class Period implements Parcelable{
    //TODO: rethink the structure (whether to use long (milliseconds) or String (readable time))

    private String day;
    private String timeFrom;
    private String timeTo;

    public Period(String day, String startsAtTime, String stopsAtTime) {
        super();
        this.day = day;
        this.timeFrom = startsAtTime;
        this.timeTo = stopsAtTime;
    }

    protected Period(Parcel in) {
        day = in.readString();
        timeFrom = in.readString();
        timeTo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(timeFrom);
        dest.writeString(timeTo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Period> CREATOR = new Creator<Period>() {
        @Override
        public Period createFromParcel(Parcel in) {
            return new Period(in);
        }

        @Override
        public Period[] newArray(int size) {
            return new Period[size];
        }
    };

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }
}

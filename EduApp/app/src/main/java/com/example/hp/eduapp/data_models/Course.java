package com.example.hp.eduapp.data_models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by HP on 10/12/2016.
 */
public class Course implements Parcelable{
    private String courseID;
    private String courseName;
    private String courseCode;
    private String creditUnits;
    private boolean isRetake;
    private ArrayList<Period> periods;

    protected Course(Parcel in) {
        courseID = in.readString();
        courseName = in.readString();
        courseCode = in.readString();
        creditUnits = in.readString();
        isRetake = in.readByte() != 0;
        periods = in.createTypedArrayList(Period.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseID);
        dest.writeString(courseName);
        dest.writeString(courseCode);
        dest.writeString(creditUnits);
        dest.writeByte((byte) (isRetake ? 1 : 0));
        dest.writeTypedList(periods);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<Period> periods) {
        this.periods = periods;
    }

    public Course() {super();}

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCreditUnits() {
        return creditUnits;
    }

    public void setCreditUnits(String creditUnits) {
        this.creditUnits = creditUnits;
    }

    public boolean isRetake() {
        return isRetake;
    }

    public void setRetake(boolean retake) {
        isRetake = retake;
    }
}

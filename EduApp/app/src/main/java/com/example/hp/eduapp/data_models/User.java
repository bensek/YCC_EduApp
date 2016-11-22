package com.example.hp.eduapp.data_models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP on 9/25/2016.
 */
public class User implements Parcelable{
    private String userId;
    private String email;
    private String fname;
    private String lname;
    private String username;
    private String phonecode;
    private int phonenumber;
    private String level;
    private String programme;
    private String year;
    private String time;
    private String institution;
    private String address;
    // Courses, Groups etc

    public User(){ super();} // default constructor

    //Making the class parcelable

    public User(Parcel in){
        userId = in.readString();
        email = in.readString();
        fname = in.readString();
        lname = in.readString();
        username = in.readString();
        phonecode = in.readString();
        phonenumber = in.readInt();
        level = in.readString();
        programme = in.readString();
        year = in.readString();
        time = in.readString();
        institution = in.readString();
        address = in.readString();
    }


    public User(String id, String email, String fname, String lname, String username,
                String phonecode, int phonenumber, String level, String programme,String year,
                String time, String institution, String address){

        this.userId = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.phonecode = phonecode;
        this.phonenumber = phonenumber;
        this.level = level;
        this.programme = programme;
        this.year = year;
        this.time = time;
        this.institution = institution;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(email);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(username);
        dest.writeString(phonecode);
        dest.writeInt(phonenumber);
        dest.writeString(level);
        dest.writeString(programme);
        dest.writeString(year);
        dest.writeString(time);
        dest.writeString(institution);
        dest.writeString(address);
    }
}

package com.example.hp.eduapp.data_models;

/**
 * Created by radman on 10/16/2016.
 */
public class Picture {

    private String picFileName;
    private String picFilePath;

    public Picture() {}

    public String getPicFilePath() {
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName;
    }

}

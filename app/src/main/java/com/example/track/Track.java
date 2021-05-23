package com.example.track;

import android.location.Location;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Track {
    private int id;
    private double latitude;
    private double altitude;
    private double longitude;
    private String imageUri;
    private String name;
    private String date;
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public Track(int id, double latitude, double altitude, double longitude, String imageUri, String name,String date) {
        this.id = id;
        this.latitude = latitude;
        this.altitude = altitude;
        this.longitude = longitude;
        this.imageUri = imageUri;
        this.name = name;
        this.date=date;
    }
    public Track(int id, double latitude, double altitude, double longitude, String imageUri, String name) {
        Date currentDate=new Date();
        this.date=formatter.format(currentDate);
        this.id = id;
        this.latitude = latitude;
        this.altitude = altitude;
        this.longitude = longitude;
        this.imageUri = imageUri;
        this.name = name;
    }

    public Track(int id, double latitude, double altitude, double longitude, String name) {
        Date currentDate=new Date();
        this.date=formatter.format(currentDate);
        this.date=formatter.format(currentDate);
        this.id = id;
        this.latitude = latitude;
        this.altitude = altitude;
        this.longitude = longitude;
        this.name = name;
        this.imageUri="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }


}

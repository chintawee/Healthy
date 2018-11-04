package com.example.a59070023.kongkarat.healthypoon.healthyp;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep implements Serializable {
    private int id;
    private String date;
    private String toBedTime;
    private String awakeTime;
    private String sleepTime;

    public Sleep()
    {

    }

    public int getId()

    {
        return id;
    }

    public void setId(int id){

        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setToBedTime(String toBedTime) {
        this.toBedTime = toBedTime;
    }

    public void setAwakeTime(String awakeTime) {
        this.awakeTime = awakeTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getDate() {
        return date;
    }


    public String getToBedTime() {
        return toBedTime;
    }

    public String getAwakeTime() {
        return awakeTime;
    }

    public String getSleepTime() {
        calculateSleeptime();
        return sleepTime;
    }

    public void calculateSleeptime()
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date toBed = format.parse(toBedTime);
            Date awake = format.parse(awakeTime);
            long difference = 0;
            boolean after = false;
            if (toBed.after(awake))
            {
                difference = ((awake.getTime() - toBed.getTime())/1000)/60;
                after = true;
            }
            difference = Math.abs(difference);
            int hour = (int) difference/60;
            if (after)
            {
                hour = 24 -hour ;
            }
            int minute = (int) difference %60;
            sleepTime = String.format("%02d",hour) + ":" +String.format("%02d" ,minute);
        }
        catch (Exception e)
        {
            Log.d("test", e.getMessage());
        }
    }

}

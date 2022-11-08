package com.example.relaxtep;

import com.google.type.DateTime;

public class log {
    String uid;
    long dateTime;
    int footsteps;
    int timeinsec;

    public log(long dateTime, int footsteps, int timeinsec, String uid) {
        this.dateTime = dateTime;
        this.footsteps = footsteps;
        this.timeinsec = timeinsec;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getFootsteps() {
        return footsteps;
    }

    public void setFootsteps(int footsteps) {
        this.footsteps = footsteps;
    }

    public int getTimeinsec() {
        return timeinsec;
    }

    public void setTimeinsec(int timeinsec) {
        this.timeinsec = timeinsec;
    }
}

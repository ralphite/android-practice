package com.ralphwen.runtracker;

import java.util.Date;

/**
 * Created by Yadong on 12/17/2014.
 * ralph.wen@gmail.com
 * Project: RunTracker
 */
public class Run {
    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getStartDate() {

        return mStartDate;
    }

    private Date mStartDate;

    public Run() {
        mStartDate = new Date();
    }

    public int getDurationSeconds(long endMillis) {
        return (int) ((endMillis - mStartDate.getTime()) / 1000);
    }

    public static String formatDuration(int durationSeconds) {
        int seconds = durationSeconds % 60;
        int minutes = ((durationSeconds - seconds) / 60) % 60;
        int hours = (durationSeconds - (minutes * 60) - seconds) / 3600;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

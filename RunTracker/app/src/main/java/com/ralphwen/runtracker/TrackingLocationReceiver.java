package com.ralphwen.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by Yadong on 12/18/2014.
 * ralph.wen@gmail.com
 * Project: RunTracker
 */
public class TrackingLocationReceiver extends LocationReceiver {
    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
    }
}

package com.ralphwen.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by Yadong on 12/19/2014.
 * ralph.wen@gmail.com
 * Project: RunTracker
 */
public class LastLocationLoader extends DataLoader<Location> {

    private long mRunId;

    public LastLocationLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }

    @Override
    public Location loadInBackground() {
        return RunManager.get(getContext()).getLastLocationForRun(mRunId);
    }
}

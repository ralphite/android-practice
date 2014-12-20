package com.ralphwen.runtracker;

import android.content.Context;

/**
 * Created by Yadong on 12/19/2014.
 * ralph.wen@gmail.com
 * Project: RunTracker
 */
public class RunLoader extends DataLoader<Run> {

    private long mRunId;

    public RunLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }

    @Override
    public Run loadInBackground() {
        return RunManager.get(getContext()).getRun(mRunId);
    }
}

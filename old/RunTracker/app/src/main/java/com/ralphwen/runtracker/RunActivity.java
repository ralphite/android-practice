package com.ralphwen.runtracker;

import android.support.v4.app.Fragment;


public class RunActivity extends SingleFragmentActivity {

    //key for passing run id as long
    public static final String EXTRA_RUN_ID = "com.ralphwen.runtracker.run_id";

    @Override
    protected Fragment createFragment() {
        long runId = getIntent().getLongExtra(EXTRA_RUN_ID, -1);
        if (runId != -1) {
            return RunFragment.newInstance(runId);
        } else {
            return new RunFragment();
        }
    }
}

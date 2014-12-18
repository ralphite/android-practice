package com.ralphwen.runtracker;

import android.support.v4.app.Fragment;

/**
 * Created by Yadong on 12/18/2014.
 * ralph.wen@gmail.com
 * Project: RunTracker
 */
public class RunListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }
}

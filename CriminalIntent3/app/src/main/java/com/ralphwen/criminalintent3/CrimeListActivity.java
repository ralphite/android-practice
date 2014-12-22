package com.ralphwen.criminalintent3;

import android.support.v4.app.Fragment;

/**
 * Created by Yadong on 12/22/2014.
 * ralph.wen@gmail.com
 * Project: CriminalIntent3
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}

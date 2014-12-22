package com.ralphwen.criminalintent3;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Yadong on 12/22/2014.
 * ralph.wen@gmail.com
 * Project: CriminalIntent3
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context c) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}

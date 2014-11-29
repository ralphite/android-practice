package com.ralphwen.criminalintent2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class CrimeLab {

	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";

	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;

	private static CrimeLab sCrimeLab;
	private Context mAppContext;

	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);

		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			mCrimes = new ArrayList<>();
			Log.e(TAG, "Error loading crimes:", e);
		}

		/*
		 * for (int i = 0; i < 100; i++) { Crime c = new Crime();
		 * c.setTitle("Crime #" + i); c.setSolved(i % 2 == 0); mCrimes.add(c); }
		 */
	}

	public void addCrime(Crime c) {
		mCrimes.add(c);
	}

	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
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

	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}
}

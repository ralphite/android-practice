package com.ralphwen.criminalintent2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.R.integer;
import android.content.Context;

public class CriminalIntentJSONSerializer {

	private Context mContext;
	private String mFilename;

	public CriminalIntentJSONSerializer(Context context, String file) {
		mContext = context;
		mFilename = file;
	}

	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {

		ArrayList<Crime> crimes = new ArrayList<>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonStringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonStringBuilder.append(line);
			}

			JSONArray array = (JSONArray) new JSONTokener(
					jsonStringBuilder.toString()).nextValue();

			for (int i = 0; i < array.length(); i++)
				crimes.add(new Crime(array.getJSONObject(i)));
		} catch (FileNotFoundException e) {
			// ignore
			// happens when starting fresh
		} finally {
			if (reader != null) {
				reader.close();
			}
			reader = null;
		}

		return crimes;
	}

	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,
			IOException {
		JSONArray array = new JSONArray();

		for (Crime c : crimes) {
			array.put(c.toJSON());
		}

		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}

package com.ralphwen.criminalintent;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
	public static final String EXTRA_TIME = "com.ralphwen.criminalintent.time";

	private Calendar mCalendar;

	public static TimePickerFragment newInstance(Calendar calendar) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, calendar);

		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mCalendar = (Calendar) getArguments().getSerializable(EXTRA_TIME);

		int hour = mCalendar.get(Calendar.HOUR);
		int minute = mCalendar.get(Calendar.MINUTE);
		int ampm = mCalendar.get(Calendar.AM);

		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_time, null);

		final TimePicker timePicker = (TimePicker) v
				.findViewById(R.id.dialog_time_timePicker);
		if (ampm > 0)
			timePicker.setCurrentHour(hour % 12 + 12);
		else
			timePicker.setCurrentHour(hour % 12);
		timePicker.setCurrentMinute(minute);

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(R.string.time_picker_title)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								sendResult(Activity.RESULT_OK);
							}

							private void sendResult(int resultCode) {
								// TODO Auto-generated method stub
								if (getTargetFragment() == null) {
									return;
								}

								int currentHour = timePicker.getCurrentHour();
								int currentMinute = timePicker
										.getCurrentMinute();
								if (currentHour > 12) {
									mCalendar.set(Calendar.AM, 1);
								} else {
									mCalendar.set(Calendar.AM, 0);
								}

								mCalendar.set(mCalendar.get(Calendar.YEAR),
										mCalendar.get(Calendar.MONTH),
										mCalendar.get(Calendar.DATE),
										currentHour, currentMinute);

								Intent i = new Intent();
								i.putExtra(EXTRA_TIME, mCalendar);

								getTargetFragment().onActivityResult(
										getTargetRequestCode(), resultCode, i);
							}
						}).create();
	}
}

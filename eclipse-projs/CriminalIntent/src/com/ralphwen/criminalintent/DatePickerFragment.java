package com.ralphwen.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {

	public static final String EXTRA_DATE = "com.ralphwen.criminalintent.date";

	private Calendar mCalendar;

	public static DatePickerFragment newInstance(Calendar calendar) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, calendar);

		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}

		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mCalendar);

		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, i);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		mCalendar = (Calendar) getArguments().getSerializable(EXTRA_DATE);

		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);

		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_date, null);

		DatePicker datePicker = (DatePicker) v
				.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				mCalendar.set(Calendar.YEAR, year);
				mCalendar.set(Calendar.MONTH, monthOfYear);
				mCalendar.set(Calendar.DATE, dayOfMonth);
				
				getArguments().putSerializable(EXTRA_DATE, mCalendar);
			}

		});

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(R.string.date_picker_title)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								sendResult(Activity.RESULT_OK);
							}
						}).create();
	}
}

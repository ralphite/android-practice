package com.ralphwen.criminalintent;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ChoiceFragment extends DialogFragment {
	public static final String EXTRA_CHOICE = "com.ralphwen.criminalintent.choice";

	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_TIME = "time";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_TIME = 1;

	private Calendar mCalendar;
	private Fragment mParentFragment;
	private boolean dateIsChosen;

	public static ChoiceFragment newInstance(Calendar calendar, Fragment parent) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CHOICE, calendar);

		ChoiceFragment fragment = new ChoiceFragment();
		fragment.setArguments(args);
		fragment.mParentFragment = parent;

		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mCalendar = (Calendar) getArguments().getSerializable(EXTRA_CHOICE);

		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_choice, null);

		final RadioGroup radioGroup = (RadioGroup) v
				.findViewById(R.id.choice_dialog_radioGroup);
		radioGroup.check(R.id.choice_dialog_date_radioButton);
		dateIsChosen = true;

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				dateIsChosen = checkedId == R.id.choice_dialog_date_radioButton;
			}

		});

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(R.string.choice_dialog_title)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								FragmentManager fm = getActivity()
										.getSupportFragmentManager();
								if (dateIsChosen) {
									// open date dialog
									DatePickerFragment datePickerFragment = DatePickerFragment
											.newInstance(mCalendar);
									datePickerFragment.setTargetFragment(
											mParentFragment, REQUEST_DATE);
									datePickerFragment.show(fm, DIALOG_DATE);
								} else {
									// open time dialog
								}
							}
						}).create();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			mCalendar = (Calendar) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
		} else if (requestCode == REQUEST_TIME) {
			// mCalendar = (Calendar) data
			// .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
		}
		// close self and send result
		if (getTargetFragment() == null) {
			return;
		}

		Intent i = new Intent();
		i.putExtra(EXTRA_CHOICE, mCalendar);

		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, i);
	}
}

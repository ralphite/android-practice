package com.ralphwen.criminalintent;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ChoiceFragment extends DialogFragment {
	public static final String EXTRA_CHOICE = "com.ralphwen.criminalintent.choice";
	private Calendar mCalendar;
	private boolean dateIsChosen;

	public static ChoiceFragment newInstance(Calendar calendar) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CHOICE, calendar);

		ChoiceFragment fragment = new ChoiceFragment();
		fragment.setArguments(args);

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
								if (dateIsChosen) {
									// open date dialog
								} else {
									// open time dialog
								}

								// return data to CrimeFragment
							}
						}).create();
	}
}

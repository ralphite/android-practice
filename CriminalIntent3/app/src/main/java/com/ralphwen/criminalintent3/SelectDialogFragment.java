package com.ralphwen.criminalintent3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;

import java.util.Date;

/**
 * Created by Yadong on 12/23/2014.
 * ralph.wen@gmail.com
 * Project: CriminalIntent3
 */
public class SelectDialogFragment extends DialogFragment {

    //intent extras
    public static final String EXTRA_DATE_TIME =
            "com.ralphwen.criminalintent3.date_time";

    //DatePickerDialog identifier
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";

    private Date mDate;

    public static SelectDialogFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE_TIME, date);

        SelectDialogFragment fragment = new SelectDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date)getArguments().getSerializable(EXTRA_DATE_TIME);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_select, null);

        final RadioButton dateRadioButton =
                (RadioButton)v.findViewById(R.id.dialog_select_date_radioButton);
        dateRadioButton.setChecked(true);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_dialog_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dateRadioButton.isChecked()) {
                            // start DatePickerFragment
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            DatePickerFragment datePickerFragment =
                                    DatePickerFragment.newInstance(mDate);
                            datePickerFragment.setTargetFragment(
                                    SelectDialogFragment.this.getTargetFragment(),
                                    CrimeFragment.REQUEST_DATE
                            );
                            datePickerFragment.show(fm, DIALOG_DATE);
                        } else {
                            // start TimePickerFragment
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            TimePickerFragment timePickerFragment =
                                    TimePickerFragment.newInstance(mDate);
                            timePickerFragment.setTargetFragment(
                                    SelectDialogFragment.this.getTargetFragment(),
                                    CrimeFragment.REQUEST_TIME
                            );
                            timePickerFragment.show(fm, DIALOG_TIME);
                        }
                    }
                })
                .create();
    }
}

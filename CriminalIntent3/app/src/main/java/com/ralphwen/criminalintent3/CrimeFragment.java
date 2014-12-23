package com.ralphwen.criminalintent3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Yadong on 12/21/2014.
 * ralph.wen@gmail.com
 * Project: CriminalIntent3
 */
public class CrimeFragment extends Fragment {

    //intent extras
    public static final String EXTRA_CRIME_ID =
            "com.ralphwen.criminalintent3.crime_id";

    //DatePickerDialog identifier
    private static final String DIALOG_SELECT = "select";

    //request codes
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    public static final int REQUEST_SELECT = 2;

    private Crime mCrime;

    private TextView mTitleField;
    private Button mDateTimeButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField = (TextView)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateTimeButton = (Button) v.findViewById(R.id.crime_date_time);
        mDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectDialogFragment dialog = SelectDialogFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_SELECT);
                dialog.show(fm, DIALOG_SELECT);
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        updateDateTime();

        return v;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_DATE:
                Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(date);
                updateDateTime();
                break;
            case REQUEST_TIME:
                Date time = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mCrime.setDate(time);
                updateDateTime();
                break;
            case REQUEST_SELECT:
                return;
        }
    }

    public void updateDateTime() {
        mDateTimeButton.setText(DateFormat.format("EEEE, MMM dd, yyyy", mCrime.getDate()));
        mDateTimeButton.setText(DateFormat.format("hh:mm a, ", mCrime.getDate())
                .toString().toUpperCase()
                + DateFormat.format("EEEE, MMM dd, yyyy", mCrime.getDate()));
    }
}

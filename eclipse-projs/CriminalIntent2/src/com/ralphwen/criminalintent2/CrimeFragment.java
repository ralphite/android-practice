package com.ralphwen.criminalintent2;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

public class CrimeFragment extends Fragment {

	// log tag
	private static final String TAG = "CrimeFragment";

	public static final String EXTRA_CRIME_ID = "com.ralphwen.criminalintent2.crime_id";

	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_IMAGE = "image";

	// request code constants
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_PHOTO = 1;
	private static final int REQUEST_CONTACT = 2;

	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	private ImageButton mPhotoButton;
	private ImageView mPhotoImageView;
	private Button mSuspectButton;

	private Callbacks mCallbacks;

	public interface Callbacks {
		void onCrimeUpdated(Crime crime);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);

		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		setHasOptionsMenu(true);
	}

	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null)
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		mTitleField = (EditText) v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				mCallbacks.onCrimeUpdated(mCrime);
				mCrime.setTitle(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});

		mDateButton = (Button) v.findViewById(R.id.crime_date);
		updateDate();
		mDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});

		mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						mCallbacks.onCrimeUpdated(mCrime);
						mCrime.setSolved(isChecked);
					}
				});

		mPhotoButton = (ImageButton) v.findViewById(R.id.crime_imageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				startActivityForResult(i, REQUEST_PHOTO);
			}
		});

		PackageManager pm = getActivity().getPackageManager();
		boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
				|| pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
				|| Camera.getNumberOfCameras() > 0;

		if (!hasACamera)
			mPhotoButton.setEnabled(false);

		mPhotoImageView = (ImageView) v.findViewById(R.id.crime_imageView);
		mPhotoImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Photo photo = mCrime.getPhoto();
				if (photo == null)
					return;

				FragmentManager fm = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(
						photo.getFilename()).getAbsolutePath();
				ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
			}
		});

		Button reportButton = (Button) v.findViewById(R.id.crime_reportButton);
		reportButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
				intent.putExtra(Intent.EXTRA_SUBJECT,
						getString(R.string.crime_report_subject));
				intent = Intent.createChooser(intent,
						getString(R.string.send_report));

				startActivity(intent);
			}
		});

		mSuspectButton = (Button) v.findViewById(R.id.crime_suspectButton);
		mSuspectButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, REQUEST_CONTACT);
			}
		});

		if (mCrime.getSuspect() != null)
			mSuspectButton.setText(mCrime.getSuspect());

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			mCallbacks.onCrimeUpdated(mCrime);
			updateDate();
		} else if (requestCode == REQUEST_PHOTO) {
			String filename = data
					.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);

			if (filename != null) {
				Photo photo = new Photo(filename);
				mCrime.setPhoto(photo);
				mCallbacks.onCrimeUpdated(mCrime);
				showPhoto();
			}
		} else if (requestCode == REQUEST_CONTACT) {
			Uri contactUri = data.getData();

			String[] queryFieldStrings = new String[] { ContactsContract.Contacts.DISPLAY_NAME };

			Cursor cursor = getActivity().getContentResolver().query(
					contactUri, queryFieldStrings, null, null, null);

			if (cursor.getCount() == 0) {
				cursor.close();
				return;
			}

			cursor.moveToFirst();
			String suspect = cursor.getString(0);
			mCrime.setSuspect(suspect);
			mCallbacks.onCrimeUpdated(mCrime);
			mSuspectButton.setText(suspect);

			cursor.close();
		}
	}

	private void updateDate() {
		mDateButton.setText(mCrime.getDate().toString());
	}

	private void showPhoto() {
		Photo photo = mCrime.getPhoto();
		BitmapDrawable bitmapDrawable = null;

		if (photo != null) {
			String pathString = getActivity().getFileStreamPath(
					photo.getFilename()).getAbsolutePath();
			bitmapDrawable = PictureUtils.getScaledDrawable(getActivity(),
					pathString);
		}

		mPhotoImageView.setImageDrawable(bitmapDrawable);
	}

	private String getCrimeReport() {
		String solvedString = null;
		if (mCrime.isSolved()) {
			solvedString = getString(R.string.crime_report_solved);
		} else {
			solvedString = getString(R.string.crime_report_unsolved);
		}

		String dateString = DateFormat.format("EEE,  MMM dd", mCrime.getDate())
				.toString();

		String suspect = mCrime.getSuspect();
		if (suspect == null) {
			suspect = getString(R.string.crime_report_no_suspect);
		} else {
			suspect = getString(R.string.crime_report_suspect, suspect);
		}

		String report = getString(R.string.crime_report, mCrime.getTitle(),
				dateString, solvedString, suspect);

		return report;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
	}

	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}

	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoImageView);
	}
}

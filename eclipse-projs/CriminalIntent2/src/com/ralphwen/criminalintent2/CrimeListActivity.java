package com.ralphwen.criminalintent2;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CrimeListActivity extends SingleFragmentActivity implements
		CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}

	@Override
	public void onCrimeSelected(Crime crime) {
		// TODO Auto-generated method stub
		if (findViewById(R.id.detailFragmentContainer) == null) {
			Intent i = new Intent(this, CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());

			startActivity(i);
		} else {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			Fragment oldDetail = fragmentManager
					.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = CrimeFragment.newInstance(crime.getId());

			if (oldDetail != null)
				fragmentTransaction.remove(oldDetail);

			fragmentTransaction.add(R.id.detailFragmentContainer, newDetail);

			fragmentTransaction.commit();
		}
	}

	@Override
	public void onCrimeUpdated(Crime crime) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		CrimeListFragment fragment = (CrimeListFragment) fm
				.findFragmentById(R.id.fragmentContainer);

		fragment.updateUI();
	}

}

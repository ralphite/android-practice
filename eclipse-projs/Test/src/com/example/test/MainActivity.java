package com.example.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new Fragment() {

				@Override
				public View onCreateView(LayoutInflater inflater,
						ViewGroup container, Bundle savedInstanceState) {
					super.onCreateView(inflater, container, savedInstanceState);
					View view = inflater.inflate(R.layout.fragment_test,
							container, false);

					return view;
				}
			};
			fm.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}
}

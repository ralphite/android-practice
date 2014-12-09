package com.ralphwen.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {

	private AudioPlayer mPlayer = new AudioPlayer();

	private Button mPlayButton;
	private Button mStopButton;
	private Button mPauseResumeButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);

		mPlayButton = (Button) v.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPlayer.play(getActivity());
			}
		});

		mStopButton = (Button) v.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPlayer.stop();
			}
		});

		mPauseResumeButton = (Button) v
				.findViewById(R.id.hellomoon_pauseResumeButton);
		if (mPlayer.isPaused) {
			mPauseResumeButton.setText(R.string.hellomoon_resume);
		} else {
			mPauseResumeButton.setText(R.string.hellomoon_pause);
		}
		mPauseResumeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mPlayer.isStarted)
					return;
				if (mPlayer.isPaused) {
					mPlayer.resume();
					mPauseResumeButton.setText(R.string.hellomoon_pause);
				} else {
					mPlayer.pause();
					mPauseResumeButton.setText(R.string.hellomoon_resume);
				}
			}
		});

		return v;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mPlayer.stop();
	}
}

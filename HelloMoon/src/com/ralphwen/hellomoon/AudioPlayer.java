package com.ralphwen.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
	private MediaPlayer mPlayer;
	public boolean isPaused = false;
	public boolean isStarted = false;

	public void stop() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
			isPaused = false;
			isStarted = false;
		}
	}
	
	public void pause() {
		if(isStarted) {
			mPlayer.pause();
			isPaused = true;
		}
	}
	
	public void resume() {
		if(isPaused) {
			mPlayer.start();
			isPaused = false;
		}
	}

	public void play(Context c) {
		stop();
		
		mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
		
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				stop();
			}
		});
		
		mPlayer.start();
		isStarted = true;
	}
}

package com.ralphwen.hellomoon2;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Yadong on 12/23/2014.
 * ralph.wen@gmail.com
 * Project: HelloMoon2
 */
public class AudioPlayer {

    private MediaPlayer mPlayer;
    private boolean isPaused = false;

    public void stop() {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            isPaused = false;
        }
    }

    public void play(Context c) {
        if (isPaused) {
            isPaused = false;
            mPlayer.start();
        } else {
            stop();

            mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });

            mPlayer.start();
        }
    }

    public void pause(Context c) {
        if (mPlayer == null || isPaused)
            return;
        mPlayer.pause();
        isPaused = true;
    }
}

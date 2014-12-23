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

    public void stop() {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void play(Context c) {
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

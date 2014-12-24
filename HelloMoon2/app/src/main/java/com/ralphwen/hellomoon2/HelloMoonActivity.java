package com.ralphwen.hellomoon2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import java.io.IOException;


public class HelloMoonActivity extends ActionBarActivity {

    public static final String TAG = "HelloMoonActivity";

    private AudioPlayer mAudioPlayer;
    private MediaPlayer mMediaPlayer, holdOn;
    private SurfaceView mSurfaceView;
    private Uri mUri = Uri.parse("android.resource://" +
            "com.ralphwen.hellomoon2/raw/apollo_17_stroll");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_moon);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec audioSpec = tabHost.newTabSpec("play_audio");
        audioSpec.setIndicator("Audio");
        audioSpec.setContent(R.id.audio);
        tabHost.addTab(audioSpec);

        TabHost.TabSpec videoSpec = tabHost.newTabSpec("play_video");
        videoSpec.setIndicator("Video");
        videoSpec.setContent(R.id.video);
        tabHost.addTab(videoSpec);

        mAudioPlayer = new AudioPlayer();

        Button mAudioPlayButton = (Button)findViewById(R.id.audio_playButton);
        mAudioPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioPlayer.play(getApplication());
            }
        });

        Button mAudioPauseButton = (Button) findViewById(R.id.audio_pauseButton);
        mAudioPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioPlayer.pause(getApplication());
            }
        });

        Button mAudioStopButton = (Button) findViewById(R.id.audio_stopButton);
        mAudioStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioPlayer.stop();
            }
        });


        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.apollo_17_stroll);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holdOn = mp;
            }
        });

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.setDisplay(holder);
                try {
                    //mMediaPlayer.setDataSource(getApplicationContext(), mUri);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException ioe) {
                    Log.e(TAG, "Error opening video file.", ioe);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mMediaPlayer.release();
            }
        });


    }
}

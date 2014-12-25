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
import android.widget.VideoView;

import java.io.IOException;


public class HelloMoonActivity extends ActionBarActivity {

    public static final String TAG = "HelloMoonActivity";

    private AudioPlayer mAudioPlayer;
    private VideoView mVideoView;
    private Uri mUri = Uri.parse("android.resource://" +
            "com.ralphwen.hellomoon2/raw/apollo.17.stroll");

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


        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setVideoURI(mUri);
    }
}

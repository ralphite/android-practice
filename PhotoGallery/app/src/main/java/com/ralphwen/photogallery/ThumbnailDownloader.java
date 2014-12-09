package com.ralphwen.photogallery;

import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by Yadong on 12/10/2014.
 */
public class ThumbnailDownloader<Token> extends HandlerThread {

    //log tag
    private static final String TAG = "ThumbnailDownloader";

    public ThumbnailDownloader() {
        super(TAG);
    }

    public void queueThumbnail(Token token, String url) {
        Log.i(TAG, "Got an URL: " + url);
    }
}

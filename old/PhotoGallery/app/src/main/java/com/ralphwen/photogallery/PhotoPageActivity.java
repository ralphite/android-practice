package com.ralphwen.photogallery;

import android.support.v4.app.Fragment;

/**
 * Created by Yadong on 12/17/2014.
 * ralph.wen@gmail.com
 * Project: PhotoGallery
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new PhotoPageFragment();
    }
}

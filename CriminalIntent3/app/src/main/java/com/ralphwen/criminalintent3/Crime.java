package com.ralphwen.criminalintent3;

import java.util.UUID;

/**
 * Created by Yadong on 12/21/2014.
 * ralph.wen@gmail.com
 * Project: CriminalIntent3
 */
public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}

package com.soviet_wave.youtube_downloader;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class Material extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}

package com.github.bubbleview.sample;

import android.app.Application;

/**
 * Created by lgp on 2015/4/25.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}

package com.groupseven.voicestamp.mainlist.activity;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.groupseven.voicestamp.tools.AppGlobal;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppGlobal.getInstance().setApplicationContext(this);
        Stetho.initializeWithDefaults(this);
    }
}

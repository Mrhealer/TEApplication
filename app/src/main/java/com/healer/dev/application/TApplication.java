package com.healer.dev.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.healer.dev.R;

public class TApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getApplicationContext();
        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    public static Context getAppContext(){
        return sContext;
    }

}

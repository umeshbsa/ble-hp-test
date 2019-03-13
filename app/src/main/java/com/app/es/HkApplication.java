package com.app.es;

import android.app.Application;
import android.content.Context;

public class HkApplication extends Application {
    private static final String TAG = "HkApplication";
    public static HkApplication application;

    public void onCreate() {
        super.onCreate();
        application = this;

    }


    public static boolean isEn(Context context) {
        return true;
    }

}

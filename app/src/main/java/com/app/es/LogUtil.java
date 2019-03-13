package com.app.es;

import android.util.Log;

public class LogUtil {
    /* renamed from: i */
    public static void m294i(String TAG, String methodName, String msg) {
        Log.i(new StringBuilder(String.valueOf(TAG)).append("-->").toString(), new StringBuilder(String.valueOf(methodName)).append("-->").append(msg).toString());
    }

    /* renamed from: d */
    public static void m292d(String TAG, String methodName, String msg) {
        Log.d(new StringBuilder(String.valueOf(TAG)).append("-->").toString(), new StringBuilder(String.valueOf(methodName)).append("-->").append(msg).toString());
    }

    /* renamed from: w */
    public static void m296w(String TAG, String methodName, String msg) {
        Log.w(new StringBuilder(String.valueOf(TAG)).append("-->").toString(), new StringBuilder(String.valueOf(methodName)).append("-->").append(msg).toString());
    }

    /* renamed from: e */
    public static void m293e(String TAG, String methodName, String msg) {
        Log.e("TTTTTTTTTT", new StringBuilder(String.valueOf(methodName)).append("-->").append(msg).toString());
    }

    /* renamed from: v */
    public static void m295v(String TAG, String methodName, String msg) {
        Log.v("TTTTTTTTTT", new StringBuilder(String.valueOf(methodName)).append("-->").append(msg).toString());
    }
}

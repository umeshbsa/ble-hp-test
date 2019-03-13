package com.app.es;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class PreferenceUtil {
    public static final String AUTO_CHECK_UPDATE = "auto_check_update";
    public static final String IS_AUTO_LOGIN = "is_auto_login";
    public static final String IS_FIRST_USE = "is_first_use";
    public static final String IS_REMEMBER_PASSWORD = "is_remember_password";
    public static final String IS_SAFE_LOCK = "is_safe_lock";
    public static final String IS_SPEACH = "is_speach";
    public static final String KEY_BG_PATH = "key_background_path";
    public static final String KEY_DEVICE_SCALE = "key_device_scale";
    public static final String KEY_DEVICE_TREADMILL = "key_device_treadmill";
    public static final String KEY_MAX_SLOPE = "key_max_slope";
    public static final String KEY_MAX_SPEED = "key_max_speed";
    public static final String KEY_MIN_SPEED = "key_min_speed";
    public static final String KEY_SAFE_LOCK_PWD = "key_safe_lock_pwd";
    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_UNIT = "key_unit";
    public static final String MESSAGE_NOTIFY = "message_notify";
    public static final String PREFERENCES_NAME = "com.hk.bletreadmill.preferences";
    public static final String USER_ACCOUNT = "user_account";
    public static final String USER_PASSWORD = "user_password";

    public static void setString(Context context, String key, String value) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static String getString(Context context, String key, String defValue) {
        return context == null ? defValue : context.getSharedPreferences(PREFERENCES_NAME, 0).getString(key, defValue);
    }

    public static void setInt(Context context, String key, int value) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public static int getInt(Context context, String key, int defValue) {
        return context == null ? defValue : context.getSharedPreferences(PREFERENCES_NAME, 0).getInt(key, defValue);
    }

    public static void delString(Context context, String key) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
            editor.remove(key);
            editor.commit();
        }
    }

    public static void setLong(Context context, String key, Long value) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
            editor.putLong(key, value.longValue());
            editor.commit();
        }
    }

    public static Long getLong(Context context, String key, long def) {
        if (context == null) {
            return Long.valueOf(def);
        }
        return Long.valueOf(context.getSharedPreferences(PREFERENCES_NAME, 0).getLong(key, def));
    }

    public static void setBoolean(Context context, String key, boolean value) {
        if (context != null) {
            Editor editor = context.getSharedPreferences(PREFERENCES_NAME, 0).edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context == null ? defaultValue : context.getSharedPreferences(PREFERENCES_NAME, 0).getBoolean(key, defaultValue);
    }
}

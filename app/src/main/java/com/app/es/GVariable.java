package com.app.es;

import android.app.Activity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GVariable {
    public static int MAX_GRADIENT = 15;
    public static int MAX_SPEED = 150;
    public static int MIN_SPEED = 10;
    public static ArrayList<Activity> activityList = new ArrayList();
    public static BluetoothTreadmillService bluetoothLeService = null;
    public static int calorie = 0;
    public static boolean change = false;
    public static int commandIndex = 0;
    public static int curPageIndex = 0;
    public static MemberInfo currentMember;
    private static DecimalFormat decimalFormat = new DecimalFormat("0.0");
    public static float distance = 0.0f;
    public static int faultCode = 0;
    public static int gradient = 0;
    public static int heartRate = 0;
    public static boolean isConnected = false;
    public static boolean isInPlayer = true;
    public static boolean isReceiveSpeed = false;
    public static boolean isStart = false;
    public static boolean isUpload = false;
    public static ArrayList<MemberInfo> memberList = new ArrayList();
    public static int speed = 10;
    public static String treadmillDevice = "";

    public static String getSpeed() {
        return decimalFormat.format((double) (((float) speed) / 10.0f));
    }

    public static int getCalorie() {
        return calorie / 1000;
    }
}

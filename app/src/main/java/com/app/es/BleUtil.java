package com.app.es;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class BleUtil {
    public static final String ACTION_BACK_TO_HOME = "com.hankang.back.to.home";
    public static final String ACTION_RECEIVE_SLOPE = "com.hankang.receive.slope";
    public static final String ACTION_RECEIVE_SPEED = "com.hankang.receive.speed";
    public static final String ACTION_START_SWITCH = "com.hankang.treadmill.start.switch";
    public static final String ACTION_TREADMILL_CONNECTED = "com.hankang.treadmill.connected";
    public static final String ACTION_TREADMILL_DISCONNECTED = "com.hankang.treadmill.disconnected";
    private static final String TAG = "BleUtil";

    public static void checkCommand(Context context, byte[] data) {

        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                Log.i("DDDDDDDDDDDDDD", "BLE Utils : Data " + data[i]);

            }
        }

        if (data == null || data.length != 3) {
            if (data != null && data.length == 4 && data[0] == (byte) 9 && data[1] == (byte) 2) {
                GVariable.MIN_SPEED = data[2];
                GVariable.MAX_SPEED = data[3];
                if (data[3] < (byte) 0) {
                    GVariable.MAX_SPEED = data[3] + 256;
                }

                GVariable.isReceiveSpeed = true;

            }
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 1) {


            Log.i("DDDDDDDDDDDDDD", "BLE Utils : Start Device  ");

            GVariable.isStart = true;

            context.sendBroadcast(new Intent(ACTION_START_SWITCH));

        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 2) {


            GVariable.isStart = false;

            context.sendBroadcast(new Intent(ACTION_START_SWITCH));

        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 3) {

            if (GVariable.speed < GVariable.MAX_SPEED) {
                GVariable.speed++;

            }

            context.sendBroadcast(new Intent(ACTION_RECEIVE_SPEED));

        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 4) {

            if (GVariable.speed > GVariable.MIN_SPEED) {
                GVariable.speed--;
            }

            context.sendBroadcast(new Intent(ACTION_RECEIVE_SPEED));

        } else if (data[0] == (byte) -32 && data[1] == (byte) 1) {

            GVariable.speed = data[2];
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SPEED));

        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 5) {
            if (GVariable.gradient < GVariable.MAX_GRADIENT) {
                GVariable.gradient++;
            }
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SLOPE));
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 6) {
            if (GVariable.gradient > 0) {
                GVariable.gradient--;
            }
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SLOPE));
        } else if (data[0] == (byte) -31 && data[1] == (byte) 1) {
            GVariable.gradient = data[2];
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SLOPE));
        } else if (data[0] == (byte) 2 && data[1] == (byte) 1) {
            GVariable.heartRate = data[2];
        } else if (data[0] == (byte) 9 && data[1] == (byte) 1) {
            GVariable.MAX_GRADIENT = data[2];
            PreferenceUtil.setInt(context, PreferenceUtil.KEY_MAX_SLOPE, GVariable.MAX_GRADIENT);
        } else if (data[0] == (byte) 3 && data[1] == (byte) 1) {
            GVariable.faultCode = data[2];
            if (GVariable.bluetoothLeService != null && GVariable.isStart) {
                GVariable.isStart = false;
                context.sendBroadcast(new Intent(ACTION_START_SWITCH));
                GVariable.bluetoothLeService.disconnect();
                GVariable.bluetoothLeService = null;
            }
        }
    }

    public static void countDistance() {
        GVariable.distance += ((float) GVariable.speed) / 36.0f;
    }

    public static void countCalorie() {
        if (GVariable.currentMember == null || TextUtils.isEmpty(GVariable.currentMember.getWeight())) {
            GVariable.calorie = (int) (((double) GVariable.calorie) + CalorieUtils.calculateCalorieBySecond(60.0f, 1.0f, ((float) GVariable.speed) / 10.0f, GVariable.gradient).doubleValue());
            return;
        }
        GVariable.calorie = (int) (((double) GVariable.calorie) + CalorieUtils.calculateCalorieBySecond(Float.parseFloat(GVariable.currentMember.getWeight()), 1.0f, ((float) GVariable.speed) / 10.0f, GVariable.gradient).doubleValue());
    }

}

package com.app.es;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.Arrays;

public class BleUtil {
    public static final String ACTION_BACK_TO_HOME = "com.hankang.back.to.home";
    public static final String ACTION_RECEIVE_SLOPE = "com.hankang.receive.slope";
    public static final String ACTION_RECEIVE_SPEED = "com.hankang.receive.speed";
    public static final String ACTION_START_SWITCH = "com.hankang.treadmill.start.switch";
    public static final String ACTION_TREADMILL_CONNECTED = "com.hankang.treadmill.connected";
    public static final String ACTION_TREADMILL_DISCONNECTED = "com.hankang.treadmill.disconnected";
    private static final String TAG = "BleUtil";

    public static void checkCommand(Context context, byte[] data) {
        if (data == null || data.length != 3) {
            if (data != null && data.length == 4 && data[0] == (byte) 9 && data[1] == (byte) 2) {
                GVariable.MIN_SPEED = data[2];
                GVariable.MAX_SPEED = data[3];
                if (data[3] < (byte) 0) {
                    GVariable.MAX_SPEED = data[3] + 256;
                }
                GVariable.isReceiveSpeed = true;
                PreferenceUtil.setInt(context, PreferenceUtil.KEY_MIN_SPEED, GVariable.MIN_SPEED);
                PreferenceUtil.setInt(context, PreferenceUtil.KEY_MAX_SPEED, GVariable.MAX_SPEED);
                LogUtil.m295v(TAG, "checkCommand", "最小速度：" + data[2] + "---最大速度：" + data[3]);
            }
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 1) {
            GVariable.isStart = true;
            context.sendBroadcast(new Intent(ACTION_START_SWITCH));
            LogUtil.m295v(TAG, "checkCommand", "开始按键");
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 2) {
            GVariable.isStart = false;
            context.sendBroadcast(new Intent(ACTION_START_SWITCH));
            LogUtil.m295v(TAG, "checkCommand", "停止按键");
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 3) {
            if (GVariable.speed < GVariable.MAX_SPEED) {
                GVariable.speed++;
            }
            LogUtil.m295v(TAG, "checkCommand", "速度加1按键");
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SPEED));
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 4) {
            if (GVariable.speed > GVariable.MIN_SPEED) {
                GVariable.speed--;
            }
            LogUtil.m295v(TAG, "checkCommand", "速度减1按键");
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SPEED));
        } else if (data[0] == (byte) -32 && data[1] == (byte) 1) {
            GVariable.speed = data[2];
            LogUtil.m295v(TAG, "checkCommand", "速度到=" + data[2]);
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SPEED));
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 5) {
            if (GVariable.gradient < GVariable.MAX_GRADIENT) {
                GVariable.gradient++;
            }
            LogUtil.m295v(TAG, "checkCommand", "升降加1按键");
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SLOPE));
        } else if (data[0] == (byte) 5 && data[1] == (byte) 1 && data[2] == (byte) 6) {
            if (GVariable.gradient > 0) {
                GVariable.gradient--;
            }
            LogUtil.m295v(TAG, "checkCommand", "升降减1按键");
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SLOPE));
        } else if (data[0] == (byte) -31 && data[1] == (byte) 1) {
            GVariable.gradient = data[2];
            LogUtil.m295v(TAG, "checkCommand", "升降到=" + data[2]);
            context.sendBroadcast(new Intent(ACTION_RECEIVE_SLOPE));
        } else if (data[0] == (byte) 2 && data[1] == (byte) 1) {
            GVariable.heartRate = data[2];
        } else if (data[0] == (byte) 9 && data[1] == (byte) 1) {
            GVariable.MAX_GRADIENT = data[2];
            PreferenceUtil.setInt(context, PreferenceUtil.KEY_MAX_SLOPE, GVariable.MAX_GRADIENT);
            LogUtil.m294i(TAG, "checkCommand", "最大坡度：" + data[2]);
        } else if (data[0] == (byte) 3 && data[1] == (byte) 1) {
            GVariable.faultCode = data[2];
            if (GVariable.bluetoothLeService != null && GVariable.isStart) {
                LogUtil.m293e(TAG, "checkCommand", "故障码=" + Arrays.toString(data));
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

    public static boolean isChecd(int index, byte[] value) {
        LogUtil.m294i(TAG, "isChecd", "value=" + Arrays.toString(value));
        switch (index) {
            case 0:
                return zero(value);
            case 1:
                return one(value);
            case 2:
                return two(value);
            case 3:
                return three(value);
            case 4:
                return four(value);
            case 5:
                return five(value);
            default:
                return false;
        }
    }

    private static boolean zero(byte[] value) {
        if (value.length != 6) {
            return false;
        }
        int a = value[2];
        int b = value[3];
        int c = value[4];
        int d = value[5];
        if (c == a + b && d == a * b) {
            return true;
        }
        return false;
    }

    private static boolean one(byte[] value) {
        if (value.length != 6) {
            return false;
        }
        int a = value[2];
        int b = value[3];
        int c = value[4];
        int d = value[5];
        if (c == a * b && d == a + b) {
            return true;
        }
        return false;
    }

    private static boolean two(byte[] value) {
        if (value.length != 6) {
            return false;
        }
        int a = value[2];
        int b = value[3];
        int c = value[4];
        int d = value[5];
        if (a == b + c && d == b * c) {
            return true;
        }
        return false;
    }

    private static boolean three(byte[] value) {
        if (value.length != 6) {
            return false;
        }
        int a = value[2];
        int b = value[3];
        int c = value[4];
        int d = value[5];
        if (a == b * c && d == b + c) {
            return true;
        }
        return false;
    }

    private static boolean four(byte[] value) {
        if (value.length != 6) {
            return false;
        }
        int a = value[2];
        int b = value[3];
        int c = value[4];
        int d = value[5];
        if (a == c + d && b == c * d) {
            return true;
        }
        return false;
    }

    private static boolean five(byte[] value) {
        if (value.length != 6) {
            return false;
        }
        int a = value[2];
        int b = value[3];
        int c = value[4];
        int d = value[5];
        if (a == c * d && b == c + d) {
            return true;
        }
        return false;
    }
}

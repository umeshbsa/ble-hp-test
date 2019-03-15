package com.app.es;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.Timer;

/*

<action android:name="com.hankang.bluetooth.treadmill.ACTION_GATT_CONNECTED" />
<action android:name="com.hankang.bluetooth.treadmill.ACTION_GATT_DISCONNECTED" />
<action android:name="com.hankang.bluetooth.treadmill.ACTION_GATT_SERVICES_DISCOVERED" />
<action android:name="com.hankang.bluetooth.treadmill.ACTION_DATA_AVAILABLE" />
<action android:name="com.hankang.bluetooth.treadmill.EXTRA_DATA" />

*/

public class GattUpdateReceiver extends BroadcastReceiver {
    private static final String TAG = "GattUpdateReceiver";
    private boolean connecting = false;
    private Context mContext;

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.i("TTTTTTTTTT", "GattUpdateReceiver  " + action);

        this.mContext = context;
        if (BluetoothTreadmillService.ACTION_GATT_CONNECTED.equals(action)) {


            LogUtil.m295v(TAG, "onReceive", "ACTION_GATT_CONNECTED");

        } else if (BluetoothTreadmillService.ACTION_GATT_DISCONNECTED.equals(action)) {

            LogUtil.m293e(TAG, "onReceive", "ACTION_GATT_DISCONNECTED");

            GVariable.isConnected = false;
            GVariable.isStart = false;

            if (!(GVariable.bluetoothLeService == null || TextUtils.isEmpty(GVariable.treadmillDevice))) {
                GVariable.bluetoothLeService.connect(GVariable.treadmillDevice);
            }
            context.sendBroadcast(new Intent(BleUtil.ACTION_TREADMILL_DISCONNECTED));

        } else if (BluetoothTreadmillService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {

            LogUtil.m294i(TAG, "onReceive", "ACTION_GATT_SERVICES_DISCOVERED");
            this.woshouHandler.sendEmptyMessageDelayed(1, 300);

        } else if (BluetoothTreadmillService.ACTION_DATA_AVAILABLE.equals(action)) {

            byte[] data = intent.getByteArrayExtra(BluetoothTreadmillService.EXTRA_DATA);
            if (data != null) {
                if (data.length == 6 && data[0] == (byte) 8 && data[1] == (byte) 4 && data[2] == (byte) 1 && data[3] == (byte) 0 && data[4] == (byte) 0 && data[5] == (byte) 1) {
                    this.woshouHandler.removeMessages(1);
                    this.woshouHandler.sendEmptyMessageDelayed(2, 8);
                }
                if (data.length == 3) {
                    if (data[0] == (byte) 8 && data[1] == (byte) 1 && data[2] == (byte) -1) {

                        this.woshouHandler.sendEmptyMessage(1);

                    } else if (data[0] == (byte) 2 && data[1] == (byte) 1 && !GVariable.isConnected && !this.connecting) {

                        this.connecting = true;

                        this.woshouHandler.sendEmptyMessage(1);

                        new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        GattUpdateReceiver.this.connecting = false;
                                    }
                                }, 3000);

                    }
                }
                if (data.length == 5 && data[0] == (byte) 8 && data[1] == (byte) 3 && data[3] == ((byte) (data[2] ^ 245)) && data[4] == ((byte) (data[2] ^ 222))) {

                    this.woshouHandler.removeMessages(1);

                    Message msg = this.woshouHandler.obtainMessage(4);

                    msg.arg1 = data[4];

                    this.woshouHandler.sendMessageDelayed(msg, 8);
                }
                if (data.length == 4 && data[0] == (byte) 8 && data[1] == (byte) 2) {

                    this.woshouHandler.removeMessages(1);

                    this.woshouHandler.sendEmptyMessageDelayed(3, 50);
                }
            }
        }
    }

    Handler woshouHandler = new Handler() {

        public void handleMessage(Message msg) {

            if (msg.what == 1) {  //  for dis connection

                byte[] value = new byte[]{(byte) 8, (byte) 1, (byte) 1};
                LogUtil.m295v(GattUpdateReceiver.TAG, "woshouHandler/1", "value= " + Arrays.toString(value));
                if (GVariable.bluetoothLeService != null) {
                    GVariable.bluetoothLeService.WriteValue(value);
                }

            } else if (msg.what == 2) { //
                byte[] strvalue = new byte[6];
                strvalue[0] = (byte) 8;
                strvalue[1] = (byte) 4;
                strvalue[2] = (byte) 1;
                strvalue[5] = (byte) 1;
                GVariable.bluetoothLeService.WriteValue(strvalue);

                GattUpdateReceiver.this.woshouHandler.removeMessages(1);

                GattUpdateReceiver.this.woshouHandler.sendEmptyMessageDelayed(3, 1000);

                // for refresh data
                GattUpdateReceiver.this.mContext.sendBroadcast(new Intent(BleUtil.ACTION_TREADMILL_CONNECTED));

            } else if (msg.what == 3) {
                GattUpdateReceiver.this.woshouHandler.removeMessages(1);
                GVariable.isConnected = true;
                new Timer().scheduleAtFixedRate(new CommandTimerTask(), 10, 500);
                GattUpdateReceiver.this.mContext.sendBroadcast(new Intent(BleUtil.ACTION_TREADMILL_CONNECTED));
                GattUpdateReceiver.this.getSpeedSlopeHandler.sendEmptyMessageDelayed(4, 10);
            } else if (msg.what == 4) {
                GattUpdateReceiver.this.woshouHandler.removeMessages(1);
                byte y2 = (byte) msg.arg1;
                byte z1 = (byte) (y2 ^ 245);
                byte z2 = (byte) (y2 ^ 66);
                GVariable.bluetoothLeService.WriteValue(new byte[]{(byte) 8, (byte) 3, y2, z1, z2});
                GattUpdateReceiver.this.mContext.sendBroadcast(new Intent(BleUtil.ACTION_TREADMILL_CONNECTED));
            }
        }
    };

    Handler getSpeedSlopeHandler = new Handler() {

        public void handleMessage(Message msg) {
            if (!GVariable.isReceiveSpeed && GVariable.bluetoothLeService != null) {
                GVariable.bluetoothLeService.WriteValue(new byte[]{(byte) 9, (byte) 1, (byte) 1});
                GVariable.bluetoothLeService.WriteValue(new byte[]{(byte) 9, (byte) 1, (byte) 2});
                GattUpdateReceiver.this.getSpeedSlopeHandler.sendEmptyMessageDelayed(4, 100);
            }
        }
    };

}

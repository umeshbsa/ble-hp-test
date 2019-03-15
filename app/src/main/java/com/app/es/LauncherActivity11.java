package com.app.es;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.hankang.phone.treadmill.R;

public class LauncherActivity11 extends Activity {
    private static final String TAG = "LauncherActivity11";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GVariable.activityList.add(this);
        setContentView(R.layout.launcher_activity);

        initTreadmillBlueTooth();
    }


    private void initTreadmillBlueTooth() {
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            Toast.makeText(this, R.string.supportble, Toast.LENGTH_LONG).show();
        }
        scanBleDevice();
    }

    private void scanBleDevice() {

        new ScanTreadmillDevice(this, new ScanTreadmillDevice.SearchListener() {
            @Override
            public void setAddress(String address) {
                LogUtil.m296w(LauncherActivity11.TAG, "scanBleDevice", "连接蓝牙：" + address);
                GVariable.treadmillDevice = address;
                if (GVariable.bluetoothLeService != null) {
                    GVariable.bluetoothLeService.connect(GVariable.treadmillDevice);
                } else {
                    initBleService();
                }
            }

            @Override
            public void setStatus(String str) {

            }
        });

    }

    private void initBleService() {
        Log.w(TAG, "Try to bindService=" + bindService(new Intent(this, BluetoothTreadmillService.class), this.mServiceConnection, Context.BIND_AUTO_CREATE));
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            GVariable.bluetoothLeService = ((BluetoothTreadmillService.LocalBinder) service).getService();
            if (GVariable.bluetoothLeService.initialize()) {
                GVariable.bluetoothLeService.connect(GVariable.treadmillDevice);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        LauncherActivity11.this.startActivity(new Intent(LauncherActivity11.this, PlayActivity.class));


                    }
                }, 2000);
                return;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    protected void onDestroy() {
        GVariable.activityList.remove(this);
        super.onDestroy();
    }
}

package com.app.es;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.hankang.phone.treadmill.R;

public class LauncherActivity11 extends Activity implements OnClickListener {
    private static final String TAG = "LauncherActivity11";
    private Handler handler;
    private BTBroadcastReceiver mBTReceiver;
    private BluetoothAdapter mBluetoothAdapter;
    // private Runnable runnableer;

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


    /* renamed from: com.hankang.phone.treadmill.activity.LauncherActivity11$5 */
    class C01605 implements Runnable {
        C01605() {
        }

        public void run() {
            LauncherActivity11.this.mBluetoothAdapter.enable();
        }
    }

    private class BTBroadcastReceiver extends BroadcastReceiver {
        private BTBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                return;
            }
            if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1) == 12) {
                LogUtil.m293e(LauncherActivity11.TAG, "BTBroadcastReceiver", "蓝牙打开");
                String device = PreferenceUtil.getString(LauncherActivity11.this, PreferenceUtil.KEY_DEVICE_TREADMILL, "");
                if (!TextUtils.isEmpty(device)) {
                    GVariable.treadmillDevice = device;
                }
                if (TextUtils.isEmpty(GVariable.treadmillDevice)) {
                    LauncherActivity11.this.scanBleDevice();
                } else if (GVariable.bluetoothLeService != null) {
                    GVariable.bluetoothLeService.connect(GVariable.treadmillDevice);
                } else {
                    LauncherActivity11.this.initBleService();
                }
            } else if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1) == 10) {
                LogUtil.m293e(LauncherActivity11.TAG, "BTBroadcastReceiver", "蓝牙关闭");
                GVariable.isConnected = false;
                GVariable.isStart = false;
                context.sendBroadcast(new Intent(BleUtil.ACTION_TREADMILL_DISCONNECTED));
            }
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GVariable.activityList.add(this);
        setContentView(R.layout.launcher_activity);
        final ImageView imageView = (ImageView) findViewById(R.id.launcher_image);
        imageView.setImageResource(R.drawable.launcher_image1);
        ((ImageView) findViewById(R.id.launcher_imagee)).setOnClickListener(this);
        this.handler = new Handler();

        // this.runnableer = new C01583();

        //  this.handler.postDelayed(this.runnableer, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LauncherActivity11.this.initTreadmillBlueTooth();
                LauncherActivity11.this.registerBTReceiver();
            }
        }, 1000);
    }

    protected void onDestroy() {
        //   this.handler.removeCallbacks(this.runnableer);

        GVariable.activityList.remove(this);

        unregisterBTReceiver();

        super.onDestroy();
    }

    private void registerBTReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mBTReceiver = new BTBroadcastReceiver();
        getApplicationContext().registerReceiver(this.mBTReceiver, filter);
    }

    private void unregisterBTReceiver() {
        if (this.mBTReceiver != null) {
            getApplication().unregisterReceiver(this.mBTReceiver);
        }
    }

    private void initTreadmillBlueTooth() {
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            Toast.makeText(this, R.string.supportble, Toast.LENGTH_LONG).show();
            finish();
        }


        this.mBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
            String device = PreferenceUtil.getString(this, PreferenceUtil.KEY_DEVICE_TREADMILL, "");
            LogUtil.m296w(TAG, "initTreadmillBlueTooth", "device=" + device);
            if (TextUtils.isEmpty(device)) {
                scanBleDevice();
                return;
            }
            GVariable.treadmillDevice = device;
            initBleService();
        new Handler().post(new C01605());
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

    public void onClick(View v) {
        switch (v.getId()) {


        }
    }
}

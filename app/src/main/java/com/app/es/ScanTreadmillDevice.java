package com.app.es;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.hankang.phone.treadmill.R;

public class ScanTreadmillDevice {
    private static final long SCAN_PERIOD = 20000;
    private static boolean mScanning = false;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice = null;
    private LeScanCallback mLeScanCallback = new C02521();
    private SearchListener mSearchListener;

    /* renamed from: com.hankang.phone.treadmill.service.ScanTreadmillDevice$1 */
    class C02521 implements LeScanCallback {
        C02521() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            String name = device.getName();
            if (name != null && name.contains("ESLinker")) {
                Log.i("TAG", "name=" + name);
                ScanTreadmillDevice.this.scanLeDevice(false);
                ScanTreadmillDevice.this.mBluetoothDevice = device;
                if (ScanTreadmillDevice.this.mSearchListener != null) {
                    Log.i("TAG", "Address:::::=" + device.getAddress());
                    ScanTreadmillDevice.this.mSearchListener.setAddress(device.getAddress());
                }
            }
        }
    }

    /* renamed from: com.hankang.phone.treadmill.service.ScanTreadmillDevice$2 */
    class C02532 implements Runnable {
        C02532() {
        }

        public void run() {
            ScanTreadmillDevice.this.scanLeDevice(true);
        }
    }

    /* renamed from: com.hankang.phone.treadmill.service.ScanTreadmillDevice$3 */
    class C02543 implements Runnable {
        C02543() {
        }

        public void run() {
            if (ScanTreadmillDevice.mScanning) {
                ScanTreadmillDevice.mScanning = false;
                ScanTreadmillDevice.this.mBluetoothAdapter.stopLeScan(ScanTreadmillDevice.this.mLeScanCallback);
                if (ScanTreadmillDevice.this.mBluetoothDevice == null) {
                    ScanTreadmillDevice.this.scanLeDevice(true);
                }
            }
        }
    }

    public interface SearchListener {
        void setAddress(String str);

        void setStatus(String str);
    }

    public ScanTreadmillDevice(Context context, SearchListener searchListener) {
        this.mSearchListener = searchListener;
        this.mBluetoothAdapter = ((BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        if (this.mBluetoothAdapter == null) {
            Toast.makeText(context, R.string.error_bluetooth_not_supported, 0).show();
        } else if (this.mBluetoothAdapter.getState() == 12) {
            new Handler().post(new C02532());
        }
    }

    private void scanLeDevice(boolean enable) {
        if (enable) {
            new Handler().postDelayed(new C02543(), SCAN_PERIOD);
            mScanning = true;
            if (this.mSearchListener != null) {
                this.mSearchListener.setStatus("searching...");
            }
            this.mBluetoothDevice = null;
            this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
            return;
        }
        mScanning = false;
        this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
    }

    public static void stopScan() {
        mScanning = false;
    }
}

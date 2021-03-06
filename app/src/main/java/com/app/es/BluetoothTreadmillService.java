package com.app.es;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BluetoothTreadmillService extends Service {
    public static final String ACTION_DATA_AVAILABLE = "com.hankang.bluetooth.treadmill.ACTION_DATA_AVAILABLE";
    public static final String ACTION_GATT_CONNECTED = "com.hankang.bluetooth.treadmill.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "com.hankang.bluetooth.treadmill.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.hankang.bluetooth.treadmill.ACTION_GATT_SERVICES_DISCOVERED";
    private static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final String EXTRA_DATA = "com.hankang.bluetooth.treadmill.EXTRA_DATA";
    private static final String TAG = BluetoothTreadmillService.class.getSimpleName();
    private static final UUID UUID_ISSC_CHAR_5TX = UUID.fromString("0000fff5-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_ISSC_CHAR_RX = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_ISSC_CHAR_TX = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");
    private int ble_rssi = 0;
    private final IBinder mBinder = new LocalBinder();
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    public BluetoothGattCharacteristic mNotifyCharacteristic;
    public BluetoothGattCharacteristic mWriteCharacteristic;

    /* renamed from: com.hankang.phone.treadmill.service.BluetoothTreadmillService$1 */
    BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {


        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == 2) {

                boolean isDiscovered = BluetoothTreadmillService.this.mBluetoothGatt.discoverServices();

                String intentAction = BluetoothTreadmillService.ACTION_GATT_CONNECTED;

                Log.i("DDDDDDDDDDDD", "Is Discovered : "+isDiscovered);

                LogUtil.m294i(BluetoothTreadmillService.TAG, "mGattCallback", "discoverServices= " + isDiscovered);

                BluetoothTreadmillService.this.broadcastUpdate(intentAction);

            } else if (newState == 0) {
                LogUtil.m294i(BluetoothTreadmillService.TAG, "mGattCallback", "ACTION_GATT_DISCONNECTED");
                GVariable.isConnected = false;
                GVariable.isStart = false;
                BluetoothTreadmillService.this.broadcastUpdate(BluetoothTreadmillService.ACTION_GATT_DISCONNECTED);
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            LogUtil.m296w(BluetoothTreadmillService.TAG, "onServicesDiscovered", "status=" + status);
            if (status == 0) {

                Log.i("DDDDDDDDDDDD", " onServicesDiscovered ");
                BluetoothTreadmillService.this.findService();
            } else if (BluetoothTreadmillService.this.mBluetoothGatt.getDevice().getUuids() == null) {
                LogUtil.m296w(BluetoothTreadmillService.TAG, "onServicesDiscovered", "mBluetoothGatt.getDevice().getUuids() == null");
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtil.m296w(BluetoothTreadmillService.TAG, "onCharacteristicRead", "characteristic=" + Arrays.toString(characteristic.getValue()));
            broadcastData(BluetoothTreadmillService.ACTION_DATA_AVAILABLE, characteristic.getValue());
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            broadcastData(BluetoothTreadmillService.ACTION_DATA_AVAILABLE, data);
            BleUtil.checkCommand(BluetoothTreadmillService.this, data);
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
            Log.e(BluetoothTreadmillService.TAG, "onDescriptorRead");
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
            Log.e(BluetoothTreadmillService.TAG, "onDescriptorWrite");
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            BluetoothTreadmillService.this.ble_rssi = rssi;
            LogUtil.m294i(BluetoothTreadmillService.TAG, "onReadRemoteRssi", "ble_rssi=" + BluetoothTreadmillService.this.ble_rssi);
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int a) {
            LogUtil.m293e(BluetoothTreadmillService.TAG, "onReliableWriteCompleted", "a=" + a);
        }
    };

    public class LocalBinder extends Binder {
        public BluetoothTreadmillService getService() {
            return BluetoothTreadmillService.this;
        }
    }

    public boolean WriteValue(byte[] value) {
        BluetoothGattCharacteristic characteristic = this.mWriteCharacteristic;
        if (this.mWriteCharacteristic == null) {
            LogUtil.m296w(TAG, "WriteValue", "mWriteCharacteristic == null");
            GVariable.isConnected = false;
            GVariable.isStart = false;
            return false;
        } else if (this.mBluetoothGatt == null) {
            LogUtil.m296w(TAG, "WriteValue", "mBluetoothGatt == null");
            GVariable.isConnected = false;
            GVariable.isStart = false;
            return false;
        } else {
            if ((characteristic.getProperties() | 4) > 0) {
                characteristic.setWriteType(1);
            } else {
                characteristic.setWriteType(2);
            }
            characteristic.setValue(value);
            boolean write = this.mBluetoothGatt.writeCharacteristic(characteristic);
            LogUtil.m294i(TAG, "WriteValue", "write=" + write);
            return write;
        }
    }

    public void findService() {
        if (this.mBluetoothGatt == null) {
            LogUtil.m292d(TAG, "findService", "mBluetoothGatt==null");
            return;
        }
        boolean findTX = false;
        List<BluetoothGattService> gattServices = this.mBluetoothGatt.getServices();
        for (int i = 0; i < gattServices.size(); i++) {
            List<BluetoothGattCharacteristic> gattCharacteristics = ((BluetoothGattService) gattServices.get(i)).getCharacteristics();
            for (int j = 0; j < gattCharacteristics.size(); j++) {
                BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic) gattCharacteristics.get(j);
                int charaProperty = gattCharacteristic.getProperties();
                if (UUID_ISSC_CHAR_RX.equals(gattCharacteristic.getUuid())) {
                    LogUtil.m294i(TAG, "findService", "UUID_ISSC_CHAR_RX=" + gattCharacteristic.getUuid().toString());
                    if ((charaProperty & 16) > 0) {
                        this.mNotifyCharacteristic = gattCharacteristic;
                        this.mBluetoothGatt.setCharacteristicNotification(gattCharacteristic, true);
                    }
                    BluetoothGattDescriptor descriptor = gattCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    this.mBluetoothGatt.writeDescriptor(descriptor);
                } else if (findTX || !UUID_ISSC_CHAR_TX.equals(gattCharacteristic.getUuid())) {
                    if (!findTX && UUID_ISSC_CHAR_5TX.equals(gattCharacteristic.getUuid()) && ((charaProperty & 8) > 0 || (charaProperty & 4) > 0)) {
                        LogUtil.m294i(TAG, "findService", " UUID_ISSC_CHAR_5TX=" + gattCharacteristic.getUuid().toString());
                        this.mWriteCharacteristic = gattCharacteristic;
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        findTX = true;
                    }
                } else if ((charaProperty & 8) > 0 || (charaProperty & 4) > 0) {
                    LogUtil.m294i(TAG, "findService", " UUID_ISSC_CHAR_TX=" + gattCharacteristic.getUuid().toString());
                    this.mWriteCharacteristic = gattCharacteristic;
                    broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                    findTX = true;
                }
            }
        }
    }

    private void broadcastUpdate(String action) {
        sendBroadcast(new Intent(action));
    }

    public int getRSSI() {
        return this.ble_rssi;
    }

    public void update_rssi() {
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.readRemoteRssi();
        }
    }

    private void broadcastData(String action, byte[] data) {
        Intent intent = new Intent(action);
        if (data != null && data.length > 0) {
            intent.putExtra(EXTRA_DATA, data);
            sendBroadcast(intent);
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (this.mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter != null) {
            return true;
        }
        Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
        return false;
    }

    public boolean connect(String address) {
        LogUtil.m296w(TAG, "connect", "address=" + address);
        if (this.mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.disconnect();
            this.mBluetoothGatt = null;
        }
        this.mBluetoothGatt = device.connectGatt(this, true, this.mGattCallback);
        LogUtil.m296w(TAG, "connect", " mBluetoothGatt=" + this.mBluetoothGatt);
        return true;
    }

    public void disconnect() {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        this.mBluetoothGatt.disconnect();
        LogUtil.m296w(TAG, "disconnect", "disconnect()");
    }

    public void close() {
        LogUtil.m296w(TAG, "close()", "close()");
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
        } else {
            this.mBluetoothGatt.readCharacteristic(characteristic);
        }
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
        } else {
            this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        }
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (this.mBluetoothGatt == null) {
            return null;
        }
        return this.mBluetoothGatt.getServices();
    }
}

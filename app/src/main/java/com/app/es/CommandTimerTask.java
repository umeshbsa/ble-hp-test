package com.app.es;

import java.util.TimerTask;

public class CommandTimerTask extends TimerTask {
    private static final String TAG = "CommandTimerTask";

    public void run() {
        if (GVariable.isConnected && GVariable.faultCode == 0) {
            GVariable.commandIndex++;
            byte[] strvalue;
            if (GVariable.change) {
                GVariable.change = false;
                byte sendSpeed = (byte) GVariable.speed;
                if (!(GVariable.isStart && GVariable.isInPlayer)) {
                    sendSpeed = (byte) 0;
                }
                strvalue = new byte[]{(byte) 1, (byte) 1, sendSpeed};
                if (GVariable.bluetoothLeService != null) {
                    GVariable.bluetoothLeService.WriteValue(strvalue);
                    return;
                }
                return;
            }
            GVariable.change = true;
            byte sendmGradient = (byte) GVariable.gradient;
            if (!(GVariable.isStart && GVariable.isInPlayer)) {
                sendmGradient = (byte) 0;
            }
            strvalue = new byte[]{(byte) 4, (byte) 1, sendmGradient};
            if (GVariable.bluetoothLeService != null) {
                GVariable.bluetoothLeService.WriteValue(strvalue);
            }
        }
    }
}

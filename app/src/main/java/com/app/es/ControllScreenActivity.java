package com.app.es;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hankang.phone.treadmill.R;

import java.util.Timer;
import java.util.TimerTask;

public class ControllScreenActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_control);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GVariable.isStart = true;
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GVariable.isStart = false;
            }
        });


        findViewById(R.id.btn_speed_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GVariable.speed = GVariable.speed + 1;
            }
        });


        findViewById(R.id.btn_speed_min).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GVariable.speed = GVariable.speed - 1;
            }
        });

        new Timer().scheduleAtFixedRate(new CommandTimerTask1(), 10, 500);

        //new Timer().scheduleAtFixedRate(new CommandTimerTask1(), 1000, 1000);
    }


    public class CommandTimerTask1 extends TimerTask {

        private static final String TAG = "CommandTimerTask";

        public void run() {

            Log.i("YYYYYYYYYYY", "Speed : "+GVariable.speed + " : "+GVariable.change + " : "+GVariable.isStart);
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
}

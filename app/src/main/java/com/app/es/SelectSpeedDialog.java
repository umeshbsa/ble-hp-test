package com.app.es;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.hankang.phone.treadmill.R;

public class SelectSpeedDialog extends Dialog implements OnClickListener {
    private SpeedListener mSpeedListener;

    /* renamed from: com.hankang.phone.treadmill.dialog.SelectSpeedDialog$1 */
    class C02371 implements Runnable {
        C02371() {
        }

        public void run() {
            SelectSpeedDialog.this.cancel();
        }
    }

    public interface SpeedListener {
        void speed(int i);
    }

    public SelectSpeedDialog(Activity activity, SpeedListener listener) {
        super(activity, R.style.MyDialog2);
        this.mSpeedListener = listener;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutInflater().inflate(R.layout.select_speed_dialog, null));
        initView();
        new Handler().postDelayed(new C02371(), 2000);
    }

    private void initView() {
        ((LinearLayout) findViewById(R.id.layout_speed_2)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_4)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_6)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_8)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_10)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_12)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_14)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.layout_speed_15)).setOnClickListener(this);
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.layout_speed_2:
                this.mSpeedListener.speed(20);
                dismiss();
                return;
            case R.id.layout_speed_4:
                this.mSpeedListener.speed(40);
                dismiss();
                return;
            case R.id.layout_speed_6:
                this.mSpeedListener.speed(60);
                dismiss();
                return;
            case R.id.layout_speed_8:
                this.mSpeedListener.speed(80);
                dismiss();
                return;
            case R.id.layout_speed_10:
                this.mSpeedListener.speed(100);
                dismiss();
                return;
            case R.id.layout_speed_12:
                this.mSpeedListener.speed(120);
                dismiss();
                return;
            case R.id.layout_speed_14:
                this.mSpeedListener.speed(140);
                dismiss();
                return;
            case R.id.layout_speed_15:
                this.mSpeedListener.speed(150);
                dismiss();
                return;
            default:
                return;
        }
    }
}

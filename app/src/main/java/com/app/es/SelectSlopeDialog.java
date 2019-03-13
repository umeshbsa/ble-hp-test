package com.app.es;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.hankang.phone.treadmill.R;

public class SelectSlopeDialog extends Dialog implements OnClickListener {
    private SlopeListener mSlopeListener;

    /* renamed from: com.hankang.phone.treadmill.dialog.SelectSlopeDialog$1 */
    class C02361 implements Runnable {
        C02361() {
        }

        public void run() {
            SelectSlopeDialog.this.cancel();
        }
    }

    public interface SlopeListener {
        void solpe(int i);
    }

    public SelectSlopeDialog(Activity activity, SlopeListener listener) {
        super(activity, R.style.MyDialog2);
        this.mSlopeListener = listener;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutInflater().inflate(R.layout.select_slope_dialog, null));
        initView();
        new Handler().postDelayed(new C02361(), 2000);
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
                this.mSlopeListener.solpe(2);
                dismiss();
                return;
            case R.id.layout_speed_4:
                this.mSlopeListener.solpe(4);
                dismiss();
                return;
            case R.id.layout_speed_6:
                this.mSlopeListener.solpe(6);
                dismiss();
                return;
            case R.id.layout_speed_8:
                this.mSlopeListener.solpe(8);
                dismiss();
                return;
            case R.id.layout_speed_10:
                this.mSlopeListener.solpe(10);
                dismiss();
                return;
            case R.id.layout_speed_12:
                this.mSlopeListener.solpe(12);
                dismiss();
                return;
            case R.id.layout_speed_14:
                this.mSlopeListener.solpe(14);
                dismiss();
                return;
            case R.id.layout_speed_15:
                this.mSlopeListener.solpe(15);
                dismiss();
                return;
            default:
                return;
        }
    }
}

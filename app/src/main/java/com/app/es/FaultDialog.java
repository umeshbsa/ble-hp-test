package com.app.es;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hankang.phone.treadmill.R;

public class FaultDialog extends Dialog implements OnClickListener {
    private TextView btn_ok;
    private ClickListener mClickListener;
    private int mCode;
    private Resources resources;

    public interface ClickListener {
        void ok();
    }

    public FaultDialog(Activity activity, int code, ClickListener listener) {
        super(activity, R.style.FaultDialog_Style);
        this.mCode = code;
        this.mClickListener = listener;
        this.resources = activity.getResources();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutInflater().inflate(R.layout.fault_dialog, null));
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.text_fault)).setText(getFaultInfo(this.mCode));
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        if (this.mCode < 10) {
            tv_title.setText(new StringBuilder(String.valueOf(this.resources.getString(R.string.faultcode))).append(":E0").append(this.mCode).toString());
        } else {
            tv_title.setText(new StringBuilder(String.valueOf(this.resources.getString(R.string.faultcode))).append(":E").append(this.mCode).toString());
        }
        this.btn_ok = (TextView) findViewById(R.id.btn_ok);
        this.btn_ok.setOnClickListener(this);
    }

    public void onBackPressed() {
    }

    private String getFaultInfo(int code) {
        String info = "unknown fault";
        switch (code) {
            case 1:
                return "Communication failure";
            case 2:
                return "IGBT shortcircuit";
            case 3:
                return "Speed sensor failure";
            case 4:
                return "The ascension self-test failure";
            case 5:
                return "Under the heavy load";
            case 7:
                return "Safety switch off";
            case 8:
                return "Drive failure";
            case 9:
                return "No data returned";
            default:
                return info;
        }
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_ok:
                if (this.mClickListener != null) {
                    this.mClickListener.ok();
                }
                dismiss();
                return;
            default:
                return;
        }
    }
}

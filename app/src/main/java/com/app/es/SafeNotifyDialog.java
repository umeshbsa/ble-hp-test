package com.app.es;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.hankang.phone.treadmill.R;

public class SafeNotifyDialog extends Dialog implements OnClickListener {
    private TextView btn_cancle;
    private TextView btn_ok;
    private OptListener mOptListener;
    private String mTitle = "";
    private TextView tv_title;

    public interface OptListener {
        void cancel();

        void ok();
    }

    public SafeNotifyDialog(Activity activity, String title, OptListener listener) {
        super(activity, R.style.style_safe_dialog);
        this.mOptListener = listener;
        this.mTitle = title;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutInflater().inflate(R.layout.safe_notify_dialog, null));
        initView();
    }

    private void initView() {
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_title.setText(this.mTitle);
        this.btn_ok = (TextView) findViewById(R.id.btn_ok);
        this.btn_ok.setOnClickListener(this);
        this.btn_cancle = (TextView) findViewById(R.id.btn_cancle);
        this.btn_cancle.setOnClickListener(this);
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_ok:
                this.mOptListener.ok();
                dismiss();
                return;
            case R.id.btn_cancle:
                this.mOptListener.cancel();
                dismiss();
                return;
            default:
                return;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 4) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}

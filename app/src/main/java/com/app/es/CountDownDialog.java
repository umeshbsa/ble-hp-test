package com.app.es;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.hankang.phone.treadmill.R;

public class CountDownDialog extends Dialog {
    private TextView count_down_num;
    private String mTitle;

    public CountDownDialog(Activity activity, String title) {
        super(activity, R.style.CountDownDialog);
        this.mTitle = title;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutInflater().inflate(R.layout.count_down_dialog, null));
        initView();
    }

    private void initView() {
        this.count_down_num = (TextView) findViewById(R.id.count_down_num);
        this.count_down_num.setText(this.mTitle);
    }

    public void setCount(String count) {
        this.count_down_num.setText(count);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}

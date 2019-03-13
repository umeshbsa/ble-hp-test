package com.app.es;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hankang.phone.treadmill.R;

public class InputSafePasswordDialog extends Dialog implements OnClickListener {
    private TextView btn_cancle;
    private TextView btn_ok;
    private EditText edit_text_input_1;
    private EditText edit_text_input_2;
    private EditText edit_text_input_3;
    private EditText edit_text_input_4;
    private InputMethodManager imm;
    private TextWatcher listener1 = new C02322();
    private TextWatcher listener2 = new C02333();
    private TextWatcher listener3 = new C02344();
    private TextWatcher listener4 = new C02355();
    private OnTouchListener mOnTouchListener = new C02311();
    private InputListener mOptListener;
    private String mTitle = "";
    private TextView tv_title;

    /* renamed from: com.hankang.phone.treadmill.dialog.InputSafePasswordDialog$1 */
    class C02311 implements OnTouchListener {
        C02311() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: com.hankang.phone.treadmill.dialog.InputSafePasswordDialog$2 */
    class C02322 implements TextWatcher {
        C02322() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                InputSafePasswordDialog.this.edit_text_input_2.requestFocus();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.hankang.phone.treadmill.dialog.InputSafePasswordDialog$3 */
    class C02333 implements TextWatcher {
        C02333() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                InputSafePasswordDialog.this.edit_text_input_3.requestFocus();
            } else {
                InputSafePasswordDialog.this.edit_text_input_1.requestFocus();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.hankang.phone.treadmill.dialog.InputSafePasswordDialog$4 */
    class C02344 implements TextWatcher {
        C02344() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                InputSafePasswordDialog.this.edit_text_input_4.requestFocus();
            } else {
                InputSafePasswordDialog.this.edit_text_input_2.requestFocus();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.hankang.phone.treadmill.dialog.InputSafePasswordDialog$5 */
    class C02355 implements TextWatcher {
        C02355() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() <= 0) {
                InputSafePasswordDialog.this.edit_text_input_3.requestFocus();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public interface InputListener {
        void input(String str);
    }

    public InputSafePasswordDialog(Activity activity, String title, InputListener listener) {
        super(activity, R.style.style_safe_dialog);
        this.mOptListener = listener;
        this.mTitle = title;
        this.imm = (InputMethodManager) activity.getSystemService("input_method");
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutInflater().inflate(R.layout.input_safe_password_dialog, null));
        initView();
    }

    private void initView() {
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_title.setText(this.mTitle);
        this.edit_text_input_1 = (EditText) findViewById(R.id.edit_text_input_1);
        this.edit_text_input_2 = (EditText) findViewById(R.id.edit_text_input_2);
        this.edit_text_input_3 = (EditText) findViewById(R.id.edit_text_input_3);
        this.edit_text_input_4 = (EditText) findViewById(R.id.edit_text_input_4);
        this.btn_ok = (TextView) findViewById(R.id.btn_ok);
        this.btn_ok.setOnClickListener(this);
        this.btn_cancle = (TextView) findViewById(R.id.btn_cancle);
        this.btn_cancle.setOnClickListener(this);
        this.edit_text_input_1.addTextChangedListener(this.listener1);
        this.edit_text_input_2.addTextChangedListener(this.listener2);
        this.edit_text_input_3.addTextChangedListener(this.listener3);
        this.edit_text_input_4.addTextChangedListener(this.listener4);
        this.edit_text_input_1.requestFocus();
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_ok:
                String value1 = this.edit_text_input_1.getText().toString();
                String value2 = this.edit_text_input_2.getText().toString();
                String value3 = this.edit_text_input_3.getText().toString();
                this.mOptListener.input(new StringBuilder(String.valueOf(value1)).append(value2).append(value3).append(this.edit_text_input_4.getText().toString()).toString());
                dismiss();
                return;
            case R.id.btn_cancle:
                dismiss();
                return;
            default:
                return;
        }
    }
}

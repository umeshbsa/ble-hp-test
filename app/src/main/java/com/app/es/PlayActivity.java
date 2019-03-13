package com.app.es;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hankang.phone.treadmill.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PlayActivity extends Activity implements OnClickListener {
    private static final String TAG = "PlayActivity";

    private CheckBox button_switch;
    private int curChartIndex = 0;
    private ImageView gradient_add;
    private ImageView gradient_reduce;
    private boolean haveLongClick = false;
    private boolean isSpeach;
    private LinearLayout layout_operation_gradient;
    private LinearLayout layout_operation_speed;
    private LinearLayout layout_plan_init;
    private ArrayList<ImageView> mProgressList = new ArrayList();
    private ArrayList<PlanStep> mRunList = new ArrayList();
    private float mSlopePosX = 0.0f;
    private float mSlopePosY = 0.0f;
    private float mSpeedButtonPosX = 0.0f;
    private float mSpeedButtonPosY = 0.0f;
    private float mSpeedPosX = 0.0f;
    private float mSpeedPosY = 0.0f;
    private TextView plan_init_distance;
    private TextView plan_init_heat;
    private TextView plan_init_time;
    private ImageView progress_img_1;
    private ImageView progress_img_10;
    private ImageView progress_img_11;
    private ImageView progress_img_12;
    private ImageView progress_img_2;
    private ImageView progress_img_3;
    private ImageView progress_img_4;
    private ImageView progress_img_5;
    private ImageView progress_img_6;
    private ImageView progress_img_7;
    private ImageView progress_img_8;
    private ImageView progress_img_9;
    private TextView run_gradient;
    private TextView run_speed;
    private ImageView speed_add;
    private ImageView speed_reduce;
    private int stopTime = 0;
    private TextView text_big_speed_slope;
    private TextView text_distance;
    private TextView text_heat;
    private TextView text_notify_login;
    private TextView text_time_minute;
    private TextView text_time_second;
    private TextView text_xinlv;
    private TextView title_plan;
    private ImageView treadmill_connected;
    private ProgressBar treadmill_connecting;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GVariable.isInPlayer = true;
        setContentView(R.layout.activity_play);


        System.gc();
        //  PhotoUtil.setBackground(this);
        initIcon();
        this.isSpeach = PreferenceUtil.getBoolean(this, PreferenceUtil.IS_SPEACH, true);
        this.text_notify_login = (TextView) findViewById(R.id.text_notify_login);
        this.treadmill_connecting = (ProgressBar) findViewById(R.id.treadmill_connecting);
        this.treadmill_connected = (ImageView) findViewById(R.id.treadmill_connected);
        this.title_plan = (TextView) findViewById(R.id.title_plan);
        this.layout_plan_init = (LinearLayout) findViewById(R.id.layout_plan_init);
        this.plan_init_time = (TextView) findViewById(R.id.plan_init_time);
        this.plan_init_distance = (TextView) findViewById(R.id.plan_init_distance);
        this.plan_init_heat = (TextView) findViewById(R.id.plan_init_heat);
        this.text_time_minute = (TextView) findViewById(R.id.text_time_minute);
        this.text_time_second = (TextView) findViewById(R.id.text_time_second);
        this.text_distance = (TextView) findViewById(R.id.text_distance);
        this.text_heat = (TextView) findViewById(R.id.text_heat);
        this.text_xinlv = (TextView) findViewById(R.id.text_xinlv);
        this.run_gradient = (TextView) findViewById(R.id.run_gradient);
        this.gradient_add = (ImageView) findViewById(R.id.gradient_add);
        this.gradient_reduce = (ImageView) findViewById(R.id.gradient_reduce);
        this.run_speed = (TextView) findViewById(R.id.run_speed);
        this.speed_add = (ImageView) findViewById(R.id.speed_add);
        this.speed_reduce = (ImageView) findViewById(R.id.speed_reduce);
        this.button_switch = (CheckBox) findViewById(R.id.button_switch);
        this.layout_operation_speed = (LinearLayout) findViewById(R.id.layout_operation_speed);
        this.layout_operation_gradient = (LinearLayout) findViewById(R.id.layout_operation_gradient);
        this.progress_img_1 = (ImageView) findViewById(R.id.progress_img_1);
        this.progress_img_2 = (ImageView) findViewById(R.id.progress_img_2);
        this.progress_img_3 = (ImageView) findViewById(R.id.progress_img_3);
        this.progress_img_4 = (ImageView) findViewById(R.id.progress_img_4);
        this.progress_img_5 = (ImageView) findViewById(R.id.progress_img_5);
        this.progress_img_6 = (ImageView) findViewById(R.id.progress_img_6);
        this.progress_img_7 = (ImageView) findViewById(R.id.progress_img_7);
        this.progress_img_8 = (ImageView) findViewById(R.id.progress_img_8);
        this.progress_img_9 = (ImageView) findViewById(R.id.progress_img_9);
        this.progress_img_10 = (ImageView) findViewById(R.id.progress_img_10);
        this.progress_img_11 = (ImageView) findViewById(R.id.progress_img_11);
        this.progress_img_12 = (ImageView) findViewById(R.id.progress_img_12);
        this.text_big_speed_slope = (TextView) findViewById(R.id.text_big_speed_slope);
        this.mProgressList.add(this.progress_img_1);
        this.mProgressList.add(this.progress_img_2);
        this.mProgressList.add(this.progress_img_3);
        this.mProgressList.add(this.progress_img_4);
        this.mProgressList.add(this.progress_img_5);
        this.mProgressList.add(this.progress_img_6);
        this.mProgressList.add(this.progress_img_7);
        this.mProgressList.add(this.progress_img_8);
        this.mProgressList.add(this.progress_img_9);
        this.mProgressList.add(this.progress_img_10);
        this.mProgressList.add(this.progress_img_11);
        this.mProgressList.add(this.progress_img_12);
        this.gradient_add.setOnClickListener(this);
        this.gradient_reduce.setOnClickListener(this);
        this.speed_add.setOnClickListener(this);
        this.speed_reduce.setOnClickListener(this);
        this.gradient_add.setOnLongClickListener(this.mLongClick);
        this.gradient_reduce.setOnLongClickListener(this.mLongClick);
        this.speed_add.setOnLongClickListener(this.mLongClick);
        this.speed_reduce.setOnLongClickListener(this.mLongClick);
        this.button_switch.setOnCheckedChangeListener(this.switchButtonListener);
        this.button_switch.setOnTouchListener(this.mOnTouchListener);
        this.gradient_add.setOnTouchListener(this.mOnTouchListener);
        this.gradient_reduce.setOnTouchListener(this.mOnTouchListener);
        this.speed_add.setOnTouchListener(this.mOnTouchListener);
        this.speed_reduce.setOnTouchListener(this.mOnTouchListener);
        this.button_switch.setEnabled(false);
        this.layout_operation_speed.setOnTouchListener(this.mOnTouchListener);
        this.layout_operation_gradient.setOnTouchListener(this.mOnTouchListener);
        initView();
        setConnectState();
        new Handler().postDelayed(

                new Runnable() {
                    @Override
                    public void run() {
                        PlayActivity.this.button_switch.setEnabled(true);
                    }
                }
                , 500);
        registerReceiver(this.refreshDataReceiver, refreshDataIntentFilter());
        if (GVariable.currentMember == null) {
            this.text_notify_login.setVisibility(0);
        } else {
            this.text_notify_login.setVisibility(8);
        }
    }

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$1 */
    OnCheckedChangeListener switchButtonListener = new OnCheckedChangeListener() {


        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!isChecked) {
                PlayActivity.this.uploadData(RefreshDataTask.mCountSecond / 60, (int) GVariable.distance, GVariable.getCalorie());
                GVariable.isStart = false;
                GVariable.calorie = 0;
                GVariable.distance = 0.0f;
                GVariable.speed = GVariable.MIN_SPEED;
                GVariable.gradient = 0;
                RefreshDataTask.mCountSecond = 0;
                RefreshDataTask.setStopTime(0);
                PlayActivity.this.run_gradient.setText(new StringBuilder(String.valueOf(GVariable.gradient)).toString());
                PlayActivity.this.run_speed.setText(GVariable.getSpeed());
                PlayActivity.this.text_time_minute.setText(PlayUtil.formateMinute(RefreshDataTask.mCountSecond));
                PlayActivity.this.text_time_second.setText(PlayUtil.formateSecond(RefreshDataTask.mCountSecond));
                PlayActivity.this.text_distance.setText(String.valueOf((int) GVariable.distance));
                PlayActivity.this.text_heat.setText(String.valueOf(GVariable.getCalorie()));
                PlayActivity.this.curChartIndex = 0;
                PlayActivity.this.layout_plan_init.setVisibility(4);
                PlayActivity.this.title_plan.setText(PlayActivity.this.getResources().getString(R.string.freemovement));
                for (int i = 0; i < PlayActivity.this.mProgressList.size(); i++) {
                    ((ImageView) PlayActivity.this.mProgressList.get(i)).setImageResource(R.drawable.pro_transparent);
                }
                RefreshDataTask.MODE = 0;
            } else if (!GVariable.isStart) {
                PlayActivity.this.downCountHandler.sendEmptyMessageDelayed(3, 50);
            }
        }
    };

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$2 */
    Handler bigTextHandler = new Handler() {
        public void handleMessage(Message msg) {
            PlayActivity.this.text_big_speed_slope.setVisibility(4);
        }
    };

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$3 */
    OnTouchListener mOnTouchListener = new OnTouchListener() {

        /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$3$1 */
        class C03711 implements SelectSpeedDialog.SpeedListener {
            C03711() {
            }

            public void speed(int speed) {
                GVariable.speed = speed;
                PlayActivity.this.run_speed.setText(GVariable.getSpeed());
                PlayActivity.this.updateProgressView();
            }
        }

        /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$3$2 */
        class C03722 implements SelectSlopeDialog.SlopeListener {
            C03722() {
            }

            public void solpe(int solpe) {
                GVariable.gradient = solpe;
                PlayActivity.this.run_gradient.setText(new StringBuilder(String.valueOf(GVariable.gradient)).toString());
            }
        }

        /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$3$3 */
        class C03733 implements SafeNotifyDialog.OptListener {
            C03733() {
            }

            public void ok() {
                //   PlayActivity.this.startActivityForResult(new Intent(PlayActivity.this, SetSafePasswordActivity.class), 0);
            }

            public void cancel() {
                PlayActivity.this.button_switch.setChecked(true);
            }
        }

        /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$3$4 */
        class C03744 implements InputSafePasswordDialog.InputListener {
            C03744() {
            }

            public void input(String str) {
                boolean z = true;
                if (PreferenceUtil.getString(PlayActivity.this, PreferenceUtil.KEY_SAFE_LOCK_PWD, "").equals(str)) {
                    boolean ischeck = PlayActivity.this.button_switch.isChecked();
                    CheckBox access$25 = PlayActivity.this.button_switch;
                    if (ischeck) {
                        z = false;
                    }
                    access$25.setChecked(z);
                    return;
                }
                Toast.makeText(PlayActivity.this, R.string.passwordnotcorrect, 1).show();
            }
        }

        public boolean onTouch(View v, MotionEvent event) {
            float moveX;
            float moveY;
            int height;
            if (v.getId() == R.id.layout_operation_speed) {
                switch (event.getAction()) {
                    case 0:
                        PlayActivity.this.mSpeedPosX = event.getRawX();
                        PlayActivity.this.mSpeedPosY = event.getRawY();
                        return true;
                    case 1:
                        moveX = PlayActivity.this.mSpeedPosX - event.getRawX();
                        moveY = Math.abs(event.getRawY() - PlayActivity.this.mSpeedPosY);
                        height = PlayActivity.this.layout_operation_speed.getBottom() - PlayActivity.this.layout_operation_speed.getTop();
                        if (moveX <= ((float) ((PlayActivity.this.layout_operation_speed.getRight() - PlayActivity.this.layout_operation_speed.getLeft()) / 2)) || moveY >= ((float) (height / 2))) {
                            return true;
                        }
                        new SelectSpeedDialog(PlayActivity.this, new C03711()).show();
                        return true;
                    default:
                        return true;
                }
            } else if (v.getId() == R.id.layout_operation_gradient) {
                switch (event.getAction()) {
                    case 0:
                        PlayActivity.this.mSlopePosX = event.getRawX();
                        PlayActivity.this.mSlopePosY = event.getRawY();
                        return true;
                    case 1:
                        moveX = event.getRawX() - PlayActivity.this.mSlopePosX;
                        moveY = Math.abs(event.getRawY() - PlayActivity.this.mSlopePosY);
                        height = PlayActivity.this.layout_operation_gradient.getBottom() - PlayActivity.this.layout_operation_gradient.getTop();
                        if (moveX <= ((float) ((PlayActivity.this.layout_operation_gradient.getRight() - PlayActivity.this.layout_operation_gradient.getLeft()) / 2)) || moveY >= ((float) (height / 2))) {
                            return true;
                        }
                        new SelectSlopeDialog(PlayActivity.this, new C03722()).show();
                        return true;
                    default:
                        return true;
                }
            } else {
                if (v.getId() != R.id.button_switch) {
                    if (v.getId() == R.id.gradient_add || v.getId() == R.id.gradient_reduce || v.getId() == R.id.speed_add || v.getId() == R.id.speed_reduce) {
                        switch (event.getAction()) {
                            case 0:
                                PlayActivity.this.mSpeedButtonPosX = event.getRawX();
                                PlayActivity.this.mSpeedButtonPosY = event.getRawY();
                                break;
                            case 1:
                                PlayActivity.this.stopOperationHandler();
                                break;
                            case 2:
                                if (Math.abs(PlayActivity.this.mSpeedButtonPosX - event.getRawX()) + Math.abs(PlayActivity.this.mSpeedButtonPosY - event.getRawY()) > 40.0f) {
                                    PlayActivity.this.stopOperationHandler();
                                    break;
                                }
                                break;
                        }
                    }
                } else if (event.getAction() == 0) {
                    if (GVariable.isConnected) {
                        if (PreferenceUtil.getBoolean(PlayActivity.this, PreferenceUtil.IS_FIRST_USE, true)) {
                            if (!PlayActivity.this.button_switch.isChecked()) {
                                PreferenceUtil.setBoolean(PlayActivity.this, PreferenceUtil.IS_FIRST_USE, false);
                                new SafeNotifyDialog(PlayActivity.this, PlayActivity.this.getResources().getString(R.string.saftwarn), new C03733()).show();
                                return true;
                            }
                        } else if (PreferenceUtil.getBoolean(PlayActivity.this, PreferenceUtil.IS_SAFE_LOCK, false) && !PlayActivity.this.button_switch.isChecked()) {
                            new InputSafePasswordDialog(PlayActivity.this, PlayActivity.this.getResources().getString(R.string.safelock), new C03744()).show();
                            return true;
                        }
                        return false;
                    }
                    if (PlayActivity.this.isSpeach) {
                        HkApplication hkApplication = HkApplication.application;
                        if (HkApplication.isEn(PlayActivity.this)) {
                            // Speach.TTSPlay("Please connect the running machine");
                        } else {
                            // Speach.TTSPlay("请连接跑步机");
                        }
                    }
                    Toast.makeText(PlayActivity.this, R.string.notreadmill, 0).show();
                    return true;
                }
                return false;
            }
        }
    };

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$4 */
    OnLongClickListener mLongClick = new OnLongClickListener() {

        public boolean onLongClick(View v) {
            PlayActivity.this.haveLongClick = true;
            PlayActivity.this.operationHandler.removeMessages(R.id.gradient_add);
            PlayActivity.this.operationHandler.removeMessages(R.id.gradient_reduce);
            PlayActivity.this.operationHandler.removeMessages(R.id.speed_add);
            PlayActivity.this.operationHandler.removeMessages(R.id.speed_reduce);
            PlayActivity.this.operationHandler.sendEmptyMessageDelayed(v.getId(), 200);
            return false;
        }
    };

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$5 */
    Handler operationHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case R.id.gradient_add:
                    if (GVariable.gradient < GVariable.MAX_GRADIENT) {
                        GVariable.gradient++;
                        PlayActivity.this.run_gradient.setText(new StringBuilder(String.valueOf(GVariable.gradient)).toString());
                        PlayActivity.this.setBigSpeedSlope(false, String.valueOf(GVariable.gradient));
                        PlayActivity.this.operationHandler.sendEmptyMessageDelayed(msg.what, 50);
                        return;
                    }
                    return;
                case R.id.gradient_reduce:
                    if (GVariable.gradient > 0) {
                        GVariable.gradient--;
                        PlayActivity.this.run_gradient.setText(new StringBuilder(String.valueOf(GVariable.gradient)).toString());
                        PlayActivity.this.setBigSpeedSlope(false, String.valueOf(GVariable.gradient));
                        PlayActivity.this.operationHandler.sendEmptyMessageDelayed(msg.what, 50);
                        return;
                    }
                    return;
                case R.id.speed_add:
                    if (GVariable.speed < GVariable.MAX_SPEED) {
                        GVariable.speed++;
                        PlayActivity.this.run_speed.setText(GVariable.getSpeed());
                        PlayActivity.this.setBigSpeedSlope(true, GVariable.getSpeed());
                        PlayActivity.this.updateProgressView();
                        PlayActivity.this.operationHandler.sendEmptyMessageDelayed(msg.what, 50);
                        return;
                    }
                    return;
                case R.id.speed_reduce:
                    if (GVariable.speed > GVariable.MIN_SPEED) {
                        GVariable.speed--;
                        PlayActivity.this.run_speed.setText(GVariable.getSpeed());
                        PlayActivity.this.setBigSpeedSlope(true, GVariable.getSpeed());
                        PlayActivity.this.updateProgressView();
                        PlayActivity.this.operationHandler.sendEmptyMessageDelayed(msg.what, 50);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$6 */
    Handler downCountHandler = new Handler() {
        CountDownDialog countDownDialog;

        public void handleMessage(Message msg) {
            if (PlayActivity.this != null) {
                switch (msg.what) {
                    case 0:
                        if (PlayActivity.this.isSpeach) {
                            HkApplication hkApplication = HkApplication.application;
                            if (HkApplication.isEn(PlayActivity.this)) {
                                // Speach.TTSPlay("Start running, please watch your step");
                            } else {
                                // Speach.TTSPlay("开始跑步，请注意脚下安全");
                            }
                        }
                        if (this.countDownDialog != null && this.countDownDialog.isShowing()) {
                            this.countDownDialog.cancel();
                            this.countDownDialog = null;
                        }
                        GVariable.isStart = true;
                        PlayActivity.this.button_switch.setChecked(GVariable.isStart);
                        return;
                    case 1:
                        if (PlayActivity.this.isSpeach) {
                            //  Speach.TTSPlay("1");
                        }
                        this.countDownDialog.setCount(new StringBuilder(String.valueOf(msg.what)).toString());
                        PlayActivity.this.downCountHandler.sendEmptyMessageDelayed(0, 1000);
                        return;
                    case 2:
                        if (PlayActivity.this.isSpeach) {
                            //  Speach.TTSPlay("2");
                        }
                        this.countDownDialog.setCount(new StringBuilder(String.valueOf(msg.what)).toString());
                        PlayActivity.this.downCountHandler.sendEmptyMessageDelayed(1, 1000);
                        return;
                    case 3:
                    case 4:
                    case 5:
                        if (PlayActivity.this.isSpeach) {
                            // Speach.TTSPlay("3");
                        }
                        this.countDownDialog = new CountDownDialog(PlayActivity.this, new StringBuilder(String.valueOf(msg.what)).toString());
                        this.countDownDialog.show();
                        PlayActivity.this.downCountHandler.sendEmptyMessageDelayed(2, 1000);
                        return;
                    default:
                        return;
                }
            }
        }
    };

    BroadcastReceiver commandSpeedSlopeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BleUtil.ACTION_RECEIVE_SPEED.equals(action)) {
                PlayActivity.this.setBigSpeedSlope(true, GVariable.getSpeed());
            } else if (BleUtil.ACTION_RECEIVE_SLOPE.equals(action)) {
                PlayActivity.this.setBigSpeedSlope(false, String.valueOf(GVariable.gradient));
            } else if (BleUtil.ACTION_START_SWITCH.equals(action)) {
                PlayActivity.this.button_switch.setChecked(GVariable.isStart);
                if (GVariable.faultCode != 0) {
                    new FaultDialog(PlayActivity.this, GVariable.faultCode, new FaultDialog.ClickListener() {
                        @Override
                        public void ok() {

                            PlayActivity.this.finish();
                        }
                    }).show();
                }
            }
        }
    };

    /* renamed from: com.hankang.phone.treadmill.activity.PlayActivity$8 */
    BroadcastReceiver refreshDataReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {


            String action = intent.getAction();
            Log.i("TTTTTTTTTTTTT", "Call Play " + action);


            if (RefreshDataTask.ACTION_BASE_DATA.equals(action)) {


                PlayActivity.this.text_time_minute.setText(intent.getStringExtra("minute"));
                PlayActivity.this.text_time_second.setText(intent.getStringExtra("second"));
                PlayActivity.this.text_distance.setText(intent.getStringExtra("distance"));
                PlayActivity.this.text_heat.setText(intent.getStringExtra("heat"));
                PlayActivity.this.text_xinlv.setText(new StringBuilder(String.valueOf(PlayActivity.this.getResources().getString(R.string.pulse))).append(":").append(GVariable.heartRate).toString());
                PlayActivity.this.button_switch.setChecked(GVariable.isStart);
                PlayActivity.this.run_gradient.setText(String.valueOf(GVariable.gradient));
                PlayActivity.this.run_speed.setText(GVariable.getSpeed());
                PlayActivity.this.curChartIndex = intent.getIntExtra("curChartIndex", 0);
                if (RefreshDataTask.MODE != 4) {
                    PlayUtil.setFreeState(PlayActivity.this.curChartIndex, PlayActivity.this.mProgressList, GVariable.speed);
                }
            } else if (RefreshDataTask.ACTION_PLAN_CHART_DATA.equals(action)) {

                PlayUtil.setPlanState(intent.getIntExtra("index", 0), PlayActivity.this.mProgressList, PlayActivity.this.mRunList);

            } else if (BleUtil.ACTION_TREADMILL_CONNECTED.equals(action)) {

                PlayActivity.this.setConnectState();

            } else if (BleUtil.ACTION_TREADMILL_DISCONNECTED.equals(action)) {

                PlayActivity.this.setConnectState();

            } else if (BleUtil.ACTION_BACK_TO_HOME.equals(action)) {

                PlayActivity.this.finish();

            }
        }
    };


    protected void onDestroy() {
        GVariable.isInPlayer = false;
        unregisterReceiver(this.refreshDataReceiver);
        super.onDestroy();
    }

    private void initIcon() {
        TextView button_back = (TextView) findViewById(R.id.button_back);
        button_back.setOnClickListener(this);
        //   AliIconUtil.initIcon(this, button_back);
        ((ImageView) findViewById(R.id.btn_sence)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.btn_internet)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.btn_video)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.btn_music)).setOnClickListener(this);
    }

    private void initView() {
        RefreshDataTask.MODE = getIntent().getIntExtra("MODE", 0);
        this.title_plan.setText(getResources().getString(R.string.freemovement));
        this.text_time_minute.setText("00:00");
        this.text_time_second.setText("00");
        this.text_distance.setText("0");
        this.text_heat.setText("0");
        this.text_xinlv.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.pulse))).append(":0").toString());
        this.run_gradient.setText(String.valueOf(GVariable.gradient));
        this.run_speed.setText(GVariable.getSpeed());
        this.button_switch.setChecked(GVariable.isStart);
        this.stopTime = getIntent().getIntExtra("stopTime", 0);
        String planTitle = getIntent().getStringExtra("planTitle");
        switch (RefreshDataTask.MODE) {
            case 1:
                this.title_plan.setText(planTitle);
                String distance = getIntent().getStringExtra("distance");
                if (!TextUtils.isEmpty(distance)) {
                    RefreshDataTask.setDistance(Integer.parseInt(distance));
                }
                this.text_distance.setText(distance);
                return;
            case 2:
                this.title_plan.setText(planTitle);
                String time = getIntent().getStringExtra(C0126c.f298l);
                if (!TextUtils.isEmpty(time)) {
                    int TOTAL_TIME = Integer.parseInt(time) * 60;
                    RefreshDataTask.setTime(TOTAL_TIME);
                    this.text_time_minute.setText(PlayUtil.formateMinute(TOTAL_TIME));
                    this.text_time_second.setText(PlayUtil.formateSecond(TOTAL_TIME));
                    return;
                }
                return;
            case 3:
                this.title_plan.setText(planTitle);
                String heat = getIntent().getStringExtra("heat");
                if (!TextUtils.isEmpty(heat)) {
                    int TOTAL_HEAT = Integer.parseInt(heat) * 1000;
                    RefreshDataTask.setHeat(TOTAL_HEAT);
                    this.text_heat.setText(String.valueOf(TOTAL_HEAT / 1000));
                    return;
                }
                return;
            case 4:
                this.title_plan.setText(planTitle);
                this.mRunList = getIntent().getParcelableArrayListExtra("runList");
                this.layout_plan_init.setVisibility(0);
                this.plan_init_time.setText(getIntent().getStringExtra(C0126c.f298l));
                this.plan_init_distance.setText(getIntent().getStringExtra("distance"));
                this.plan_init_heat.setText(getIntent().getStringExtra("heat"));
                if (this.mRunList != null) {
                    RefreshDataTask.setStopTime(this.stopTime);
                    RefreshDataTask.setRunList(this.mRunList);
                    PlayUtil.initPlanState(this.mProgressList, this.mRunList, this.stopTime);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_back:
                GVariable.isStart = false;
                GVariable.calorie = 0;
                GVariable.distance = 0.0f;
                GVariable.speed = GVariable.MIN_SPEED;
                GVariable.gradient = 0;
                RefreshDataTask.mCountSecond = 0;
                finish();
                return;
          /*  case R.id.btn_sence:
                startActivity(new Intent(this, SceneSelectActivity.class));
                return;*/
           /* case R.id.btn_internet:
                intent = new Intent(this, MediaPlayActivity.class);
                intent.putExtra("startMode", 3);
                startActivity(intent);
                return;*/
           /* case R.id.btn_music:
                intent = new Intent(this, MediaPlayActivity.class);
                intent.putExtra("startMode", 2);
                startActivity(intent);
                return;*/
          /*  case R.id.btn_video:
                intent = new Intent(this, MediaPlayActivity.class);
                intent.putExtra("startMode", 1);
                startActivity(intent);
                return;*/
            case R.id.gradient_add:
                if (this.haveLongClick) {
                    stopOperationHandler();
                    return;
                } else if (GVariable.gradient < GVariable.MAX_GRADIENT) {
                    GVariable.gradient++;
                    this.run_gradient.setText(new StringBuilder(String.valueOf(GVariable.gradient)).toString());
                    setBigSpeedSlope(false, String.valueOf(GVariable.gradient));
                    return;
                } else {
                    return;
                }
            case R.id.gradient_reduce:
                if (this.haveLongClick) {
                    stopOperationHandler();
                    return;
                } else if (GVariable.gradient > 0) {
                    GVariable.gradient--;
                    this.run_gradient.setText(new StringBuilder(String.valueOf(GVariable.gradient)).toString());
                    setBigSpeedSlope(false, String.valueOf(GVariable.gradient));
                    return;
                } else {
                    return;
                }
            case R.id.speed_add:
                if (this.haveLongClick) {
                    stopOperationHandler();
                    return;
                } else if (GVariable.speed < GVariable.MAX_SPEED) {
                    GVariable.speed++;
                    this.run_speed.setText(GVariable.getSpeed());
                    setBigSpeedSlope(true, GVariable.getSpeed());
                    updateProgressView();
                    return;
                } else {
                    return;
                }
            case R.id.speed_reduce:
                if (this.haveLongClick) {
                    stopOperationHandler();
                    return;
                } else if (GVariable.speed > GVariable.MIN_SPEED) {
                    GVariable.speed--;
                    this.run_speed.setText(GVariable.getSpeed());
                    setBigSpeedSlope(true, GVariable.getSpeed());
                    updateProgressView();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void setBigSpeedSlope(boolean isSpeed, String text) {
        this.text_big_speed_slope.setText(text);
        this.text_big_speed_slope.setVisibility(0);
        if (isSpeed) {
            this.text_big_speed_slope.setTextColor(getResources().getColor(R.color.speed_blue));
        } else {
            this.text_big_speed_slope.setTextColor(getResources().getColor(R.color.slope_green));
        }
        this.bigTextHandler.removeMessages(1);
        this.bigTextHandler.sendEmptyMessageDelayed(1, 800);
    }

    private void updateProgressView() {
        if (!GVariable.isStart) {
            return;
        }
        if (RefreshDataTask.MODE == 4) {
            int index = (this.mRunList.size() - RefreshDataTask.getRunLinkedList().size()) - 1;
            if (index < 0) {
                index = 0;
            }
            PlayUtil.setPlanState(index, this.mProgressList, this.mRunList);
            return;
        }
        PlayUtil.setFreeState(this.curChartIndex, this.mProgressList, GVariable.speed);
    }

    private void stopOperationHandler() {
        this.haveLongClick = false;
        this.operationHandler.removeMessages(R.id.gradient_add);
        this.operationHandler.removeMessages(R.id.gradient_reduce);
        this.operationHandler.removeMessages(R.id.speed_add);
        this.operationHandler.removeMessages(R.id.speed_reduce);
    }

    public void onBackPressed() {
        GVariable.isStart = false;
        GVariable.calorie = 0;
        GVariable.distance = 0.0f;
        GVariable.speed = GVariable.MIN_SPEED;
        GVariable.gradient = 0;
        RefreshDataTask.mCountSecond = 0;
        super.onBackPressed();
    }

    private String getHeatDestription(int heat) {
        if (heat <= 0) {
            return "";
        }
        String destription = "";
        try {
            InputStream is = getAssets().open("speach_description.txt");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            JSONArray result = new JSONObject(new String(buffer, "UTF-8")).optJSONArray("result");
            LogUtil.m294i(TAG, "getHeatDestription", "result" + result.toString());
            for (int i = 0; i < result.length(); i++) {
                JSONObject speach = result.optJSONObject(i);
                String sound = speach.optString("sound");
                if (Integer.parseInt(speach.optString("calorie")) <= heat) {
                    return "相当于" + sound.trim() + "的热量";
                }
            }
            return destription;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return destription;
        }
    }

    private void uploadData(int time, int distance, int calorie) {
        /*if (!GVariable.isUpload) {
            GVariable.isUpload = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    GVariable.isUpload = false;
                }
            }, 5000);
            if (GVariable.currentMember != null && time != 0 && distance != 0 && calorie != 0) {
                String channelId = getIntent().getStringExtra("id");
                RequestParams params = new RequestParams();
                params.put("requestId", HkApplication.application.getAppId());
                params.put("msgToken", GVariable.currentMember.getId());
                params.put(C0126c.f288b, "record");
                params.put("channelId", channelId);
                params.put(C0126c.f298l, time);
                params.put("distance", distance);
                params.put("calorie", calorie);
                params.put("language", "en");
                HttpUtil.get(Urls.address, params, new JsonHttpResponseHandler() {
                    public void setRequestURI(URI requestURI) {
                        LogUtil.m294i(PlayActivity.TAG, "uploadData/setRequestURI", requestURI.toString());
                    }

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        LogUtil.m294i(PlayActivity.TAG, "uploadData/onSuccess", "statusCode=" + statusCode);
                        LogUtil.m294i(PlayActivity.TAG, "uploadData/onSuccess", response.toString());
                        JSONObject result = response.optJSONObject("result");
                        String error = response.optString("error");
                        if (!TextUtils.isEmpty(error) && error.equals("null")) {
                        }
                    }

                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        LogUtil.m294i(PlayActivity.TAG, "uploadData/onFailure", "statusCode=" + statusCode);
                    }
                });
            }
        }*/
    }

    protected void onResume() {
        super.onResume();
        if (GVariable.isStart != this.button_switch.isChecked()) {
            this.button_switch.setChecked(GVariable.isStart);
        }
        registerReceiver(this.commandSpeedSlopeReceiver, commandSpeedSlopeIntentFilter());
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.commandSpeedSlopeReceiver);
    }

    private void setConnectState() {
        Log.i(TAG, "setConnectState=" + GVariable.isConnected);
        if (GVariable.isConnected) {
            this.treadmill_connecting.setVisibility(8);
            this.treadmill_connected.setVisibility(0);
            return;
        }
        this.treadmill_connecting.setVisibility(0);
        this.treadmill_connected.setVisibility(8);
    }

    private static IntentFilter commandSpeedSlopeIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleUtil.ACTION_RECEIVE_SPEED);
        intentFilter.addAction(BleUtil.ACTION_RECEIVE_SLOPE);
        intentFilter.addAction(BleUtil.ACTION_START_SWITCH);
        return intentFilter;
    }

    private static IntentFilter refreshDataIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RefreshDataTask.ACTION_BASE_DATA);
        intentFilter.addAction(RefreshDataTask.ACTION_PLAN_CHART_DATA);
        intentFilter.addAction(BleUtil.ACTION_TREADMILL_CONNECTED);
        intentFilter.addAction(BleUtil.ACTION_TREADMILL_DISCONNECTED);
        intentFilter.addAction(BleUtil.ACTION_BACK_TO_HOME);
        return intentFilter;
    }
}

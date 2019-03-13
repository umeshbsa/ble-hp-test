package com.app.es;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;

public class RefreshDataTask extends TimerTask {
    public static final String ACTION_BASE_DATA = "com.hankang.base.data";
    public static final String ACTION_PLAN_CHART_DATA = "com.hankang.plan.chart.data";
    public static int MODE = 0;
    private static final String TAG = "RefreshDataTask";
    private static int TOTAL_DISTANCE = 0;
    private static int TOTAL_HEAT = 0;
    private static int TOTAL_TIME = 0;
    public static int mCountSecond = 0;
    private static LinkedList<PlanStep> mRunLinkedList = new LinkedList();
    private static ArrayList<PlanStep> mRunList = new ArrayList();
    private static int stopTime = 0;
    private Context mContext;
    private int mRunProgress = 0;
    private int stepRunedTime = 0;
    private int stepSlope = 0;
    private int stepSpeed = 0;
    private int stepTime = 0;

    public RefreshDataTask(Context context) {
        this.mContext = context;
    }

    public void run() {
        if (GVariable.isStart) {
            String minute = "00:00";
            String second = "00";
            String distance = "0";
            String heat = "0";
            int curChartIndex = 0;
            switch (MODE) {
                case 0:
                    minute = PlayUtil.formateMinute(mCountSecond);
                    second = PlayUtil.formateSecond(mCountSecond);
                    distance = String.valueOf((int) GVariable.distance);
                    heat = String.valueOf(GVariable.getCalorie());
                    curChartIndex = mCountSecond / 180;
                    break;
                case 1:
                    if (((int) GVariable.distance) >= TOTAL_DISTANCE) {
                        GVariable.distance = (float) TOTAL_DISTANCE;
                        GVariable.isStart = false;
                    }
                    this.mRunProgress = (((int) GVariable.distance) * 1000) / TOTAL_DISTANCE;
                    minute = PlayUtil.formateMinute(mCountSecond);
                    second = PlayUtil.formateSecond(mCountSecond);
                    distance = String.valueOf(TOTAL_DISTANCE - ((int) GVariable.distance));
                    heat = String.valueOf(GVariable.getCalorie());
                    curChartIndex = this.mRunProgress / 83;
                    break;
                case 2:
                    if (mCountSecond >= TOTAL_TIME) {
                        mCountSecond = TOTAL_TIME;
                        GVariable.isStart = false;
                    }
                    this.mRunProgress = (mCountSecond * 1000) / TOTAL_TIME;
                    minute = PlayUtil.formateMinute(TOTAL_TIME - mCountSecond);
                    second = PlayUtil.formateSecond(TOTAL_TIME - mCountSecond);
                    distance = String.valueOf((int) GVariable.distance);
                    heat = String.valueOf(GVariable.getCalorie());
                    curChartIndex = this.mRunProgress / 83;
                    break;
                case 3:
                    if (GVariable.calorie >= TOTAL_HEAT) {
                        GVariable.calorie = TOTAL_HEAT;
                        GVariable.isStart = false;
                    }
                    this.mRunProgress = (GVariable.calorie * 1000) / TOTAL_HEAT;
                    minute = PlayUtil.formateMinute(mCountSecond);
                    second = PlayUtil.formateSecond(mCountSecond);
                    distance = String.valueOf((int) GVariable.distance);
                    heat = String.valueOf((TOTAL_HEAT / 1000) - (GVariable.calorie / 1000));
                    curChartIndex = this.mRunProgress / 83;
                    break;
                case 4:
                    Intent intent;
                    if (this.stepTime > 0) {
                        this.stepTime--;
                    } else if (mRunLinkedList.size() > 0) {
                        int listSize = mRunLinkedList.size();
                        for (int i = 0; i < listSize; i++) {
                            PlanStep step = (PlanStep) mRunLinkedList.remove();
                            this.stepTime = step.getTime() * 60;
                            this.stepSpeed = step.getSpeed();
                            this.stepSlope = step.getSlope();
                            this.stepRunedTime += step.getTime();
                            if (this.stepRunedTime > stopTime) {
                                GVariable.gradient = this.stepSlope;
                                GVariable.speed = this.stepSpeed;
                                intent = new Intent(ACTION_PLAN_CHART_DATA);
                                intent.putExtra("index", (mRunList.size() - mRunLinkedList.size()) - 1);
                                this.mContext.sendBroadcast(intent);
                                if (this.stepTime > 0) {
                                    this.stepTime--;
                                }
                            }
                        }
                    } else {
                        intent = new Intent(ACTION_PLAN_CHART_DATA);
                        intent.putExtra("index", mRunList.size());
                        this.mContext.sendBroadcast(intent);
                        this.mRunProgress = 1000;
                        GVariable.isStart = false;
                    }
                    minute = PlayUtil.formateMinute(mCountSecond);
                    second = PlayUtil.formateSecond(mCountSecond);
                    distance = String.valueOf((int) GVariable.distance);
                    heat = String.valueOf(GVariable.getCalorie());
                    break;
            }
            countData();
            sendData(minute, second, distance, heat, curChartIndex);
            return;
        }
        this.stepTime = 0;
        this.stepSpeed = 0;
        this.stepSlope = 0;
        this.stepRunedTime = 0;
    }

    private void countData() {
        if (GVariable.isStart) {
            mCountSecond++;
            BleUtil.countCalorie();
            BleUtil.countDistance();
        }
    }

    private void sendData(String minute, String second, String distance, String heat, int curChartIndex) {
        Intent intent = new Intent(ACTION_BASE_DATA);
        intent.putExtra("minute", minute);
        intent.putExtra("second", second);
        intent.putExtra("distance", distance);
        intent.putExtra("heat", heat);
        intent.putExtra("curChartIndex", curChartIndex);
        this.mContext.sendBroadcast(intent);
    }

    public static void setRunList(ArrayList<PlanStep> runList) {
        mRunList.clear();
        mRunLinkedList.clear();
        mRunList.addAll(runList);
        mRunLinkedList.addAll(runList);
    }

    public static LinkedList<PlanStep> getRunLinkedList() {
        return mRunLinkedList;
    }

    public static void setDistance(int diatance) {
        TOTAL_DISTANCE = diatance;
    }

    public static void setTime(int time) {
        TOTAL_TIME = time;
    }

    public static void setHeat(int heat) {
        TOTAL_HEAT = heat;
    }

    public static void setStopTime(int time) {
        stopTime = time;
    }
}

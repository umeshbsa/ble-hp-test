package com.app.es;

import android.widget.ImageView;

import com.hankang.phone.treadmill.R;

import java.util.ArrayList;

public class PlayUtil {
    private static final String TAG = "PlayUtil";
    private static ArrayList<PlanStep> mRunList = new ArrayList();

    public static void initPlanState(ArrayList<ImageView> imgList, ArrayList<PlanStep> runList, int stopTime) {
        int runedTime = 0;
        for (int i = 0; i < imgList.size(); i++) {
            if (i < runList.size()) {
                PlanStep plan = (PlanStep) runList.get(i);
                runedTime += plan.getTime();
                if (runedTime <= stopTime) {
                    ((ImageView) imgList.get(i)).setImageResource(getGreen(getSpeedBlock(plan.getSpeed())));
                } else {
                    ((ImageView) imgList.get(i)).setImageResource(getGray(getSpeedBlock(plan.getSpeed())));
                }
            } else {
                ((ImageView) imgList.get(i)).setImageResource(getGray(0));
            }
        }
    }

    public static void setPlanState(int index, ArrayList<ImageView> imgList, ArrayList<PlanStep> runList) {
        LogUtil.m294i(TAG, "setPlanState", "index=" + index);
        for (int i = 0; i < imgList.size(); i++) {
            if (i == index) {
                if (i < runList.size()) {
                    PlanStep plan = (PlanStep) runList.get(i);
                    plan.setSpeed(GVariable.speed);
                    ((ImageView) imgList.get(i)).setImageResource(getRed(getSpeedBlock(plan.getSpeed())));
                } else {
                    ((ImageView) imgList.get(i)).setImageResource(getRed(0));
                }
            } else if (i < index) {
                if (i < runList.size()) {
                    ((ImageView) imgList.get(i)).setImageResource(getGreen(getSpeedBlock(((PlanStep) runList.get(i)).getSpeed())));
                } else {
                    ((ImageView) imgList.get(i)).setImageResource(getGreen(0));
                }
            } else if (i < runList.size()) {
                ((ImageView) imgList.get(i)).setImageResource(getGray(getSpeedBlock(((PlanStep) runList.get(i)).getSpeed())));
            } else {
                ((ImageView) imgList.get(i)).setImageResource(getGray(0));
            }
        }
    }

    public static void setFreeState(int index, ArrayList<ImageView> imgList, int speed) {
        /*for (int i = 0; i < imgList.size(); i++) {
            if (i == index) {
                ((ImageView) imgList.get(i)).setImageResource(getRed(getSpeedBlock(speed)));
                PlanStep step = new PlanStep();
                step.setSpeed(speed);
                if (mRunList.size() > index) {
                    mRunList.remove(index);
                    mRunList.add(index, step);
                } else {
                    mRunList.add(step);
                }
            } else if (i >= index) {
                ((ImageView) imgList.get(i)).setImageResource(R.drawable.pro_transparent);
            } else if (i < mRunList.size()) {
                ((ImageView) imgList.get(i)).setImageResource(getGreen(getSpeedBlock(((PlanStep) mRunList.get(i)).getSpeed())));
            } else {
                ((ImageView) imgList.get(i)).setImageResource(getGreen(0));
            }
        }*/
    }

    public static int getSpeedBlock(int speed) {
        int speedBlock = speed / 10;
        if (speed % 10 > 0) {
            return speedBlock + 1;
        }
        return speedBlock;
    }

    public static String formateMinute(int totalSecond) {
        int hour = totalSecond / 3600;
        int minute = (totalSecond % 3600) / 60;
        StringBuilder b = new StringBuilder();
        if (hour < 10) {
            b.append("0" + hour + ":");
        } else {
            b.append(new StringBuilder(String.valueOf(hour)).append(":").toString());
        }
        if (minute < 10) {
            b.append("0" + minute);
        } else {
            b.append(minute);
        }
        return b.toString();
    }

    public static String formateSecond(int totalSecond) {
        int second = totalSecond % 60;
        StringBuilder b = new StringBuilder();
        if (second < 10) {
            b.append("0" + second);
        } else {
            b.append(second);
        }
        return b.toString();
    }

    private static int getGreen(int index) {
        switch (index) {
            case 0:
                return R.drawable.pro_transparent;
            case 1:
                return R.drawable.pro_green_1;
            case 2:
                return R.drawable.pro_green_2;
            case 3:
                return R.drawable.pro_green_3;
            case 4:
                return R.drawable.pro_green_4;
            case 5:
                return R.drawable.pro_green_5;
            case 6:
                return R.drawable.pro_green_6;
            case 7:
                return R.drawable.pro_green_7;
            case 8:
                return R.drawable.pro_green_8;
            case 9:
                return R.drawable.pro_green_9;
            case 10:
                return R.drawable.pro_green_10;
            case 11 /*11*/:
                return R.drawable.pro_green_11;
            case 12:
                return R.drawable.pro_green_12;
            case 13 /*13*/:
                return R.drawable.pro_green_13;
            case 14 /*14*/:
                return R.drawable.pro_green_14;
            default:
                return R.drawable.pro_green_15;
        }
    }

    private static int getGray(int index) {
        switch (index) {
            case 0:
                return R.drawable.pro_transparent;
            case 1:
                return R.drawable.pro_gray_1;
            case 2:
                return R.drawable.pro_gray_2;
            case 3:
                return R.drawable.pro_gray_3;
            case 4:
                return R.drawable.pro_gray_4;
            case 5:
                return R.drawable.pro_gray_5;
            case 6:
                return R.drawable.pro_gray_6;
            case 7:
                return R.drawable.pro_gray_7;
            case 8:
                return R.drawable.pro_gray_8;
            case 9:
                return R.drawable.pro_gray_9;
            case 10:
                return R.drawable.pro_gray_10;
            case 11 /*11*/:
                return R.drawable.pro_gray_11;
            case 12:
                return R.drawable.pro_gray_12;
            case 13 /*13*/:
                return R.drawable.pro_gray_13;
            case 14 /*14*/:
                return R.drawable.pro_gray_14;
            default:
                return R.drawable.pro_gray_15;
        }
    }

    private static int getRed(int index) {
        switch (index) {
            case 0:
                return R.drawable.pro_transparent;
            case 1:
                return R.drawable.pro_red_1;
            case 2:
                return R.drawable.pro_red_2;
            case 3:
                return R.drawable.pro_red_3;
            case 4:
                return R.drawable.pro_red_4;
            case 5:
                return R.drawable.pro_red_5;
            case 6:
                return R.drawable.pro_red_6;
            case 7:
                return R.drawable.pro_red_7;
            case 8:
                return R.drawable.pro_red_8;
            case 9:
                return R.drawable.pro_red_9;
            case 10:
                return R.drawable.pro_red_10;
            case 11 /*11*/:
                return R.drawable.pro_red_11;
            case 12:
                return R.drawable.pro_red_12;
            case 13 /*13*/:
                return R.drawable.pro_red_13;
            case 14 /*14*/:
                return R.drawable.pro_red_14;
            default:
                return R.drawable.pro_red_15;
        }
    }

    public static int getStepIcon(int index) {
        switch (index) {
            case 0:
                return R.drawable.step_transparent;
            case 1:
                return R.drawable.step_1;
            case 2:
                return R.drawable.step_2;
            case 3:
                return R.drawable.step_3;
            case 4:
                return R.drawable.step_4;
            case 5:
                return R.drawable.step_5;
            case 6:
                return R.drawable.step_6;
            case 7:
                return R.drawable.step_7;
            case 8:
                return R.drawable.step_8;
            case 9:
                return R.drawable.step_9;
            case 10:
                return R.drawable.step_10;
            case 11 /*11*/:
                return R.drawable.step_11;
            case 12:
                return R.drawable.step_12;
            case 13 /*13*/:
                return R.drawable.step_13;
            case 14 /*14*/:
                return R.drawable.step_14;
            default:
                return R.drawable.step_15;
        }
    }
}

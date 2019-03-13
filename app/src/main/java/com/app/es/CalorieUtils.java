package com.app.es;

public class CalorieUtils {
    public static Double calculateCalorieByMinutes(String weight, String time, String speed, String slope) {
        return Double.valueOf(MathHelper.mul((((Double.valueOf(weight).doubleValue() * Double.valueOf(time).doubleValue()) / 60.0d) * 1.25d) * Double.valueOf(speed).doubleValue(), (double) ((Integer.valueOf(slope).intValue() / 100) + 1), 4));
    }

    public static Double calculateCalorieBySecond(float weight, float time, float speed, int slope) {
        return Double.valueOf(Double.valueOf(MathHelper.mul(((((Double.valueOf((double) weight).doubleValue() * Double.valueOf((double) time).doubleValue()) / 60.0d) / 60.0d) * 1.25d) * Double.valueOf((double) speed).doubleValue(), (double) ((Integer.valueOf(slope).intValue() / 100) + 1), 4)).doubleValue() * 1000.0d);
    }

    public static Integer calculateCalorieByMinutes(String weight, String time, String speed, Integer slope) {
        return Integer.valueOf((int) MathHelper.mul(((((Double.valueOf(weight).doubleValue() * Double.valueOf(time).doubleValue()) / 60.0d) * 30.0d) / (400.0d / ((Double.valueOf(speed).doubleValue() * 1000.0d) / 60.0d))) * 1000.0d, (double) (slope.intValue() + 1)));
    }

    public static Integer calculateDistanceByMinutes(String time, String speed) {
        return Integer.valueOf((int) MathHelper.mul(Double.valueOf(speed).doubleValue() * 1000.0d, Double.valueOf(time).doubleValue() / 60.0d));
    }
}

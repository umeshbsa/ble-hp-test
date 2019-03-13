package com.app.es;

import java.math.BigDecimal;

public class MathHelper {
    public static Double add(double v1, double v2) {
        return Double.valueOf(new BigDecimal(Double.toString(v1)).add(new BigDecimal(Double.toString(v2))).doubleValue());
    }

    public static double add(double v1, double v2, int scale) {
        return new BigDecimal(Double.toString(v1)).setScale(scale, 4).add(new BigDecimal(Double.toString(v2)).setScale(scale, 4)).doubleValue();
    }

    public static double sub(double v1, double v2) {
        return new BigDecimal(Double.toString(v1)).subtract(new BigDecimal(Double.toString(v2))).setScale(1, 4).doubleValue();
    }

    public static double mul(double v1, double v2) {
        return new BigDecimal(Double.toString(v1)).multiply(new BigDecimal(Double.toString(v2))).setScale(1, 4).doubleValue();
    }

    public static double mul(double v1, double v2, int scale) {
        return new BigDecimal(Double.toString(v1)).multiply(new BigDecimal(Double.toString(v2))).doubleValue();
    }

    public static double div(double v1, double v2) {
        return div(v1, v2, 1);
    }

    public static double div(double v1, double v2, int scale) {
        if (scale >= 0) {
            return new BigDecimal(Double.toString(v1)).divide(new BigDecimal(Double.toString(v2)), scale, 4).doubleValue();
        }
        throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }

    public static double round(double v, int scale) {
        if (scale >= 0) {
            return new BigDecimal(Double.toString(v)).divide(new BigDecimal("1")).doubleValue();
        }
        throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
}

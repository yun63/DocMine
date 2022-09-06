package com.jengine.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 数字相关静态方法类
 *
 * @author leiyunfei
 * @time 2020-06-29 15:13
 */
public final class NumberUtil {
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 把两个32位整数组合成64位长整数
     *
     * @param hi 高32位
     * @param lo 低32位
     * @return long 64
     */
    public static long join64(int hi, int lo) {
        long h = ((long) hi << 32) & 0xFFFFFFFF00000000L;
        long l = lo & 0x00000000FFFFFFFFL;
        return h | l;
    }

    /**
     * @param n 64位长整数
     * @return 高32位部分整数
     */
    public static int h32(long n) {
        return (int) (n >> 32);
    }

    /**
     * @param n 64位长整数
     * @return 低32部分整数
     */
    public static int l32(long n) {
        n &= 0xFFFFFFFFL;
        return (int) n;
    }

    public static int parseInt(Object input, int defaultValue) {
        if (input == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(input.toString());
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    /**
     * 从一列列（>=2)个数据中，返回最大的那个
     *
     * @param value1 第一个long整数
     * @param value2 第二个long整数
     * @param values 后续一系列long整数
     * @return 返回所有long整数中最大的那一个
     */
    public static long max(long value1, long value2, long... values) {
        long max = Math.max(value1, value2);
        for (long value : values) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 从一列列（>=2)个数据中，返回最小的那个
     *
     * @param value1 第一个long整数
     * @param value2 第二个long整数
     * @param values 后续一系列long整数
     * @return 返回所有long整数中最小的那一个
     */
    public static long min(long value1, long value2, long... values) {
        long min = Math.min(value1, value2);
        for (long value : values) {
            if (min > value) {
                min = value;
            }
        }
        return min;
    }

    /**
     * @param d 浮点数
     * @return 向上取整
     */
    public static long ceil(double d) {
        return Math.round(Math.ceil(d));
    }

    /**
     * @param d 浮点数
     * @return 向下取整
     */
    public static long floor(double d) {
        return Math.round(d - 0.5d);
    }

    public static List<Integer> makeSequence(int begin, int end) {
        List<Integer> sequence = new ArrayList<>();
        for (int i = begin; i != end; i++) {
            sequence.add(i);
        }
        return sequence;
    }

    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double d1, double d2) {
        return div(d1, d2, DEF_DIV_SCALE);
    }

    public static double div(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

package com.niujiacun.music.common.utils;

/**
 * Created by Administrator on 2017/11/25.
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }
    public static String getDbType() {
        return ((String) contextHolder.get());
    }
    public static void clearDbType() {
        contextHolder.remove();
    }
}

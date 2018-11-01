package com.xiangri.dongdong.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.disklrucache.DiskLruCache;

public class SpUtil {
 
    private static volatile SpUtil inserter;
    private Context context;
    private static String SPCONFIG = "config";
    private static SharedPreferences sp;
    private static SharedPreferences.Editor edit;
 
    private SpUtil(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SPCONFIG, context.MODE_PRIVATE);
        edit = sp.edit();
    }
 
    public static SpUtil getInserter(Context con) {
        if (inserter == null) {
            synchronized (SpUtil.class) {
                if (null == inserter) {
                    inserter = new SpUtil(con);
                }
            }
        }
        return inserter;
    }
 
    // 获取sharedpreferences中的数据
    public  Object getSpData( String key, Object value) {
        String type = value.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) value);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) value);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) value);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) value);
        } else if ("String".equals(type)) {
            return sp.getString(key, (String) value);
        }
        return null;
    }
 
    public SharedPreferences.Editor saveData(String key, Object value) {
        String type = value.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            return edit.putInt(key, (Integer) value);
        } else if ("Float".equals(type)) {
            return edit.putFloat(key, (Float) value);
        } else if ("Boolean".equals(type)) {
            return edit.putBoolean(key, (Boolean) value);
        } else if ("Long".equals(type)) {
            return edit.putLong(key, (Long) value);
        } else if ("String".equals(type)) {
            return edit.putString(key, (String) value);
        }
        return null;
 
    }
 
    public  boolean commit() {
        return edit.commit();
    }
}
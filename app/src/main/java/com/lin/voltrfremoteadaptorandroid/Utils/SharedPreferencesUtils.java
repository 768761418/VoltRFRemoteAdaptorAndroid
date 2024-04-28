package com.lin.voltrfremoteadaptorandroid.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private SharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(ApplicationSetting.sharedPreferencesFileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPreferencesUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtils(context.getApplicationContext());
        }
        return instance;
    }

//    读数据
    public int loadIntData(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
    public Boolean loadBooleanData(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    //存储数据
    public void saveIntData(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveBooleanData(String key,Boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }


}

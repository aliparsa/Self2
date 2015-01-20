package com.pishgamanasia.self2.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by parsa on 2014-12-07.
 */
public class SettingHelper {
    Context context;

    public SettingHelper(Context context) {
        this.context = context;

    }

    public void setOption(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getOption(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,null);
    }
}

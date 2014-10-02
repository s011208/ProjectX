
package com.bj4.yhh.projectx;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String KEY_SETTING_CHANGED = "key_setting_changed";

    private static final String KEY_GRID_COLOR = "key_grid_color";

    public static final int GRID_COLOR_BLUE = 1;

    public static final int GRID_COLOR_GREY = 2;

    public static final int GRID_COLOR_BLACK = 3;

    private static SharedPreferenceManager sInstance;

    public synchronized static SharedPreferenceManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferenceManager(context);
        }
        return sInstance;
    }

    private Context mContext;

    private SharedPreferences mPref;

    private SharedPreferenceManager(Context context) {
        mContext = context.getApplicationContext();
        mPref = mContext.getSharedPreferences(SharedPreferenceManager.class.getName(),
                Context.MODE_PRIVATE);
    }

    public int getGridColor() {
        return mPref.getInt(KEY_GRID_COLOR, GRID_COLOR_BLUE);
    }

    public void setGridColor(int colorNumber) {
        mPref.edit().putInt(KEY_GRID_COLOR, colorNumber).commit();
    }

    public boolean hasSettingChanged() {
        return mPref.getBoolean(KEY_SETTING_CHANGED, false);
    }

    public void settingChanged(boolean changed) {
        mPref.edit().putBoolean(KEY_SETTING_CHANGED, changed).commit();
    }
}

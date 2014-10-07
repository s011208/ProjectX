
package com.bj4.yhh.projectx;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String KEY_SETTING_CHANGED = "key_setting_changed";

    // grid color
    private static final String KEY_GRID_COLOR = "key_grid_color";

    public static final int GRID_COLOR_BLUE = 1;

    public static final int GRID_COLOR_GREY = 2;

    public static final int GRID_COLOR_BLACK = 3;

    // long click delete
    public static final String KEY_LONG_CLICK_DELETE = "key_long_click_delete";

    // display lines
    public static final String KEY_DISPLAY_LINES = "key_display_lines";

    public static final int DEFAULT_DISPLAY_LINES = 0;

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

    public boolean isLongClickDeleteEnable() {
        return mPref.getBoolean(KEY_LONG_CLICK_DELETE, true);
    }

    public void setLongClickDeleteEnable(boolean enable) {
        mPref.edit().putBoolean(KEY_LONG_CLICK_DELETE, enable).apply();
    }

    public int getDisplayLines() {
        return mPref.getInt(KEY_DISPLAY_LINES, DEFAULT_DISPLAY_LINES);
    }

    public void setDisplayLines(int lines) {
        if (lines < 0)
            lines = DEFAULT_DISPLAY_LINES;
        mPref.edit().putInt(KEY_DISPLAY_LINES, lines).commit();
    }
}

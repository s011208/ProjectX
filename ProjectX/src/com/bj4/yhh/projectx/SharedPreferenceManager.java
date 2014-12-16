
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

    // text size
    // 39
    public static final String KEY_39_TEXT_SIZE = "key_39_textsize";

    public static final String KEY_39_NUMBER_WIDTH = "key_39_number_width";

    // 49
    public static final String KEY_49_TEXT_SIZE = "key_49_textsize";

    public static final String KEY_49_NUMBER_WIDTH = "key_49_number_width";

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

    public void set39TextSize(int textSize) {
        mPref.edit().putInt(KEY_39_TEXT_SIZE, textSize).commit();
    }

    public int get39TextSize() {
        return mPref.getInt(KEY_39_TEXT_SIZE,
                mContext.getResources().getInteger(R.integer.table_text_size_39));
    }

    public void set49TextSize(int textSize) {
        mPref.edit().putInt(KEY_49_TEXT_SIZE, textSize).commit();
    }

    public int get49TextSize() {
        return mPref.getInt(KEY_49_TEXT_SIZE,
                mContext.getResources().getInteger(R.integer.table_text_size_49));
    }

    public void set39NumberWidth(float width) {
        mPref.edit().putFloat(KEY_39_NUMBER_WIDTH, width).commit();
    }

    public float get39NumberWidth() {
        return mPref.getFloat(KEY_39_NUMBER_WIDTH,
                mContext.getResources().getDimension(R.dimen.table_number_width_39));
    }

    public void set49NumberWidth(float width) {
        mPref.edit().putFloat(KEY_49_NUMBER_WIDTH, width).commit();
    }

    public float get49NumberWidth() {
        return mPref.getFloat(KEY_49_NUMBER_WIDTH,
                mContext.getResources().getDimension(R.dimen.table_number_width_49));
    }
}

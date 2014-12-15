
package com.bj4.yhh.projectx;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

public class SettingPreference extends PreferenceFragment {
    private SharedPreferenceManager mSharedPreferenceManager;

    private ListPreference mGridColorPreference;

    private CheckBoxPreference mLongClickRemovePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferenceManager = SharedPreferenceManager.getInstance(getActivity()
                .getApplicationContext());
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference);
        // grid color
        mGridColorPreference = (ListPreference)findPreference("grid_color");
        final int currentColor = mSharedPreferenceManager.getGridColor();
        switch (currentColor) {
            case SharedPreferenceManager.GRID_COLOR_BLUE:
                mGridColorPreference.setDefaultValue("1");
                break;
            case SharedPreferenceManager.GRID_COLOR_BLACK:
                mGridColorPreference.setDefaultValue("0");
                break;
            case SharedPreferenceManager.GRID_COLOR_GREY:
                mGridColorPreference.setDefaultValue("2");
                break;
            default:
                mGridColorPreference.setDefaultValue("1");
                break;
        }
        mGridColorPreference
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                    @Override
                    public boolean onPreferenceChange(Preference preference, Object value) {
                        final String result = (String)value;
                        if ("0".equals(result)) {
                            mSharedPreferenceManager
                                    .setGridColor(SharedPreferenceManager.GRID_COLOR_BLACK);
                        } else if ("1".equals(result)) {
                            mSharedPreferenceManager
                                    .setGridColor(SharedPreferenceManager.GRID_COLOR_BLUE);
                        } else if ("2".equals(result)) {
                            mSharedPreferenceManager
                                    .setGridColor(SharedPreferenceManager.GRID_COLOR_GREY);
                        }
                        mSharedPreferenceManager.settingChanged(true);
                        return true;
                    }
                });

        // long click delete
        mLongClickRemovePreference = (CheckBoxPreference)findPreference("allow_long_click_delete");
        mLongClickRemovePreference.setDefaultValue(mSharedPreferenceManager
                .isLongClickDeleteEnable());
        mLongClickRemovePreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mSharedPreferenceManager.setLongClickDeleteEnable((Boolean)newValue);
                return true;
            }
        });
    }
}

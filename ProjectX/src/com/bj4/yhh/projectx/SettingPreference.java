
package com.bj4.yhh.projectx;

import com.bj4.yhh.projectx.lot.Utils;
import com.bj4.yhh.projectx.lot.blot.BLotParseService;
import com.bj4.yhh.projectx.lot.hk6.HK6ParseService;
import com.bj4.yhh.projectx.lot.lt539.LT539ParseService;

import android.app.Activity;
import android.content.Intent;
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
                mGridColorPreference.setValueIndex(1);
                break;
            case SharedPreferenceManager.GRID_COLOR_BLACK:
                mGridColorPreference.setValueIndex(0);
                break;
            case SharedPreferenceManager.GRID_COLOR_GREY:
                mGridColorPreference.setValueIndex(2);
                break;
            default:
                mGridColorPreference.setValueIndex(1);
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
        mLongClickRemovePreference.setChecked(mSharedPreferenceManager.isLongClickDeleteEnable());
        mLongClickRemovePreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mSharedPreferenceManager.setLongClickDeleteEnable((Boolean)newValue);
                return true;
            }
        });
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if ("reset_all".equals(preference.getKey())) {
            // reset all data and reload
            final Activity activity = getActivity();
            activity.stopService(new Intent(activity, HK6ParseService.class));
            activity.stopService(new Intent(activity, LT539ParseService.class));
            activity.stopService(new Intent(activity, BLotParseService.class));
            // clear all db in advance
            Utils.startUpdateAllService(activity);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}

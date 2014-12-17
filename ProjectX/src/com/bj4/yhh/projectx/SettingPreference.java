
package com.bj4.yhh.projectx;

import com.bj4.yhh.projectx.lot.LotteryDatabaseHelper;
import com.bj4.yhh.projectx.lot.Utils;
import com.bj4.yhh.projectx.lot.blot.BLotParseService;
import com.bj4.yhh.projectx.lot.hk6.HK6ParseService;
import com.bj4.yhh.projectx.lot.lt539.LT539ParseService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
                            GATracker.sendEvent(getActivity(), GATracker.CATEGORY_SETTINGS,
                                    GATracker.ACTION_GRID_COLOR, GATracker.LABEL_GRID_COLOR_BLACK);
                        } else if ("1".equals(result)) {
                            mSharedPreferenceManager
                                    .setGridColor(SharedPreferenceManager.GRID_COLOR_BLUE);
                            GATracker.sendEvent(getActivity(), GATracker.CATEGORY_SETTINGS,
                                    GATracker.ACTION_GRID_COLOR, GATracker.LABEL_GRID_COLOR_BLUE);
                        } else if ("2".equals(result)) {
                            mSharedPreferenceManager
                                    .setGridColor(SharedPreferenceManager.GRID_COLOR_GREY);
                            GATracker.sendEvent(getActivity(), GATracker.CATEGORY_SETTINGS,
                                    GATracker.ACTION_GRID_COLOR, GATracker.LABEL_GRID_COLOR_GREY);
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
        final String key = preference.getKey();
        if ("reset_all".equals(key)) {
            // reset all data and reload
            final Activity activity = getActivity();
            new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            activity.stopService(new Intent(activity, HK6ParseService.class));
                            activity.stopService(new Intent(activity, LT539ParseService.class));
                            activity.stopService(new Intent(activity, BLotParseService.class));
                            // clear all db in advance
                            LotteryDatabaseHelper.getInstance(activity).clearAllData();
                            mSharedPreferenceManager.settingChanged(true);
                            Utils.startUpdateAllService(activity);
                            GATracker.sendEvent(activity, GATracker.CATEGORY_SETTINGS,
                                    GATracker.ACTION_RESET_ALL, null);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).setIcon(android.R.drawable.ic_delete)
                    .setTitle(R.string.delete_all_data)
                    .setMessage(R.string.settings_reset_dialog_summary).create().show();
        } else if ("39_text_size".equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setItems(R.array.settings_39_text_size_items, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSharedPreferenceManager.set39TextSize(Integer.valueOf(getActivity()
                                    .getResources().getStringArray(
                                            R.array.settings_39_text_size_items)[which]));
                            mSharedPreferenceManager.settingChanged(true);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).setTitle(R.string.settings_39_text_size_title).create().show();
        } else if ("49_text_size".equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setItems(R.array.settings_49_text_size_items, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSharedPreferenceManager.set49TextSize(Integer.valueOf(getActivity()
                                    .getResources().getStringArray(
                                            R.array.settings_49_text_size_items)[which]));
                            mSharedPreferenceManager.settingChanged(true);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).setTitle(R.string.settings_49_text_size_title).create().show();
        } else if ("39_number_width".equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setItems(R.array.settings_39_number_width_items, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSharedPreferenceManager.set39NumberWidth(Integer.valueOf(getActivity()
                                    .getResources().getStringArray(
                                            R.array.settings_39_number_width_items)[which])
                                    * getActivity().getResources().getDisplayMetrics().density);
                            mSharedPreferenceManager.settingChanged(true);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).setTitle(R.string.settings_39_number_width_title).create().show();
        } else if ("49_number_width".equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setItems(R.array.settings_49_number_width_items, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSharedPreferenceManager.set49NumberWidth(Integer.valueOf(getActivity()
                                    .getResources().getStringArray(
                                            R.array.settings_49_number_width_items)[which])
                                    * getActivity().getResources().getDisplayMetrics().density);
                            mSharedPreferenceManager.settingChanged(true);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).setTitle(R.string.settings_49_number_width_title).create().show();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}

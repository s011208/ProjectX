
package com.bj4.yhh.projectx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;

public class SettingActivity extends Activity {

    private RadioGroup mColumnGridBgGroup;

    private SharedPreferenceManager mSharedPreferenceManager;

    private CheckBox mLongClickDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        mSharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
        initComponents();
    }

    private void initComponents() {
        mLongClickDelete = (CheckBox)findViewById(R.id.allow_long_click_delte);
        mLongClickDelete.setChecked(mSharedPreferenceManager.isLongClickDeleteEnable());
        mLongClickDelete.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSharedPreferenceManager.setLongClickDeleteEnable(isChecked);
            }
        });
        mColumnGridBgGroup = (RadioGroup)findViewById(R.id.column_grid_group);
        final int currentColor = mSharedPreferenceManager.getGridColor();
        switch (currentColor) {
            case SharedPreferenceManager.GRID_COLOR_BLUE:
                mColumnGridBgGroup.check(R.id.blue);
                break;
            case SharedPreferenceManager.GRID_COLOR_BLACK:
                mColumnGridBgGroup.check(R.id.black);
                break;
            case SharedPreferenceManager.GRID_COLOR_GREY:
                mColumnGridBgGroup.check(R.id.grey);
                break;
        }
        mColumnGridBgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.blue:
                        mSharedPreferenceManager
                                .setGridColor(SharedPreferenceManager.GRID_COLOR_BLUE);
                        break;
                    case R.id.black:
                        mSharedPreferenceManager
                                .setGridColor(SharedPreferenceManager.GRID_COLOR_BLACK);
                        break;
                    case R.id.grey:
                        mSharedPreferenceManager
                                .setGridColor(SharedPreferenceManager.GRID_COLOR_GREY);
                        break;
                }
                mSharedPreferenceManager.settingChanged(true);
            }
        });
    }
}

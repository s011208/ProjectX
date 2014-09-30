
package com.bj4.yhh.projectx.lot;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.bj4.yhh.projectx.R;

public abstract class AddOrMinusFragment extends Fragment implements
        RadioGroup.OnCheckedChangeListener {

    private View mRootView;

    private ListView mDataList;

    private AddOrMinusAdapter mAdapter;

    private RadioGroup mAdd, mMinus, mDirections;

    private int mValue = 0;

    public AddOrMinusFragment() {
    }

    public abstract int getGameType();

    private void initComponents(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_or_minus_table, container, false);
        mDataList = (ListView)mRootView.findViewById(R.id.data_list);
        mAdapter = new AddOrMinusAdapter(getActivity(), getGameType());
        mDataList.setAdapter(mAdapter);
        mAdd = (RadioGroup)mRootView.findViewById(R.id.add_group);
        mMinus = (RadioGroup)mRootView.findViewById(R.id.minus_group);
        mDirections = (RadioGroup)mRootView.findViewById(R.id.direction_group);
        mDirections.check(R.id.previous);
        mAdd.setOnCheckedChangeListener(this);
        mMinus.setOnCheckedChangeListener(this);
        mDirections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.previous:
                        mAdapter.setComparedDirection(AddOrMinusAdapter.COMPARED_TO_PREVIOUS);
                        break;
                    case R.id.next:
                        mAdapter.setComparedDirection(AddOrMinusAdapter.COMPARED_TO_NEXT);
                        break;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            initComponents(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == mAdd) {
            mMinus.setOnCheckedChangeListener(null);
            mMinus.clearCheck();
            mMinus.setOnCheckedChangeListener(this);
        } else if (group == mMinus) {
            mAdd.setOnCheckedChangeListener(null);
            mAdd.clearCheck();
            mAdd.setOnCheckedChangeListener(this);
        }
        switch (checkedId) {
            case R.id.add1:
                mValue = 1;
                break;
            case R.id.add2:
                mValue = 2;
                break;
            case R.id.add3:
                mValue = 3;
                break;
            case R.id.add4:
                mValue = 4;
                break;
            case R.id.add5:
                mValue = 5;
                break;
            case R.id.add6:
                mValue = 6;
                break;
            case R.id.add7:
                mValue = 7;
                break;
            case R.id.add8:
                mValue = 8;
                break;
            case R.id.add9:
                mValue = 9;
                break;
            case R.id.add10:
                mValue = 10;
                break;
            case R.id.minus1:
                mValue = -1;
                break;
            case R.id.minus2:
                mValue = -2;
                break;
            case R.id.minus3:
                mValue = -3;
                break;
            case R.id.minus4:
                mValue = -4;
                break;
            case R.id.minus5:
                mValue = -5;
                break;
            case R.id.minus6:
                mValue = -6;
                break;
            case R.id.minus7:
                mValue = -7;
                break;
            case R.id.minus8:
                mValue = -8;
                break;
            case R.id.minus9:
                mValue = -9;
                break;
            case R.id.minus10:
                mValue = -10;
                break;
        }
        mAdapter.setComparedValue(mValue);
    }
}

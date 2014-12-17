
package com.bj4.yhh.projectx.lot;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.SharedPreferenceManager;
import com.bj4.yhh.projectx.lot.dialogs.DeleteDataConfirmDialog;

public abstract class AddOrMinusFragment extends Fragment implements UpdatableFragment,
        RadioGroup.OnCheckedChangeListener {

    private View mRootView;

    private ListView mDataList;

    private AddOrMinusAdapter mAdapter;

    private RadioGroup mAdd, mMinus;

    private Button mMoveToTop, mMoveToBottom;

    private CheckBox mShowSubTotalOnly;

    private int mValue = 0;

    public AddOrMinusFragment() {
    }

    public abstract int getGameType();

    public void updateContent() {
        mAdapter.notifyDataSetChanged();
    }

    private void initComponents(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_or_minus_table, container, false);
        mDataList = (ListView)mRootView.findViewById(R.id.data_list);
        mAdapter = new AddOrMinusAdapter(getActivity(), getGameType());
        mDataList.setAdapter(mAdapter);
        mDataList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position,
                    long arg3) {
                SharedPreferenceManager sPref = SharedPreferenceManager.getInstance(getActivity());
                if (sPref.isLongClickDeleteEnable() == false)
                    return false;
                DeleteDataConfirmDialog dialogFragment = DeleteDataConfirmDialog
                        .newInstance(new DeleteDataConfirmDialog.Callback() {

                            @Override
                            public void doPositive() {
                                LotteryData data = mAdapter.getItem(position);
                                LotteryDatabaseHelper.getInstance(getActivity()).deleteData(
                                        getGameType(), data.mNumber);
                                updateContent();
                                Toast.makeText(
                                        getActivity(),
                                        getActivity().getString(R.string.delete_complete_toast)
                                                + " " + data.mNumber, Toast.LENGTH_LONG).show();
                            }
                        });
                dialogFragment.show(getFragmentManager(), "DeleteDataConfirmDialog");
                return true;
            }
        });
        mAdd = (RadioGroup)mRootView.findViewById(R.id.add_group);
        mMinus = (RadioGroup)mRootView.findViewById(R.id.minus_group);
        mAdd.setOnCheckedChangeListener(this);
        mMinus.setOnCheckedChangeListener(this);
        mMoveToTop = (Button)mRootView.findViewById(R.id.move_to_top);
        mMoveToTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDataList.setSelection(0);
            }
        });
        mMoveToBottom = (Button)mRootView.findViewById(R.id.move_to_bottom);
        mMoveToBottom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDataList.setSelection(mAdapter.getCount());
            }
        });
        mShowSubTotalOnly = (CheckBox)mRootView.findViewById(R.id.show_sub_total_only);
        if (mShowSubTotalOnly != null) {
            mShowSubTotalOnly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean b) {
                    mAdapter.setShowSubTotal(b);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
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

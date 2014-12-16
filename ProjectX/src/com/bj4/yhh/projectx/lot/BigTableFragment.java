
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.SharedPreferenceManager;
import com.bj4.yhh.projectx.lot.dialogs.DeleteDataConfirmDialog;

public abstract class BigTableFragment extends Fragment implements BigTableAdapter.Callback,
        UpdatableFragment {

    private View mRootView;

    private ListView mDataList;

    private LinearLayout mHeader, mFooter;

    private BigTableAdapter mAdapter;

    private int mTotalNumber;

    private Button mMoveToTop, mMoveToBottom;

    public BigTableFragment() {
    }

    public abstract int getGameType();

    public abstract int getFragmentType();

    private void initComponents(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.big_table, container, false);
        mDataList = (ListView)mRootView.findViewById(R.id.data_list);
        mAdapter = new BigTableAdapter(getActivity(), getGameType(), getFragmentType(), this);
        mDataList.setAdapter(mAdapter);
        mDataList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position,
                    long arg3) {
                SharedPreferenceManager sPref = SharedPreferenceManager.getInstance(getActivity());
                if (sPref.isLongClickDeleteEnable() == false)
                    return false;
                final LotteryData data = mAdapter.getItem(position);
                if (data.mIsSubTotal) {
                    return false;
                }
                DeleteDataConfirmDialog dialogFragment = DeleteDataConfirmDialog
                        .newInstance(new DeleteDataConfirmDialog.Callback() {

                            @Override
                            public void doPositive() {

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
        mHeader = (LinearLayout)mRootView.findViewById(R.id.data_list_header);
        mFooter = (LinearLayout)mRootView.findViewById(R.id.data_list_footer);
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
        createHeader();
    }

    private void createFooter(HashMap<Integer, Integer> resultMap) {
        mFooter.removeAllViews();
        Context context = getActivity();
        if (context == null) {
            return;
        }
        mTotalNumber = LotteryData.getTotalNumber(getGameType());
        int tableTextSize = (int)(BigTableAdapter.getTableTextSize(context, mTotalNumber) * 0.75f);
        int tableDateWidth = (int)BigTableAdapter.getTableDateWidth(context, mTotalNumber);
        int tableNumberWidth = BigTableAdapter.getTableNumberWidth(context, mTotalNumber);
        ArrayList<Integer> headerData = null;
        switch (getFragmentType()) {
            case MainActivity.FRAGMENT_TYPE_LAST:
                headerData = Utils.getLastList(mTotalNumber);
                break;
            case MainActivity.FRAGMENT_TYPE_COMBINATION:
                headerData = Utils.getCombinationList(mTotalNumber);
                break;
            case MainActivity.FRAGMENT_TYPE_ORDERED:
                headerData = Utils.getOrderedList(mTotalNumber);
                break;
        }

        TextView date = new TextView(context);
        date.setTextSize(TypedValue.COMPLEX_UNIT_SP, tableTextSize);
        date.setEllipsize(TruncateAt.END);
        date.setGravity(Gravity.CENTER);
        date.setBackgroundResource(R.drawable.header_column_bg);
        date.setText(R.string.sub_total);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(tableDateWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mFooter.addView(date, ll);
        for (int i = 0; i < mTotalNumber; i++) {
            TextView txt = new TextView(context);
            txt.setSingleLine();
            txt.setGravity(Gravity.CENTER);
            txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, tableTextSize);
            txt.setBackgroundResource(R.drawable.header_column_bg);
            LinearLayout.LayoutParams tl = new LinearLayout.LayoutParams(tableNumberWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mFooter.addView(txt, tl);
            txt.setText(Utils.NUMBER_FORMATTER.format(resultMap.get(headerData.get(i))));
        }
    }

    private void createHeader() {
        mHeader.removeAllViews();
        Context context = getActivity();
        if (context == null) {
            return;
        }
        mTotalNumber = LotteryData.getTotalNumber(getGameType());
        int tableTextSize = BigTableAdapter.getTableTextSize(context, mTotalNumber);
        int tableDateWidth = BigTableAdapter.getTableDateWidth(context, mTotalNumber);
        int tableNumberWidth = BigTableAdapter.getTableNumberWidth(context, mTotalNumber);
        ArrayList<Integer> headerData = null;
        switch (getFragmentType()) {
            case MainActivity.FRAGMENT_TYPE_LAST:
                headerData = Utils.getLastList(mTotalNumber);
                break;
            case MainActivity.FRAGMENT_TYPE_COMBINATION:
                headerData = Utils.getCombinationList(mTotalNumber);
                break;
            case MainActivity.FRAGMENT_TYPE_ORDERED:
                headerData = Utils.getOrderedList(mTotalNumber);
                break;
        }

        TextView date = new TextView(context);
        date.setTextSize(TypedValue.COMPLEX_UNIT_SP, tableTextSize);
        date.setEllipsize(TruncateAt.END);
        date.setGravity(Gravity.CENTER);
        date.setBackgroundResource(R.drawable.header_column_bg);
        date.setText(R.string.date);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(tableDateWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mHeader.addView(date, ll);
        for (int i = 0; i < mTotalNumber; i++) {
            TextView txt = new TextView(context);
            txt.setSingleLine();
            txt.setGravity(Gravity.CENTER);
            txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, tableTextSize);
            txt.setBackgroundResource(R.drawable.header_column_bg);
            LinearLayout.LayoutParams tl = new LinearLayout.LayoutParams(tableNumberWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mHeader.addView(txt, tl);
            txt.setText(Utils.NUMBER_FORMATTER.format(headerData.get(i)));
        }
    }

    public void onCalculate(HashMap<Integer, Integer> resultMap) {
        createFooter(resultMap);
    }

    public void updateContent() {
        createHeader();
        mAdapter = new BigTableAdapter(getActivity(), getGameType(), getFragmentType(), this);
        mDataList.setAdapter(mAdapter);
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
}

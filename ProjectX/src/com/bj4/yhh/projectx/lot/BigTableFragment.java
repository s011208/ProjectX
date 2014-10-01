
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.R;

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
        Resources r = context.getResources();
        float tableTextSize = r.getDimension(R.dimen.table_text_size) * 0.75f;
        int tableDateWidth = (int)r.getDimension(R.dimen.table_date_width);
        int tableNumberWidth = (int)r.getDimension(R.dimen.table_number_width);
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
        date.setTextSize(tableTextSize);
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
            txt.setTextSize(tableTextSize);
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
        Resources r = context.getResources();
        float tableTextSize = r.getDimension(R.dimen.table_text_size);
        int tableDateWidth = (int)r.getDimension(R.dimen.table_date_width);
        int tableNumberWidth = (int)r.getDimension(R.dimen.table_number_width);
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
        date.setTextSize(tableTextSize);
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
            txt.setTextSize(tableTextSize);
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
        mAdapter.notifyDataSetChanged();
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

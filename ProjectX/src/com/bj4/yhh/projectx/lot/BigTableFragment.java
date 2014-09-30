
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.R;

public abstract class BigTableFragment extends Fragment {

    private View mRootView;

    private ListView mDataList;

    private LinearLayout mHeader;

    private BigTableAdapter mAdapter;

    private int mTotalNumber;

    public BigTableFragment() {
    }

    public abstract int getGameType();

    public abstract int getFragmentType();

    private void initComponents(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.big_table, container, false);
        mDataList = (ListView)mRootView.findViewById(R.id.data_list);
        mAdapter = new BigTableAdapter(getActivity(), getGameType(), getFragmentType());
        mDataList.setAdapter(mAdapter);
        mHeader = (LinearLayout)mRootView.findViewById(R.id.data_list_header);
        createHeader();
    }

    private void createHeader() {
        Context context = getActivity();
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
        date.setBackgroundResource(R.drawable.column_bg);
        date.setText("¤é´Á");
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(tableDateWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mHeader.addView(date, ll);
        for (int i = 0; i < mTotalNumber; i++) {
            TextView txt = new TextView(context);
            txt.setSingleLine();
            txt.setGravity(Gravity.CENTER);
            txt.setTextSize(tableTextSize);
            txt.setBackgroundResource(R.drawable.column_bg);
            LinearLayout.LayoutParams tl = new LinearLayout.LayoutParams(tableNumberWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mHeader.addView(txt, tl);
            txt.setText(Utils.NUMBER_FORMATTER.format(headerData.get(i)));
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
}

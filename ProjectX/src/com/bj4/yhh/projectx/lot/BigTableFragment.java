
package com.bj4.yhh.projectx.lot;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bj4.yhh.projectx.R;

public abstract class BigTableFragment extends Fragment {

    private View mRootView;

    private ListView mDataList;
    
    private LinearLayout mHeader;

    private BigTableAdapter mAdapter;

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

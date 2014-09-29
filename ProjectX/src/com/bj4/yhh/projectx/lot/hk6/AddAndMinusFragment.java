
package com.bj4.yhh.projectx.lot.hk6;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.R;

public class AddAndMinusFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView;

    private ListView mDataList;

    private AddAndMinusAdapter mAdapter;

    public static AddAndMinusFragment getNewInstance(int sectionNumber) {
        AddAndMinusFragment fragment = new AddAndMinusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AddAndMinusFragment() {
    }

    private void initComponents(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_and_minus_table, container, false);
        mDataList = (ListView)mRootView.findViewById(R.id.data_list);
        mAdapter = new AddAndMinusAdapter(getActivity());
        mDataList.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initComponents(inflater, container, savedInstanceState);

        // TextView textView = (TextView)
        // rootView.findViewById(R.id.section_label);
        // textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}


package com.bj4.yhh.projectx.lot.hk6;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class HK6OrderedFragment extends BigTableFragment {

    public static HK6OrderedFragment getNewInstance() {
        HK6OrderedFragment fragment = new HK6OrderedFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_ORDERED;
    }

}

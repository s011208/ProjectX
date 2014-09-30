
package com.bj4.yhh.projectx.lot.hk6;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class HK6LastFragment extends BigTableFragment {

    public static HK6LastFragment getNewInstance() {
        HK6LastFragment fragment = new HK6LastFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_LAST;
    }

}


package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class LT539OrderedFragment extends BigTableFragment {

    public static LT539OrderedFragment getNewInstance() {
        LT539OrderedFragment fragment = new LT539OrderedFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_ORDERED;
    }

}

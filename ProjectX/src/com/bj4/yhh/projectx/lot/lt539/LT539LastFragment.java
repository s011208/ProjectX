
package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class LT539LastFragment extends BigTableFragment {

    public static LT539LastFragment getNewInstance() {
        LT539LastFragment fragment = new LT539LastFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_LAST;
    }

}

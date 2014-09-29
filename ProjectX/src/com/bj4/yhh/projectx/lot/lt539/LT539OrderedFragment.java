
package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.OrderedFragment;

public class LT539OrderedFragment extends OrderedFragment {

    public static LT539OrderedFragment getNewInstance() {
        LT539OrderedFragment fragment = new LT539OrderedFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

}

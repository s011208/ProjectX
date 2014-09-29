
package com.bj4.yhh.projectx.lot.hk6;

import com.bj4.yhh.projectx.lot.CombinationFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class HK6CombinationFragment extends CombinationFragment {

    public static HK6CombinationFragment getNewInstance() {
        HK6CombinationFragment fragment = new HK6CombinationFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

}

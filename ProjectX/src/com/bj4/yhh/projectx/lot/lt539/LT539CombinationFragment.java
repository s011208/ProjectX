
package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.lot.CombinationFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class LT539CombinationFragment extends CombinationFragment {

    public static LT539CombinationFragment getNewInstance() {
        LT539CombinationFragment fragment = new LT539CombinationFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

}

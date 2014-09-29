
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.lot.CombinationFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class WeLiCombinationFragment extends CombinationFragment {

    public static WeLiCombinationFragment getNewInstance() {
        WeLiCombinationFragment fragment = new WeLiCombinationFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

}

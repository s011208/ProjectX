
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class WeLiCombinationFragment extends BigTableFragment {

    public static WeLiCombinationFragment getNewInstance() {
        WeLiCombinationFragment fragment = new WeLiCombinationFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_COMBINATION;
    }

}

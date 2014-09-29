
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.OrderedFragment;

public class WeLiOrderedFragment extends OrderedFragment {

    public static WeLiOrderedFragment getNewInstance() {
        WeLiOrderedFragment fragment = new WeLiOrderedFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

}


package com.bj4.yhh.projectx.lot.hk6;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.OrderedFragment;

public class HK6OrderedFragment extends OrderedFragment {

    public static HK6OrderedFragment getNewInstance() {
        HK6OrderedFragment fragment = new HK6OrderedFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

}

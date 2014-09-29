
package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.lot.LastFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class LT539LastFragment extends LastFragment {

    public static LT539LastFragment getNewInstance() {
        LT539LastFragment fragment = new LT539LastFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

}

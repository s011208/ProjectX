
package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.lot.AddOrMinusFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class LT539AddOrMinusFragment extends AddOrMinusFragment {

    public static LT539AddOrMinusFragment getNewInstance() {
        LT539AddOrMinusFragment fragment = new LT539AddOrMinusFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

}

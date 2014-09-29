
package com.bj4.yhh.projectx.lot.hk6;

import com.bj4.yhh.projectx.lot.AddOrMinusFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class HK6AddOrMinusFragment extends AddOrMinusFragment {

    public static HK6AddOrMinusFragment getNewInstance() {
        HK6AddOrMinusFragment fragment = new HK6AddOrMinusFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

}

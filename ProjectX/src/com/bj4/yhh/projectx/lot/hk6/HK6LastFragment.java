
package com.bj4.yhh.projectx.lot.hk6;

import com.bj4.yhh.projectx.lot.LastFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class HK6LastFragment extends LastFragment {

    public static HK6LastFragment getNewInstance() {
        HK6LastFragment fragment = new HK6LastFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

}

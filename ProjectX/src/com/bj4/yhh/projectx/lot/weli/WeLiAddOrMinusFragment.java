
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.lot.AddOrMinusFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class WeLiAddOrMinusFragment extends AddOrMinusFragment {

    public static WeLiAddOrMinusFragment getNewInstance() {
        WeLiAddOrMinusFragment fragment = new WeLiAddOrMinusFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

}

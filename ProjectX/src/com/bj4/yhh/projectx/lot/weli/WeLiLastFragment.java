
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.lot.LastFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class WeLiLastFragment extends LastFragment {

    public static WeLiLastFragment getNewInstance() {
        WeLiLastFragment fragment = new WeLiLastFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

}

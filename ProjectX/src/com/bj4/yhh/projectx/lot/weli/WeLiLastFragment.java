
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class WeLiLastFragment extends BigTableFragment {

    public static WeLiLastFragment getNewInstance() {
        WeLiLastFragment fragment = new WeLiLastFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_LAST;
    }

}

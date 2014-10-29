package com.bj4.yhh.projectx.lot.blot;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class BLotCombinationFragment extends BigTableFragment {

    public static BLotCombinationFragment getNewInstance() {
        BLotCombinationFragment fragment = new BLotCombinationFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_BLOT;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_COMBINATION;
    }

}

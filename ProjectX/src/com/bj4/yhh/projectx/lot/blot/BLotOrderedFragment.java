package com.bj4.yhh.projectx.lot.blot;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.lot.BigTableFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class BLotOrderedFragment extends BigTableFragment {

    public static BLotOrderedFragment getNewInstance() {
        BLotOrderedFragment fragment = new BLotOrderedFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_BLOT;
    }

    @Override
    public int getFragmentType() {
        return MainActivity.FRAGMENT_TYPE_ORDERED;
    }

}

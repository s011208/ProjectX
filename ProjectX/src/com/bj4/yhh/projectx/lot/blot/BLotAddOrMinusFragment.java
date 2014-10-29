package com.bj4.yhh.projectx.lot.blot;

import com.bj4.yhh.projectx.lot.AddOrMinusFragment;
import com.bj4.yhh.projectx.lot.LotteryData;

public class BLotAddOrMinusFragment extends AddOrMinusFragment {

    public static BLotAddOrMinusFragment getNewInstance() {
        BLotAddOrMinusFragment fragment = new BLotAddOrMinusFragment();
        return fragment;
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_BLOT;
    }

}
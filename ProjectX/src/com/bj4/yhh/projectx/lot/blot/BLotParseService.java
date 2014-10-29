package com.bj4.yhh.projectx.lot.blot;

import android.content.Context;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.ParseService;

public class BLotParseService extends ParseService {
    public static final boolean DEBUG = false;

    public static final String TAG = "BLotParseService";

    private static final int ALL_PAGE_SIZE = 40;

    @Override
    public int getAllPageSize() {
        return ALL_PAGE_SIZE;
    }

    @Override
    public String getParsedUrl() {
        return "http://www.pilio.idv.tw/ltobig/list.asp?indexpage=";
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_BLOT;
    }

    @Override
    public String getUpdateStatusString(Context context) {
        return context.getString(R.string.blot_update_status);
    }
}

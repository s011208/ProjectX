
package com.bj4.yhh.projectx.lot.hk6;

import android.content.Context;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.ParseService;

public class HK6ParseService extends ParseService {
    public static final boolean DEBUG = false;

    public static final String TAG = "HK6ParseService";

    private static final int ALL_PAGE_SIZE = 60;

    @Override
    public int getAllPageSize() {
        return ALL_PAGE_SIZE;
    }

    @Override
    public String getParsedUrl() {
        return "http://www.pilio.idv.tw/ltohk/list.asp?indexpage=";
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_HK6;
    }

    @Override
    public String getUpdateStatusString(Context context) {
        return context.getString(R.string.hk6_update_status);
    }
}

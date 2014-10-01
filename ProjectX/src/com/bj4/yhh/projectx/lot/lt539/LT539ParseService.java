
package com.bj4.yhh.projectx.lot.lt539;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.ParseService;

import android.content.Context;

public class LT539ParseService extends ParseService {
    public static final boolean DEBUG = false;

    public static final String TAG = "LT539ParseService";

    private static final int ALL_PAGE_SIZE = 70;

    @Override
    public int getAllPageSize() {
        return ALL_PAGE_SIZE;
    }

    @Override
    public String getParsedUrl() {
        return "http://www.pilio.idv.tw/lto539/list.asp?indexpage=";
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_539;
    }

    @Override
    public String getUpdateStatusString(Context context) {
        return context.getString(R.string.lt539_update_status);
    }

}

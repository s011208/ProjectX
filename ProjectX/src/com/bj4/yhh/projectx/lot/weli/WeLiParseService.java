
package com.bj4.yhh.projectx.lot.weli;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.ParseService;

import android.content.Context;

public class WeLiParseService extends ParseService {
    private static final int ALL_PAGE_SIZE = 30;

    @Override
    public int getAllPageSize() {
        return ALL_PAGE_SIZE;
    }

    @Override
    public String getParsedUrl() {
        return "http://www.pilio.idv.tw/LTO/list.asp?indexpage=";
    }

    @Override
    public int getGameType() {
        return LotteryData.TYPE_WELI;
    }

    @Override
    public String getUpdateStatusString(Context context) {
        return context.getString(R.string.weli_update_status);
    }

}

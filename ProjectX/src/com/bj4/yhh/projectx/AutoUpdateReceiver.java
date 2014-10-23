
package com.bj4.yhh.projectx;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.LotteryDatabaseHelper;
import com.bj4.yhh.projectx.lot.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (LotteryDatabaseHelper.getInstance(context.getApplicationContext()).isTableEmpty(
                LotteryData.TYPE_HK6)) {
            Utils.startUpdateAllService(context, LotteryData.TYPE_HK6);
        } else {
            Utils.startUpdateRecently(context, LotteryData.TYPE_HK6);
        }
        if (LotteryDatabaseHelper.getInstance(context.getApplicationContext()).isTableEmpty(
                LotteryData.TYPE_539)) {
            Utils.startUpdateAllService(context, LotteryData.TYPE_539);
        } else {
            Utils.startUpdateRecently(context, LotteryData.TYPE_539);
        }
    }

}

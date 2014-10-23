
package com.bj4.yhh.projectx;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.LotteryDatabaseHelper;
import com.bj4.yhh.projectx.lot.Utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

public class AutoUpdateService extends Service {
    private ConnectivityManager mConnectivityManager;

    private NetworkInfo mNetworkInfo;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    // connect
                    Toast.makeText(getApplicationContext(), "connect to internet",
                            Toast.LENGTH_LONG).show();
                    // if
                    // (LotteryDatabaseHelper.getInstance(context.getApplicationContext())
                    // .isTableEmpty(LotteryData.TYPE_HK6)) {
                    // Utils.startUpdateAllService(context,
                    // LotteryData.TYPE_HK6);
                    // } else {
                    // Utils.startUpdateRecently(context, LotteryData.TYPE_HK6);
                    // }
                    // if
                    // (LotteryDatabaseHelper.getInstance(context.getApplicationContext())
                    // .isTableEmpty(LotteryData.TYPE_539)) {
                    // Utils.startUpdateAllService(context,
                    // LotteryData.TYPE_539);
                    // } else {
                    // Utils.startUpdateRecently(context, LotteryData.TYPE_539);
                    // }
                } else {
                    // disconnect
                }
            }
        }
    };

    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "Start autoupdate service", Toast.LENGTH_LONG)
                .show();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}


package com.bj4.yhh.projectx.lot.hk6;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

// Document doc = Jsoup.connect(url).get();
public class HK6ParseService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        parse();
        return super.onStartCommand(intent, flags, startId);
    }

    private void parse() {
        // http://www.pilio.idv.tw/ltohk/list.asp?indexpage=1
        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

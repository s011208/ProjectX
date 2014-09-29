
package com.bj4.yhh.projectx.lot.lt539;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.LotteryDatabaseHelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class LT539ParseService extends Service {
    private static final boolean DEBUG = false;

    private static final String TAG = "LT539ParseService";

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
        for (int i = 1; i <= 70; i++) {
            new ParseTask(this, "http://www.pilio.idv.tw/lto539/list.asp?indexpage=" + i).execute();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class ParseTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Context> mContext;

        private String mUrl;

        public ParseTask(Context context, String url) {
            mContext = new WeakReference<Context>(context);
            mUrl = url;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Context context = mContext.get();
            if (context == null)
                return null;
            final LotteryDatabaseHelper db = LotteryDatabaseHelper.getInstance(context
                    .getApplicationContext());
            final ArrayList<LotteryData> mData = new ArrayList<LotteryData>();
            try {
                Document doc = Jsoup.connect(mUrl).get();
                Elements trEles = doc.select("tr");
                for (Element trEle : trEles) {
                    Elements tdEles = trEle.children();
                    try {
                        long number = Long.valueOf(tdEles.get(0).text());
                        String date = tdEles.get(1).text();
                        String[] raw = tdEles.get(2).text().replaceAll(",", "").replaceAll("  ", " ").split(" ");
                        int m1 = Integer.parseInt(raw[0].trim());
                        int m2 = Integer.parseInt(raw[1].trim());
                        int m3 = Integer.parseInt(raw[2].trim());
                        int m4 = Integer.parseInt(raw[3].trim());
                        int m5 = Integer.parseInt(raw[4].trim());
                        int m6 = LotteryData.NOT_USED;
                        int m7 = LotteryData.NOT_USED;
                        mData.add(new LotteryData(m1, m2, m3, m4, m5, m6, m7, date, number));
                    } catch (Exception e) {
                        if (DEBUG)
                            Log.w(TAG, "failed", e);
                    }
                }
            } catch (IOException e) {
                if (DEBUG)
                    Log.w(TAG, "failed", e);
            }
            if (mData.isEmpty() == false) {
                if (!db.isExists(LotteryData.TYPE_539, mData.get(0).mNumber)) {
                    db.addData(LotteryData.TYPE_539, mData);
                }
            }
            return null;
        }
    }

}

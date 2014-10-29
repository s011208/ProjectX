
package com.bj4.yhh.projectx.lot;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ParseTask extends AsyncTask<Void, Void, Void> {

    public interface ParseTaskCallback {
        public void done();
    }

    private static final boolean DEBUG = false;

    private static final String TAG = "ParseTask";

    private final WeakReference<ParseTaskCallback> mCallback;

    private WeakReference<Context> mContext;

    private String mUrl;

    private final int mGameType;

    public ParseTask(Context context, String url, int gameType, ParseTaskCallback cb) {
        mContext = new WeakReference<Context>(context);
        mUrl = url;
        mGameType = gameType;
        mCallback = new WeakReference<ParseTaskCallback>(cb);
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
                    if (mGameType == LotteryData.TYPE_HK6) {
                        long number = Long.valueOf(tdEles.get(0).text());
                        String date = tdEles.get(1).text();
                        String[] raw = tdEles.get(2).text().replaceAll(" ", "").split(",");
                        int m1 = Integer.parseInt(raw[0].trim());
                        int m2 = Integer.parseInt(raw[1].trim());
                        int m3 = Integer.parseInt(raw[2].trim());
                        int m4 = Integer.parseInt(raw[3].trim());
                        int m5 = Integer.parseInt(raw[4].trim());
                        int m6 = Integer.parseInt(raw[5].trim());
                        int m7 = Integer.parseInt(tdEles.get(3).text().trim());
                        mData.add(new LotteryData(new LotteryData.LotteryNumber(m1),
                                new LotteryData.LotteryNumber(m2),
                                new LotteryData.LotteryNumber(m3),
                                new LotteryData.LotteryNumber(m4),
                                new LotteryData.LotteryNumber(m5),
                                new LotteryData.LotteryNumber(m6),
                                new LotteryData.LotteryNumber(m7), date, number));
                    } else if (mGameType == LotteryData.TYPE_539) {
                        long number = Long.valueOf(tdEles.get(0).text());
                        String date = tdEles.get(1).text();
                        String[] raw = tdEles.get(2).text().replaceAll(",", "")
                                .replaceAll("  ", " ").split(" ");
                        int m1 = Integer.parseInt(raw[0].trim());
                        int m2 = Integer.parseInt(raw[1].trim());
                        int m3 = Integer.parseInt(raw[2].trim());
                        int m4 = Integer.parseInt(raw[3].trim());
                        int m5 = Integer.parseInt(raw[4].trim());
                        int m6 = LotteryData.NOT_USED;
                        int m7 = LotteryData.NOT_USED;
                        mData.add(new LotteryData(new LotteryData.LotteryNumber(m1),
                                new LotteryData.LotteryNumber(m2),
                                new LotteryData.LotteryNumber(m3),
                                new LotteryData.LotteryNumber(m4),
                                new LotteryData.LotteryNumber(m5),
                                new LotteryData.LotteryNumber(m6),
                                new LotteryData.LotteryNumber(m7), date, number));
                    } else if (mGameType == LotteryData.TYPE_WELI) {
                        long number = Long.valueOf(tdEles.get(0).text());
                        String date = tdEles.get(1).text();
                        String[] raw = tdEles.get(2).text().replaceAll(",", "")
                                .replaceAll("  ", " ").split(" ");
                        int m1 = Integer.parseInt(raw[0].trim());
                        int m2 = Integer.parseInt(raw[1].trim());
                        int m3 = Integer.parseInt(raw[2].trim());
                        int m4 = Integer.parseInt(raw[3].trim());
                        int m5 = Integer.parseInt(raw[4].trim());
                        int m6 = Integer.parseInt(raw[5].trim());
                        int m7 = Integer.parseInt(tdEles.get(3).text().trim());
                        mData.add(new LotteryData(new LotteryData.LotteryNumber(m1),
                                new LotteryData.LotteryNumber(m2),
                                new LotteryData.LotteryNumber(m3),
                                new LotteryData.LotteryNumber(m4),
                                new LotteryData.LotteryNumber(m5),
                                new LotteryData.LotteryNumber(m6),
                                new LotteryData.LotteryNumber(m7), date, number));
                    } else if (mGameType == LotteryData.TYPE_BLOT) {
                        long number = Long.valueOf(tdEles.get(0).text());
                        String date = tdEles.get(1).text();
                        String[] raw = tdEles.get(2).text().replaceAll(",", "")
                                .replaceAll("  ", " ").split(" ");
                        int m1 = Integer.parseInt(raw[0].trim());
                        int m2 = Integer.parseInt(raw[1].trim());
                        int m3 = Integer.parseInt(raw[2].trim());
                        int m4 = Integer.parseInt(raw[3].trim());
                        int m5 = Integer.parseInt(raw[4].trim());
                        int m6 = Integer.parseInt(raw[5].trim());
                        int m7 = Integer.parseInt(tdEles.get(3).text().trim());
                        mData.add(new LotteryData(new LotteryData.LotteryNumber(m1),
                                new LotteryData.LotteryNumber(m2),
                                new LotteryData.LotteryNumber(m3),
                                new LotteryData.LotteryNumber(m4),
                                new LotteryData.LotteryNumber(m5),
                                new LotteryData.LotteryNumber(m6),
                                new LotteryData.LotteryNumber(m7), date, number));
                    }
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
            if (!db.isExists(mGameType, mData.get(0).mNumber)) {
                db.addData(mGameType, mData);
            }
        }
        ParseTaskCallback cb = mCallback.get();
        if (cb != null) {
            cb.done();
        }
        return null;
    }
}

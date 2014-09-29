
package com.bj4.yhh.projectx.lot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

public class DataLoadTask extends AsyncTask<Void, Void, Void> {
    public interface Callback {
        public void done(ArrayList<LotteryData> data);

        public void startLoading();
    }

    private WeakReference<Callback> mCallback;

    private WeakReference<Context> mContext;

    private final ArrayList<LotteryData> mData = new ArrayList<LotteryData>();

    private final int mGameType;

    public DataLoadTask(Context context, int gameType, Callback cb) {
        mCallback = new WeakReference<Callback>(cb);
        mContext = new WeakReference<Context>(context);
        mGameType = gameType;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Callback cb = mCallback.get();
        if (cb != null) {
            cb.startLoading();
        }
        mData.addAll(LotteryData.getListData(mGameType));
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Callback cb = mCallback.get();
        if (cb != null) {
            cb.done(mData);
        }
    }
}

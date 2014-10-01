
package com.bj4.yhh.projectx.lot;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.lot.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public abstract class ParseService extends Service implements ParseTask.ParseTaskCallback {

    public static final boolean DEBUG = false;

    public static final String TAG = "ParseService";

    public static final String INTENT_FINISH_ALL_TASKS = "ParseService.INTENT_FINISH_ALL_TASKS";

    public static final String INTENT_EXTRAS_UPDATE_GAMETYPE = "game_type";

    public static final String INTENT_EXTRAS_PARSE_RECENT_DATA = "parse_recent_data";

    public volatile int mUpdatedProgress = 0;

    public abstract int getAllPageSize();

    public abstract String getParsedUrl();

    public abstract int getGameType();

    public abstract String getUpdateStatusString(Context context);

    @Override
    public void done() {
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification n = null;
        --mUpdatedProgress;
        if (mUpdatedProgress > 0) {
            n = Utils.getNotification(this, mUpdatedProgress, getAllPageSize(),
                    getUpdateStatusString(this), getString(R.string.update_progress));
        } else {
            n = Utils.getNotification(this, mUpdatedProgress, getAllPageSize(),
                    getUpdateStatusString(this), getString(R.string.update_done));
            finishAllTasks();
        }
        notificationManager.notify(hashCode(), n);
    }

    private void finishAllTasks() {
        Intent intent = new Intent(INTENT_FINISH_ALL_TASKS);
        intent.putExtra(INTENT_EXTRAS_UPDATE_GAMETYPE, getGameType());
        sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final boolean parseRecently = intent
                .getBooleanExtra(INTENT_EXTRAS_PARSE_RECENT_DATA, false);
        if (parseRecently) {
            parseRecently();
        } else {
            parseAll();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void parseRecently() {
        mUpdatedProgress = 0;
        ++mUpdatedProgress;
        new ParseTask(this, getParsedUrl() + 1, getGameType(), this).execute();
    }

    private void parseAll() {
        mUpdatedProgress = 0;
        for (int i = 1; i <= getAllPageSize(); i++) {
            ++mUpdatedProgress;
            new ParseTask(this, getParsedUrl() + i, getGameType(), this).execute();
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
}


package com.bj4.yhh.projectx;

import com.google.android.gms.analytics.HitBuilders.AppViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;

import android.app.Activity;
import android.content.Context;

public class GATracker {
    private static Tracker sTracker;

    public static final String CATEGORY_MENU = "menu";

    public static final String SCREEN_HK6 = "HK6";

    public static final String SCREEN_BLOT = "Blot";

    public static final String SCREEN_LT539 = "Lt539";

    public static final String SCREEN_TYPE_ORDER = " - order";

    public static final String SCREEN_TYPE_COMBINATION = " - combination";

    public static final String SCREEN_TYPE_LAST = " - last";

    public static final String SCREEN_TYPE_ADD_AND_MINUS = " - add and minus";

    public static final String ACTION_TYPE_HK6 = "HK6";

    public static final String ACTION_TYPE_BLOT = "Blot";

    public static final String ACTION_TYPE_LT539 = "lt539";

    public static final String LABEL_ORDER = "order";

    public static final String LABEL_COMBINATION = "combination";

    public static final String LABEL_ADD_AND_MINUS = "add and minus";

    public static final String LABEL_LAST = "last number";

    public static final String ACTION_UPDATE_ALL = "update all";

    public static final String ACTION_UPDATE_RECENT = "update recent";

    public static final String ACTION_SETTIGNS = "settings";

    public static final String ACTION_DISPLAY_LINES = "display lines";

    public static final String CATEGORY_SETTINGS = "settnigs";

    public static final String ACTION_RESET_ALL = "reset all data";

    public static final String ACTION_GRID_COLOR = "grid color";

    public static final String LABEL_GRID_COLOR_BLUE = "blue";

    public static final String LABEL_GRID_COLOR_BLACK = "black";

    public static final String LABEL_GRID_COLOR_GREY = "grey";

    private static synchronized Tracker getTracker(Context context) {
        if (sTracker == null) {
            sTracker = GoogleAnalytics.getInstance(context.getApplicationContext()).newTracker(
                    R.xml.tracker_config);
            sTracker.enableAdvertisingIdCollection(true);
        }
        return sTracker;
    }

    public static void sendEvent(Context context, String category, String action, String label) {
        getTracker(context).send(
                new HitBuilders.EventBuilder().setCategory(category).setAction(action)
                        .setLabel(label).build());
    }

    public static void sendScreen(Context context, String screen) {
        getTracker(context).setScreenName(screen);
        getTracker(context).send(new AppViewBuilder().build());
    }

    public static void startActivity(Activity activity) {
        GoogleAnalytics.getInstance(activity).reportActivityStart(activity);
        getTracker(activity);
    }

    public static void stopActivity(Activity activity) {
        GoogleAnalytics.getInstance(activity).reportActivityStop(activity);
    }
}

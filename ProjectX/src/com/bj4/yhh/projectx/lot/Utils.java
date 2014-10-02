
package com.bj4.yhh.projectx.lot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.SharedPreferenceManager;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

public class Utils {

    public static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("00");

    public static final ArrayList<Integer> getOrderedList(final int totalNumber) {
        final ArrayList<Integer> rtn = new ArrayList<Integer>();
        for (int i = 1; i <= totalNumber; i++) {
            rtn.add(i);
        }
        return rtn;
    }

    public static final ArrayList<Integer> getCombinationList(final int totalNumber) {
        final ArrayList<Integer> rtn = new ArrayList<Integer>();
        HashMap<Integer, ArrayList<Integer>> tempMap = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 1; i <= totalNumber; i++) {
            int s = i / 10;
            int r = i % 10;
            int result = (s + r) % 10;
            ArrayList<Integer> tempList = tempMap.get(result);
            if (tempList == null) {
                tempList = new ArrayList<Integer>();
            }
            tempList.add(i);
            tempMap.put(result, tempList);
        }
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> tempList = tempMap.get(i);
            if (tempList != null) {
                rtn.addAll(tempList);
            }
        }
        return rtn;
    }

    public static final ArrayList<Integer> getLastList(final int totalNumber) {
        final ArrayList<Integer> rtn = new ArrayList<Integer>();
        HashMap<Integer, ArrayList<Integer>> tempMap = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 1; i <= totalNumber; i++) {
            int r = i % 10;
            int result = r;
            ArrayList<Integer> tempList = tempMap.get(result);
            if (tempList == null) {
                tempList = new ArrayList<Integer>();
            }
            tempList.add(i);
            tempMap.put(result, tempList);
        }
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> tempList = tempMap.get(i);
            if (tempList != null) {
                rtn.addAll(tempList);
            }
        }
        return rtn;
    }

    public static Notification getNotification(Context context, int currentProgress,
            int targetProgress, String title, String subject) {
        Notification rtn = new Notification.Builder(context)
                .setContentTitle(title)
                .setProgress(100,
                        (int)(((float)(targetProgress - currentProgress) / targetProgress) * 100),
                        false).setContentText(subject).setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true).build();
        return rtn;
    }

    public static int getGridColorResource(Context context) {
        SharedPreferenceManager manager = SharedPreferenceManager.getInstance(context);
        int rtn = R.drawable.blue_column_bg;
        switch (manager.getGridColor()) {
            case SharedPreferenceManager.GRID_COLOR_BLUE:
                rtn = R.drawable.blue_column_bg;
                break;
            case SharedPreferenceManager.GRID_COLOR_GREY:
                rtn = R.drawable.grey_column_bg;
                break;
            case SharedPreferenceManager.GRID_COLOR_BLACK:
                rtn = R.drawable.black_column_bg;
                break;
        }
        return rtn;
    }
}


package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

public class LotteryData {
    public static final int TYPE_HK6 = 0;

    public static final int TYPE_539 = 1;

    public static final int TYPE_WELI = 2;

    public static final int NOT_USED = -1;

    public int m1, m2, m3, m4, m5, m6, m7;

    public long mNumber;

    public String mDate;

    public boolean mIsSubTotal = false;

    public SparseArray<Integer> mSubTotal;

    public static int getTotalNumber(int type) {
        switch (type) {
            case TYPE_HK6:
                return 49;
            case TYPE_539:
                return 39;
            case TYPE_WELI:
                return 39;
            default:
                return 0;
        }
    }

    public static int getAwardNumber(int type) {
        switch (type) {
            case TYPE_HK6:
                return 7;
            case TYPE_539:
                return 5;
            case TYPE_WELI:
                return 7;
            default:
                return 0;
        }
    }

    public static ArrayList<LotteryData> getListData(Context context, int type) {
        final ArrayList<LotteryData> data = new ArrayList<LotteryData>();
        data.addAll(LotteryDatabaseHelper.getInstance(context).getData(type));
        preProcessData(data);
        return data;
    }

    private static void preProcessData(final ArrayList<LotteryData> data) {
        if (data != null && data.isEmpty() == false) {
            ArrayList<LotteryData> tempList = new ArrayList<LotteryData>();
            String previousMonth = null;
            for (int i = 0; i < data.size(); i++) {
                final LotteryData current = data.get(i);
                final String currentMonth = current.mDate.substring(5, 7);
                if (previousMonth != null) {
                    if (previousMonth.equals(currentMonth)) {
                        tempList.add(current);
                    } else {
                        LotteryData subTotalData = new LotteryData(NOT_USED, NOT_USED, NOT_USED,
                                NOT_USED, NOT_USED, NOT_USED, NOT_USED, "", NOT_USED);
                        subTotalData.mIsSubTotal = true;
                        SparseArray<Integer> subTotal = new SparseArray<Integer>();
                        for (LotteryData tempData : tempList) {
                            int key = tempData.m1;
                            int value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                            key = tempData.m2;
                            value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                            key = tempData.m3;
                            value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                            key = tempData.m4;
                            value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                            key = tempData.m5;
                            value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                            key = tempData.m6;
                            value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                            key = tempData.m7;
                            value = subTotal.get(key, 0);
                            subTotal.put(key, value + 1);
                        }
                        subTotalData.mSubTotal = subTotal;
                        tempList.clear();
                        data.add(i, subTotalData);
                        i++;
                    }
                } else {
                    tempList.add(current);
                }
                previousMonth = currentMonth;
            }
        }
    }

    public LotteryData(int m1, int m2, int m3, int m4, int m5, int m6, int m7, String date,
            long number) {
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
        this.m7 = m7;
        mDate = date;
        mNumber = number;
    }

    public String toString() {
        return "No. " + mNumber + ", date: " + mDate + ", m1: " + m1 + ", m2: " + m2 + ", m3: "
                + m3 + ", m4: " + m4 + ", m5: " + m5 + ", m6: " + m6 + ", m7: " + m7;
    }
}

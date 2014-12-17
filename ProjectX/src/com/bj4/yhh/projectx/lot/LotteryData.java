
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;

public class LotteryData {
    public static class LotteryNumber {
        public int mNumber = NOT_USED;

        public boolean mIsHit = false;

        public LotteryNumber() {
        }

        public LotteryNumber(int number) {
            mNumber = number;
        }
    }

    public static final int TYPE_HK6 = 0;

    public static final int TYPE_539 = 1;

    public static final int TYPE_WELI = 2;

    public static final int TYPE_BLOT = 3;

    public static final int NOT_USED = -1;

    public LotteryNumber m1, m2, m3, m4, m5, m6, m7;

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
            case TYPE_BLOT:
                return 49;
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
            case TYPE_BLOT:
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
            String previousMonthYear = null;
            for (int i = 0; i < data.size(); i++) {
                final LotteryData current = data.get(i);
                final String currentMonthYear = current.mDate.substring(0, 7);
                if (previousMonthYear != null) {
                    if (previousMonthYear.equals(currentMonthYear)) {
                        tempList.add(current);
                    } else {
                        LotteryData subTotalData = getNewSubTotalLotteryData(tempList,
                                previousMonthYear);
                        tempList.clear();
                        data.add(i, subTotalData);
                        i++;
                        tempList.add(current);
                    }
                } else {
                    tempList.add(current);
                }
                previousMonthYear = currentMonthYear;
            }
            if (tempList.isEmpty() == false) {
                LotteryData subTotalData = getNewSubTotalLotteryData(tempList, previousMonthYear);
                data.add(subTotalData);
            }
        }
    }

    private static LotteryData getNewSubTotalLotteryData(ArrayList<LotteryData> tempList,
            String title) {
        LotteryData subTotalData = new LotteryData(new LotteryNumber(), new LotteryNumber(),
                new LotteryNumber(), new LotteryNumber(), new LotteryNumber(), new LotteryNumber(),
                new LotteryNumber(), title + " ¤p­p", NOT_USED);
        subTotalData.mIsSubTotal = true;
        SparseArray<Integer> subTotal = new SparseArray<Integer>();
        for (LotteryData tempData : tempList) {
            int key = tempData.m1.mNumber;
            int value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
            key = tempData.m2.mNumber;
            value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
            key = tempData.m3.mNumber;
            value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
            key = tempData.m4.mNumber;
            value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
            key = tempData.m5.mNumber;
            value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
            key = tempData.m6.mNumber;
            value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
            key = tempData.m7.mNumber;
            value = subTotal.get(key, 0);
            subTotal.put(key, value + 1);
        }
        subTotalData.mSubTotal = subTotal;
        return subTotalData;
    }

    public LotteryData(LotteryNumber m1, LotteryNumber m2, LotteryNumber m3, LotteryNumber m4,
            LotteryNumber m5, LotteryNumber m6, LotteryNumber m7, String date, long number) {
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

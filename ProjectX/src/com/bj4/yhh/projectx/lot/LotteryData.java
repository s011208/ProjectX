
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class LotteryData {
    public static final int TYPE_HK6 = 0;

    public static final int TYPE_539 = 1;

    public static final int TYPE_WELI = 2;

    public static final int NOT_USED = -1;

    public int m1, m2, m3, m4, m5, m6, m7;

    public long mNumber;

    public String mDate;

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
        return data;
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

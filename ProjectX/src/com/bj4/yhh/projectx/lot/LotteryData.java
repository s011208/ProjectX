
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import android.util.Log;

public class LotteryData {
    public static final int TYPE_HK6 = 0;

    public static final int NOT_USED = -1;

    public int m1, m2, m3, m4, m5, m6, m7;

    public String mDate;

    public static int getTotalNumber(int type) {
        switch (type) {
            case TYPE_HK6:
                return 49;
            default:
                return 0;
        }
    }

    public static int getAwardNumber(int type) {
        switch (type) {
            case TYPE_HK6:
                return 6;
            default:
                return 0;
        }
    }

    public static ArrayList<LotteryData> getListData(int type) {
        final ArrayList<LotteryData> data = new ArrayList<LotteryData>();
        Log.d("QQQQ", "getListData type: " + type);
        switch (type) {
            case TYPE_HK6:
                for (int i = 1; i < 10; i++) {
                    data.add(new LotteryData(1 + (int)(Math.random() * 1000 % 49), 1 + (int)(Math
                            .random() * 1000 % 49), 1 + (int)(Math.random() * 1000 % 49),
                            1 + (int)(Math.random() * 1000 % 49),
                            1 + (int)(Math.random() * 1000 % 49),
                            1 + (int)(Math.random() * 1000 % 49),
                            1 + (int)(Math.random() * 1000 % 49), "12る13ら"));
                }
                break;
            default:
                for (int i = 1; i < 250; i++) {
                    data.add(new LotteryData(NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED,
                            NOT_USED, NOT_USED, "12る13ら"));
                }
                break;
        }
        for (LotteryData l : data) {
            Log.d("QQQQ", l.toString());
        }
        return data;
    }

    public LotteryData(int m1, int m2, int m3, int m4, int m5, int m6, int m7, String date) {
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
        this.m7 = m7;
        mDate = date;
    }

    public String toString() {
        return "Date: " + mDate + ", m1: " + m1 + ", m2: " + m2 + ", m3: " + m3 + ", m4: " + m4
                + ", m5: " + m5 + ", m6: " + m6 + ", m7: " + m7;
    }
}

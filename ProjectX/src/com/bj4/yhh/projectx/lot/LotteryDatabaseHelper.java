
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;
import java.util.Collections;

import com.bj4.yhh.projectx.SharedPreferenceManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteOpenHelper;

public class LotteryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lottery_data.db";

    private static final int VERSION = 1;

    private static final String TABLE_HK6 = "hk6";

    private static final String TABLE_LT539 = "lt539";

    private static final String TABLE_WELI = "weli";
    
    private static final String TABLE_BLOT = "blot";

    private static final String COLUMN_M1 = "m1";

    private static final String COLUMN_M2 = "m2";

    private static final String COLUMN_M3 = "m3";

    private static final String COLUMN_M4 = "m4";

    private static final String COLUMN_M5 = "m5";

    private static final String COLUMN_M6 = "m6";

    private static final String COLUMN_M7 = "m7";

    private static final String COLUMN_DATE = "c_date";

    private static final String COLUMN_NUMBER = "c_numbers";

    private static LotteryDatabaseHelper sInstance;

    private SQLiteDatabase mDb;

    private Context mContext;

    public LotteryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        createTables();
    }

    public synchronized static LotteryDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LotteryDatabaseHelper(context);
        }
        return sInstance;
    }

    public boolean isExists(int type, long number) {
        boolean rtn = false;
        String table = getTableName(type);
        if (table != null) {
            Cursor data = getDatabase().query(table, null, COLUMN_NUMBER + "=" + number, null,
                    null, null, null);
            if (data != null) {
                try {
                    while (data.moveToNext()) {
                        rtn = true;
                        break;
                    }
                } finally {
                    data.close();
                }
            }
        }
        return rtn;
    }

    public void deleteData(int type, long number) {
        String table = getTableName(type);
        getDatabase().delete(table, COLUMN_NUMBER + "=" + number, null);
    }

    public void clearData(int type) {
        String table = getTableName(type);
        getDatabase().delete(table, null, null);
    }

    public ArrayList<LotteryData> getData(int type) {
        final ArrayList<LotteryData> rtn = new ArrayList<LotteryData>();
        String table = getTableName(type);
        if (table != null) {
            SharedPreferenceManager pref = SharedPreferenceManager.getInstance(mContext);
            Cursor data = null;
            final int displayLines = pref.getDisplayLines();
            if (displayLines == SharedPreferenceManager.DEFAULT_DISPLAY_LINES) {
                data = getDatabase().query(table, null, null, null, null, null,
                        COLUMN_NUMBER + " desc");
            } else {
                data = getDatabase().query(table, null, null, null, null, null,
                        COLUMN_NUMBER + " desc limit " + displayLines);
            }
            if (data != null) {
                try {
                    final int numberIndex = data.getColumnIndex(COLUMN_NUMBER);
                    final int dateIndex = data.getColumnIndex(COLUMN_DATE);
                    final int m1Index = data.getColumnIndex(COLUMN_M1);
                    final int m2Index = data.getColumnIndex(COLUMN_M2);
                    final int m3Index = data.getColumnIndex(COLUMN_M3);
                    final int m4Index = data.getColumnIndex(COLUMN_M4);
                    final int m5Index = data.getColumnIndex(COLUMN_M5);
                    final int m6Index = data.getColumnIndex(COLUMN_M6);
                    final int m7Index = data.getColumnIndex(COLUMN_M7);
                    while (data.moveToNext()) {
                        rtn.add(new LotteryData(data.getInt(m1Index), data.getInt(m2Index), data
                                .getInt(m3Index), data.getInt(m4Index), data.getInt(m5Index), data
                                .getInt(m6Index), data.getInt(m7Index), data.getString(dateIndex),
                                data.getLong(numberIndex)));
                    }
                } finally {
                    data.close();
                    Collections.reverse(rtn);
                }
            }
        }
        return rtn;
    }

    private String getTableName(int type) {
        String table = null;
        switch (type) {
            case LotteryData.TYPE_HK6:
                table = TABLE_HK6;
                break;
            case LotteryData.TYPE_539:
                table = TABLE_LT539;
                break;
            case LotteryData.TYPE_WELI:
                table = TABLE_WELI;
                break;
            case LotteryData.TYPE_BLOT:
                table = TABLE_BLOT;
            default:
                break;
        }
        return table;
    }

    public void addData(int type, int number, String date, int m1, int m2, int m3, int m4, int m5,
            int m6, int m7) {
        String table = getTableName(type);
        if (table != null) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NUMBER, number);
            cv.put(COLUMN_DATE, date);
            cv.put(COLUMN_M1, m1);
            cv.put(COLUMN_M2, m2);
            cv.put(COLUMN_M3, m3);
            cv.put(COLUMN_M4, m4);
            cv.put(COLUMN_M5, m5);
            cv.put(COLUMN_M6, m6);
            cv.put(COLUMN_M7, m7);
            getDatabase().insert(table, null, cv);
        }
    }

    private ContentValues convertToContentValues(LotteryData data) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NUMBER, data.mNumber);
        cv.put(COLUMN_DATE, data.mDate);
        cv.put(COLUMN_M1, data.m1);
        cv.put(COLUMN_M2, data.m2);
        cv.put(COLUMN_M3, data.m3);
        cv.put(COLUMN_M4, data.m4);
        cv.put(COLUMN_M5, data.m5);
        cv.put(COLUMN_M6, data.m6);
        cv.put(COLUMN_M7, data.m7);
        return cv;
    }

    public void addData(int type, ArrayList<LotteryData> data) {
        String table = getTableName(type);
        if (table != null) {
            try {
                getDatabase().beginTransaction();
                for (LotteryData l : data) {
                    ContentValues cv = convertToContentValues(l);
                    getDatabase().insert(table, null, cv);
                }
                getDatabase().setTransactionSuccessful();
            } catch (SQLException e) {
            } finally {
                getDatabase().endTransaction();
            }
        }
    }

    private void createTables() {
        getDatabase().execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_HK6 + " ( " + COLUMN_NUMBER
                        + " INTEGER PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_M1
                        + " TEXT, " + COLUMN_M2 + " TEXT, " + COLUMN_M3 + " TEXT, " + COLUMN_M4
                        + " TEXT, " + COLUMN_M5 + " TEXT, " + COLUMN_M6 + " TEXT, " + COLUMN_M7
                        + " TEXT)");
        getDatabase().execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_LT539 + " ( " + COLUMN_NUMBER
                        + " INTEGER PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_M1
                        + " TEXT, " + COLUMN_M2 + " TEXT, " + COLUMN_M3 + " TEXT, " + COLUMN_M4
                        + " TEXT, " + COLUMN_M5 + " TEXT, " + COLUMN_M6 + " TEXT, " + COLUMN_M7
                        + " TEXT)");
        getDatabase().execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_WELI + " ( " + COLUMN_NUMBER
                        + " INTEGER PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_M1
                        + " TEXT, " + COLUMN_M2 + " TEXT, " + COLUMN_M3 + " TEXT, " + COLUMN_M4
                        + " TEXT, " + COLUMN_M5 + " TEXT, " + COLUMN_M6 + " TEXT, " + COLUMN_M7
                        + " TEXT)");
        getDatabase().execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_BLOT + " ( " + COLUMN_NUMBER
                        + " INTEGER PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_M1
                        + " TEXT, " + COLUMN_M2 + " TEXT, " + COLUMN_M3 + " TEXT, " + COLUMN_M4
                        + " TEXT, " + COLUMN_M5 + " TEXT, " + COLUMN_M6 + " TEXT, " + COLUMN_M7
                        + " TEXT)");
    }

    public boolean isTableEmpty(int type) {
        String table = getTableName(type);
        Cursor tableData = getDatabase().rawQuery("select count(*) from " + table, null);
        boolean rtn = true;
        if (tableData != null) {
            try {
                if (tableData.moveToFirst()) {
                    int count = tableData.getInt(0);
                    if (count > 0)
                        rtn = false;
                }
            } finally {
                tableData.close();
            }
        }
        return rtn;
    }

    private SQLiteDatabase getDatabase() {
        if ((mDb == null) || (mDb != null && mDb.isOpen() == false)) {
            try {
                mDb = getWritableDatabase();
            } catch (SQLiteFullException e) {
            } catch (SQLiteException e) {
            } catch (Exception e) {
            }
        }
        return mDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

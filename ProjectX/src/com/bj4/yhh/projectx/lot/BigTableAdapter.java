
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;
import java.util.HashMap;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.SharedPreferenceManager;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BigTableAdapter extends BaseAdapter implements DataLoadTask.Callback {

    public interface Callback {
        public void onCalculate(HashMap<Integer, Integer> resultMap);
    }

    private final int TOTAL_NUMBER_COUNT;

    private final int AWARDS_NUMBER_COUNT;

    private final int GAME_TYPE;

    private final int FRAGMENT_TYPE;

    private Context mContext;

    private LayoutInflater mInflater;

    private int mTableTextSize;

    private int mTableDateWidth;

    private int mTableNumberWidth;

    private ArrayList<LotteryData> mData = new ArrayList<LotteryData>();

    private ArrayList<LotteryData> mSubTotalData = new ArrayList<LotteryData>();

    private final ArrayList<Integer> mOrderedList = new ArrayList<Integer>();

    private final ArrayList<Integer> mSeperatedPositionList = new ArrayList<Integer>();

    private Callback mCallback;

    private int mGridColorResource = 0;

    private int mGridColorWithExtraRightResource = 0;

    private boolean mShowSubTotalOnly = false;

    public BigTableAdapter(Context context, final int gameType, final int fragmentType,
            final Callback cb) {
        GAME_TYPE = gameType;
        FRAGMENT_TYPE = fragmentType;
        TOTAL_NUMBER_COUNT = LotteryData.getTotalNumber(GAME_TYPE);
        AWARDS_NUMBER_COUNT = LotteryData.getAwardNumber(GAME_TYPE);
        mCallback = cb;
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initOrderedList();
        initData();
    }

    public static int getTableTextSize(Context context, final int totalNumber) {
        if (totalNumber < 40) {
            return SharedPreferenceManager.getInstance(context).get39TextSize();
        } else {
            return SharedPreferenceManager.getInstance(context).get49TextSize();
        }
    }

    public static int getTableDateWidth(Context context, final int totalNumber) {
        if (totalNumber < 40) {
            return (int)context.getResources().getDimension(R.dimen.table_date_width_39);
        } else {
            return (int)context.getResources().getDimension(R.dimen.table_date_width_49);
        }
    }

    public static int getTableNumberWidth(Context context, final int totalNumber) {
        if (totalNumber < 40) {
            return (int)SharedPreferenceManager.getInstance(context).get39NumberWidth();
        } else {
            return (int)SharedPreferenceManager.getInstance(context).get49NumberWidth();
        }
    }

    private void initOrderedList() {
        switch (FRAGMENT_TYPE) {
            case MainActivity.FRAGMENT_TYPE_LAST:
                mOrderedList.addAll(Utils.getLastList(TOTAL_NUMBER_COUNT));
                mSeperatedPositionList.addAll(Utils
                        .getLastListSeperatedPosition(TOTAL_NUMBER_COUNT));
                break;
            case MainActivity.FRAGMENT_TYPE_COMBINATION:
                mOrderedList.addAll(Utils.getCombinationList(TOTAL_NUMBER_COUNT));
                mSeperatedPositionList.addAll(Utils
                        .getCombinationListSeperatedPosition(TOTAL_NUMBER_COUNT));
                break;
            case MainActivity.FRAGMENT_TYPE_ORDERED:
                mOrderedList.addAll(Utils.getOrderedList(TOTAL_NUMBER_COUNT));
                mSeperatedPositionList.addAll(Utils
                        .getOrderedListSeperatedPosition(TOTAL_NUMBER_COUNT));
                break;
        }

    }

    private void initData() {
        mTableTextSize = getTableTextSize(mContext, TOTAL_NUMBER_COUNT);
        mTableDateWidth = getTableDateWidth(mContext, TOTAL_NUMBER_COUNT);
        mTableNumberWidth = getTableNumberWidth(mContext, TOTAL_NUMBER_COUNT);
        mGridColorResource = Utils.getGridColorResource(mContext);
        mGridColorWithExtraRightResource = Utils.getGridColorWithExtraRightResource(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DataLoadTask(mContext, GAME_TYPE, this)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new DataLoadTask(mContext, GAME_TYPE, this).execute();
        }
    }

    public boolean getShowSubTotal() {
        return mShowSubTotalOnly;
    }

    public void setShowSubTotal(boolean b) {
        mShowSubTotalOnly = b;
    }

    public void notifyDataSetChanged() {
        initData();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mShowSubTotalOnly ? mSubTotalData.size() : mData.size();
    }

    @Override
    public LotteryData getItem(int position) {
        return mShowSubTotalOnly ? mSubTotalData.get(position) : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LinearLayout container = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.big_table_rows, null);
            container = (LinearLayout)convertView;
            holder = new ViewHolder();
            holder.mDate = new TextView(mContext);
            holder.mDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTableTextSize);
            holder.mDate.setEllipsize(TruncateAt.END);
            holder.mDate.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mTableDateWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            container.addView(holder.mDate, ll);
            for (int i = 0; i < TOTAL_NUMBER_COUNT; i++) {
                TextView txt = new TextView(mContext);
                txt.setSingleLine();
                txt.setGravity(Gravity.CENTER);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTableTextSize);
                LinearLayout.LayoutParams tl = new LinearLayout.LayoutParams(mTableNumberWidth,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                container.addView(txt, tl);
                holder.mText.add(txt);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
            container = (LinearLayout)convertView;
        }
        holder.mDate.setBackgroundResource(mGridColorResource);
        LotteryData data = getItem(position);
        holder.mDate.setText(data.mDate);
        for (int i = 1; i < container.getChildCount(); i++) {
            final int indexNumber = mOrderedList.get(i - 1);
            if (data.mIsSubTotal) {
                if (!mShowSubTotalOnly) {
                    if (mSeperatedPositionList.contains(i)) {
                        holder.mText.get(i - 1).setBackgroundResource(R.drawable.red_column_bg);
                    } else {
                        holder.mText.get(i - 1).setBackgroundResource(R.drawable.red_column_bg);
                    }
                } else {
                    if (mSeperatedPositionList.contains(i)) {
                        holder.mText.get(i - 1).setBackgroundResource(
                                mGridColorWithExtraRightResource);
                    } else {
                        holder.mText.get(i - 1).setBackgroundResource(mGridColorResource);
                    }
                }
                holder.mText.get(i - 1).setText(String.valueOf(data.mSubTotal.get(indexNumber, 0)));
            } else {
                boolean show = false;
                if (AWARDS_NUMBER_COUNT >= 7) {
                    if (data.m1.mNumber == indexNumber || data.m2.mNumber == indexNumber
                            || data.m3.mNumber == indexNumber || data.m4.mNumber == indexNumber
                            || data.m5.mNumber == indexNumber || data.m6.mNumber == indexNumber
                            || data.m7.mNumber == indexNumber) {
                        show = true;
                    }
                } else if (AWARDS_NUMBER_COUNT >= 6) {
                    if (data.m1.mNumber == indexNumber || data.m2.mNumber == indexNumber
                            || data.m3.mNumber == indexNumber || data.m4.mNumber == indexNumber
                            || data.m5.mNumber == indexNumber || data.m6.mNumber == indexNumber) {
                        show = true;
                    }
                } else if (AWARDS_NUMBER_COUNT >= 5) {
                    if (data.m1.mNumber == indexNumber || data.m2.mNumber == indexNumber
                            || data.m3.mNumber == indexNumber || data.m4.mNumber == indexNumber
                            || data.m5.mNumber == indexNumber) {
                        show = true;
                    }
                } else if (AWARDS_NUMBER_COUNT >= 4) {
                    if (data.m1.mNumber == indexNumber || data.m2.mNumber == indexNumber
                            || data.m3.mNumber == indexNumber || data.m4.mNumber == indexNumber) {
                        show = true;
                    }
                } else if (AWARDS_NUMBER_COUNT >= 3) {
                    if (data.m1.mNumber == indexNumber || data.m2.mNumber == indexNumber
                            || data.m3.mNumber == indexNumber) {
                        show = true;
                    }
                } else if (AWARDS_NUMBER_COUNT >= 2) {
                    if (data.m1.mNumber == indexNumber || data.m2.mNumber == indexNumber) {
                        show = true;
                    }
                } else if (AWARDS_NUMBER_COUNT >= 1) {
                    if (data.m1.mNumber == indexNumber) {
                        show = true;
                    }
                }
                if (show) {
                    holder.mText.get(i - 1).setText(
                            String.valueOf(Utils.NUMBER_FORMATTER.format(indexNumber)));
                } else {
                    holder.mText.get(i - 1).setText(null);
                }
                if (mSeperatedPositionList.contains(i)) {
                    holder.mText.get(i - 1).setBackgroundResource(mGridColorWithExtraRightResource);
                } else {
                    holder.mText.get(i - 1).setBackgroundResource(mGridColorResource);
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        ArrayList<TextView> mText = new ArrayList<TextView>();

        TextView mDate;
    }

    @Override
    public void done(ArrayList<LotteryData> data) {
        mData.clear();
        mData.addAll(data);
        mSubTotalData.clear();
        for (LotteryData d : mData) {
            if (d.mIsSubTotal) {
                mSubTotalData.add(d);
            }
        }
        new CalculateTask(data, mCallback).execute();
        super.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
    }

    private static class CalculateTask extends AsyncTask<Void, Void, Void> {
        private ArrayList<LotteryData> mData;

        private Callback mCallback;

        private HashMap<Integer, Integer> mRtn = new HashMap<Integer, Integer>();

        public CalculateTask(ArrayList<LotteryData> data, Callback cb) {
            mData = data;
            mCallback = cb;
            for (int i = -10; i <= 100; i++) {
                mRtn.put(i, 0);
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (mData != null) {
                for (LotteryData data : mData) {
                    mRtn.put(data.m1.mNumber, mRtn.get(data.m1.mNumber) + 1);
                    mRtn.put(data.m2.mNumber, mRtn.get(data.m2.mNumber) + 1);
                    mRtn.put(data.m3.mNumber, mRtn.get(data.m3.mNumber) + 1);
                    mRtn.put(data.m4.mNumber, mRtn.get(data.m4.mNumber) + 1);
                    mRtn.put(data.m5.mNumber, mRtn.get(data.m5.mNumber) + 1);
                    mRtn.put(data.m6.mNumber, mRtn.get(data.m6.mNumber) + 1);
                    mRtn.put(data.m7.mNumber, mRtn.get(data.m7.mNumber) + 1);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mCallback != null) {
                mCallback.onCalculate(mRtn);
            }
        }
    }

}

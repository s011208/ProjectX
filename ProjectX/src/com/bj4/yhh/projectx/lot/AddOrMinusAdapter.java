
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import com.bj4.yhh.projectx.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddOrMinusAdapter extends BaseAdapter implements DataLoadTask.Callback {

    private static final String TAG = "QQQQ";

    private static final boolean DEBUG = false;

    private final int TOTAL_NUMBER_COUNT;

    private final int AWARDS_NUMBER_COUNT;

    private final int GAME_TYPE;

    private Context mContext;

    private LayoutInflater mInflater;

    private final int mTableTextSize;

    private final int mTableDateWidth;

    private final int mTableNumberWidth;

    private final ArrayList<LotteryData> mData = new ArrayList<LotteryData>();

    private int mComparedValue = 0;

    private int mGridColorResource = 0;

    private final ArrayList<Integer> mSubTotal = new ArrayList<Integer>();

    public AddOrMinusAdapter(Context context, final int gameType) {
        GAME_TYPE = gameType;
        TOTAL_NUMBER_COUNT = LotteryData.getTotalNumber(GAME_TYPE);
        AWARDS_NUMBER_COUNT = LotteryData.getAwardNumber(GAME_TYPE);
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources r = context.getResources();
        mTableTextSize = r.getInteger(R.integer.add_or_minus_table_text_size);
        mTableDateWidth = (int)r.getDimension(R.dimen.add_or_minus_table_date_width);
        mTableNumberWidth = (int)r.getDimension(R.dimen.add_or_minus_table_number_width);
        initData();
    }

    public void setComparedValue(int comparedValue) {
        mComparedValue = comparedValue;
        super.notifyDataSetChanged();
    }

    private void initData() {
        mGridColorResource = Utils.getGridColorResource(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DataLoadTask(mContext, GAME_TYPE, this)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new DataLoadTask(mContext, GAME_TYPE, this).execute();
        }
    }

    public void notifyDataSetChanged() {
        initData();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public LotteryData getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (DEBUG)
            Log.d(TAG, "getView position: " + position);
        ViewHolder holder = null;
        LinearLayout container = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.big_table_rows, null);
            container = (LinearLayout)convertView;
            holder = new ViewHolder();
            holder.mDate = new TextView(mContext);
            holder.mDate.setTextSize(mTableTextSize);
            holder.mDate.setEllipsize(TruncateAt.END);
            holder.mDate.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mTableDateWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            container.addView(holder.mDate, ll);
            for (int i = 0; i < AWARDS_NUMBER_COUNT; i++) {
                TextView txt = new TextView(mContext);
                txt.setSingleLine();
                txt.setGravity(Gravity.CENTER);
                txt.setTextSize(mTableTextSize);
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
        final LotteryData data = getItem(position);
        holder.mDate.setBackgroundResource(mGridColorResource);
        holder.mDate.setText(data.mDate);
        final boolean isSubTotal = data.mIsSubTotal;
        if (container.getChildCount() > 1) {
            refreshTextView(holder.mText.get(0), data.m1, isSubTotal);
        }
        if (container.getChildCount() > 2) {
            refreshTextView(holder.mText.get(1), data.m2, isSubTotal);
        }
        if (container.getChildCount() > 3) {
            refreshTextView(holder.mText.get(2), data.m3, isSubTotal);
        }
        if (container.getChildCount() > 4) {
            refreshTextView(holder.mText.get(3), data.m4, isSubTotal);
        }
        if (container.getChildCount() > 5) {
            refreshTextView(holder.mText.get(4), data.m5, isSubTotal);
        }
        if (container.getChildCount() > 6) {
            refreshTextView(holder.mText.get(5), data.m6, isSubTotal);
        }
        if (container.getChildCount() > 7) {
            refreshTextView(holder.mText.get(6), data.m7, isSubTotal);
        }
        return convertView;
    }

    private void refreshTextView(TextView txt, LotteryData.LotteryNumber dataValue,
            boolean isSubTotal) {
        if (dataValue.mNumber != LotteryData.NOT_USED) {
            txt.setText(String.valueOf(Utils.NUMBER_FORMATTER.format(dataValue.mNumber)));
            if (DEBUG)
                Log.v(TAG, "refreshTextView dataValue: " + dataValue.mNumber);
            if (dataValue.mIsHit) {
                txt.setTextColor(Color.RED);
            } else {
                txt.setTextColor(Color.BLACK);
            }
        } else {
            txt.setTextColor(Color.BLACK);
            txt.setText(null);
        }
        if (isSubTotal) {
            txt.setBackgroundResource(R.drawable.red_column_bg);
        } else {
            txt.setBackgroundResource(mGridColorResource);
        }
    }

    private int getComparedValue(LotteryData.LotteryNumber comparedValue) {
        final int rtn = ((comparedValue.mNumber - 1 + mComparedValue + TOTAL_NUMBER_COUNT) % TOTAL_NUMBER_COUNT) + 1;
        if (DEBUG)
            Log.i(TAG, "getComparedValue rtn: " + rtn);
        return rtn;
    }

    private boolean isHit(LotteryData.LotteryNumber dataValue, LotteryData comparedData) {
        if (comparedData == null) {
            return false;
        }
        if (comparedData.m1.mNumber != LotteryData.NOT_USED
                && comparedData.m1.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m2.mNumber != LotteryData.NOT_USED
                && comparedData.m2.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m3.mNumber != LotteryData.NOT_USED
                && comparedData.m3.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m4.mNumber != LotteryData.NOT_USED
                && comparedData.m4.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m5.mNumber != LotteryData.NOT_USED
                && comparedData.m5.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m6.mNumber != LotteryData.NOT_USED
                && comparedData.m6.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m7.mNumber != LotteryData.NOT_USED
                && comparedData.m7.mNumber == getComparedValue(dataValue)) {
            return true;
        }
        return false;
    }

    private static class ViewHolder {
        ArrayList<TextView> mText = new ArrayList<TextView>();

        TextView mDate;
    }

    @Override
    public void done(ArrayList<LotteryData> data) {
        mData.clear();
        mData.addAll(data);
        int m1, m2, m3, m4, m5, m6, m7;
        m1 = m2 = m3 = m4 = m5 = m6 = m7 = 0;
        for (int position = 0; position < mData.size(); position++) {
            final LotteryData currentData = mData.get(position);
            if (currentData.mIsSubTotal) {
                currentData.m1.mNumber = m1;
                currentData.m2.mNumber = m2;
                currentData.m3.mNumber = m3;
                currentData.m4.mNumber = m4;
                currentData.m5.mNumber = m5;
                currentData.m6.mNumber = m6;
                currentData.m7.mNumber = m7;
                m1 = m2 = m3 = m4 = m5 = m6 = m7 = 0;
            } else {
                LotteryData comparedData = null;
                try {
                    comparedData = mData.get(position + 1);
                    if (comparedData != null && comparedData.mIsSubTotal) {
                        comparedData = getItem(position + 2);
                    }
                    currentData.m1.mIsHit = isHit(currentData.m1, comparedData);
                    currentData.m2.mIsHit = isHit(currentData.m2, comparedData);
                    currentData.m3.mIsHit = isHit(currentData.m3, comparedData);
                    currentData.m4.mIsHit = isHit(currentData.m4, comparedData);
                    currentData.m5.mIsHit = isHit(currentData.m5, comparedData);
                    currentData.m6.mIsHit = isHit(currentData.m6, comparedData);
                    currentData.m7.mIsHit = isHit(currentData.m7, comparedData);
                    if (currentData.m1.mIsHit) {
                        ++m1;
                    }
                    if (currentData.m2.mIsHit) {
                        ++m2;
                    }
                    if (currentData.m3.mIsHit) {
                        ++m3;
                    }
                    if (currentData.m4.mIsHit) {
                        ++m4;
                    }
                    if (currentData.m5.mIsHit) {
                        ++m5;
                    }
                    if (currentData.m6.mIsHit) {
                        ++m6;
                    }
                    if (currentData.m7.mIsHit) {
                        ++m7;
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        super.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
    }

}


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
        LotteryData comparedData = null;
        try {
            comparedData = getItem(position + 1);
            if (comparedData != null && comparedData.mIsSubTotal) {
                comparedData = getItem(position + 2);
            }
        } catch (Exception e) {
            comparedData = null;
        }
        holder.mDate.setBackgroundResource(mGridColorResource);
        holder.mDate.setText(data.mDate);
        if (container.getChildCount() > 1) {
            refreshTextView(holder.mText.get(0), data.m1, comparedData);
        }
        if (container.getChildCount() > 2) {
            refreshTextView(holder.mText.get(1), data.m2, comparedData);
        }
        if (container.getChildCount() > 3) {
            refreshTextView(holder.mText.get(2), data.m3, comparedData);
        }
        if (container.getChildCount() > 4) {
            refreshTextView(holder.mText.get(3), data.m4, comparedData);
        }
        if (container.getChildCount() > 5) {
            refreshTextView(holder.mText.get(4), data.m5, comparedData);
        }
        if (container.getChildCount() > 6) {
            refreshTextView(holder.mText.get(5), data.m6, comparedData);
        }
        if (container.getChildCount() > 7) {
            refreshTextView(holder.mText.get(6), data.m7, comparedData);
        }
        return convertView;
    }

    private void refreshTextView(TextView txt, int dataValue, LotteryData comparedData) {
        if (dataValue != LotteryData.NOT_USED) {
            txt.setText(String.valueOf(Utils.NUMBER_FORMATTER.format(dataValue)));
            if (DEBUG)
                Log.v(TAG, "refreshTextView dataValue: " + dataValue);
            boolean isHit = isHit(dataValue, comparedData);
            if (isHit) {
                txt.setTextColor(Color.RED);
            } else {
                txt.setTextColor(Color.BLACK);
            }
        } else {
            txt.setTextColor(Color.BLACK);
            txt.setText(null);
        }
        txt.setBackgroundResource(mGridColorResource);
    }

    private int getComparedValue(int comparedValue) {
        final int rtn = ((comparedValue - 1 + mComparedValue + TOTAL_NUMBER_COUNT) % TOTAL_NUMBER_COUNT) + 1;
        if (DEBUG)
            Log.i(TAG, "getComparedValue rtn: " + rtn);
        return rtn;
    }

    private boolean isHit(int dataValue, LotteryData comparedData) {
        if (comparedData == null) {
            return false;
        }
        if (comparedData.m1 != LotteryData.NOT_USED
                && comparedData.m1 == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m2 != LotteryData.NOT_USED
                && comparedData.m2 == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m3 != LotteryData.NOT_USED
                && comparedData.m3 == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m4 != LotteryData.NOT_USED
                && comparedData.m4 == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m5 != LotteryData.NOT_USED
                && comparedData.m5 == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m6 != LotteryData.NOT_USED
                && comparedData.m6 == getComparedValue(dataValue)) {
            return true;
        }
        if (comparedData.m7 != LotteryData.NOT_USED
                && comparedData.m7 == getComparedValue(dataValue)) {
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
        super.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
    }

}

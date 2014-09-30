
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import com.bj4.yhh.projectx.MainActivity;
import com.bj4.yhh.projectx.R;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BigTableAdapter extends BaseAdapter implements DataLoadTask.Callback {

    private final int TOTAL_NUMBER_COUNT;

    private final int AWARDS_NUMBER_COUNT;

    private final int GAME_TYPE;

    private final int FRAGMENT_TYPE;

    private Context mContext;

    private LayoutInflater mInflater;

    private final float mTableTextSize;

    private final int mTableDateWidth;

    private final int mTableNumberWidth;

    private final ArrayList<LotteryData> mData = new ArrayList<LotteryData>();

    private final ArrayList<Integer> mOrderedList = new ArrayList<Integer>();

    public BigTableAdapter(Context context, final int gameType, final int fragmentType) {
        GAME_TYPE = gameType;
        FRAGMENT_TYPE = fragmentType;
        TOTAL_NUMBER_COUNT = LotteryData.getTotalNumber(GAME_TYPE);
        AWARDS_NUMBER_COUNT = LotteryData.getAwardNumber(GAME_TYPE);
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources r = context.getResources();
        mTableTextSize = r.getDimension(R.dimen.table_text_size);
        mTableDateWidth = (int)r.getDimension(R.dimen.table_date_width);
        mTableNumberWidth = (int)r.getDimension(R.dimen.table_number_width);
        switch (FRAGMENT_TYPE) {
            case MainActivity.FRAGMENT_TYPE_LAST:
                mOrderedList.addAll(Utils.getLastList(TOTAL_NUMBER_COUNT));
                break;
            case MainActivity.FRAGMENT_TYPE_COMBINATION:
                mOrderedList.addAll(Utils.getCombinationList(TOTAL_NUMBER_COUNT));
                break;
            case MainActivity.FRAGMENT_TYPE_ORDERED:
                mOrderedList.addAll(Utils.getOrderedList(TOTAL_NUMBER_COUNT));
                break;
        }

        initData();
    }

    private void initData() {
        new DataLoadTask(mContext, GAME_TYPE, this).execute();
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
            holder.mDate.setBackgroundResource(R.drawable.column_bg);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mTableDateWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            container.addView(holder.mDate, ll);
            for (int i = 0; i < TOTAL_NUMBER_COUNT; i++) {
                TextView txt = new TextView(mContext);
                txt.setSingleLine();
                txt.setGravity(Gravity.CENTER);
                txt.setTextSize(mTableTextSize);
                txt.setBackgroundResource(R.drawable.column_bg);
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
        LotteryData data = getItem(position);
        holder.mDate.setText(data.mDate);
        for (int i = 1; i < container.getChildCount(); i++) {
            final int indexNumber = mOrderedList.get(i - 1);
            boolean show = false;
            if (AWARDS_NUMBER_COUNT >= 7) {
                if (data.m1 == indexNumber || data.m2 == indexNumber || data.m3 == indexNumber
                        || data.m4 == indexNumber || data.m5 == indexNumber
                        || data.m6 == indexNumber || data.m7 == indexNumber) {
                    show = true;
                }
            } else if (AWARDS_NUMBER_COUNT >= 6) {
                if (data.m1 == indexNumber || data.m2 == indexNumber || data.m3 == indexNumber
                        || data.m4 == indexNumber || data.m5 == indexNumber
                        || data.m6 == indexNumber) {
                    show = true;
                }
            } else if (AWARDS_NUMBER_COUNT >= 5) {
                if (data.m1 == indexNumber || data.m2 == indexNumber || data.m3 == indexNumber
                        || data.m4 == indexNumber || data.m5 == indexNumber) {
                    show = true;
                }
            } else if (AWARDS_NUMBER_COUNT >= 4) {
                if (data.m1 == indexNumber || data.m2 == indexNumber || data.m3 == indexNumber
                        || data.m4 == indexNumber) {
                    show = true;
                }
            } else if (AWARDS_NUMBER_COUNT >= 3) {
                if (data.m1 == indexNumber || data.m2 == indexNumber || data.m3 == indexNumber) {
                    show = true;
                }
            } else if (AWARDS_NUMBER_COUNT >= 2) {
                if (data.m1 == indexNumber || data.m2 == indexNumber) {
                    show = true;
                }
            } else if (AWARDS_NUMBER_COUNT >= 1) {
                if (data.m1 == indexNumber) {
                    show = true;
                }
            }
            if (show) {
                holder.mText.get(i - 1).setText(
                        String.valueOf(Utils.NUMBER_FORMATTER.format(indexNumber)));
            } else {
                holder.mText.get(i - 1).setText(null);
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
        super.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
    }
}


package com.bj4.yhh.projectx.lot.hk6;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

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

public class AddAndMinusAdapter extends BaseAdapter {

    public static final int TOTAL_NUMBER_COUNT = 49;

    public static final int AWARDS_NUMBER_COUNT = 6;

    private Context mContext;

    private LayoutInflater mInflater;

    private final float mTableTextSize;

    private final int mTableDateWidth;

    private final int mTableNumberWidth;

    private final NumberFormat mNumberFormatter = new DecimalFormat("00");

    private final ArrayList<HK6Data> mData = new ArrayList<HK6Data>();

    public AddAndMinusAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources r = context.getResources();
        mTableTextSize = r.getDimension(R.dimen.table_text_size);
        mTableDateWidth = (int)r.getDimension(R.dimen.table_date_width);
        mTableNumberWidth = (int)r.getDimension(R.dimen.table_number_width);
        initData();
    }

    private void initData() {
        mData.clear();
        mData.add(new HK6Data(-1, -1, -1, -1, -1, -1, "日期"));
        for (int i = 1; i < 250; i++) {
            mData.add(new HK6Data(0, 1, 2, 3, 4, 49, "12月3日"));
        }
    }

    public void notifyDataSetChanged() {
        initData();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HK6Data getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.add_and_minus_table_rows, null);
            container = (LinearLayout)convertView;
            holder = new ViewHolder();
            holder.mDate = new TextView(mContext);
            holder.mDate.setTextSize(mTableTextSize);
            holder.mDate.setEllipsize(TruncateAt.END);
            holder.mDate.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mTableDateWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            container.addView(holder.mDate, ll);
            for (int i = 0; i < TOTAL_NUMBER_COUNT; i++) {
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
        if (position == 0) {
            HK6Data data = getItem(position);
            holder.mDate.setText(data.mDate);
            for (int i = 1; i < container.getChildCount(); i++) {
                holder.mText.get(i - 1).setText(String.valueOf(mNumberFormatter.format(i)));
            }
        } else {
            HK6Data data = getItem(position);
            holder.mDate.setText(data.mDate);
            for (int i = 1; i < container.getChildCount(); i++) {
                if (data.m1 == i || data.m2 == i || data.m3 == i || data.m4 == i || data.m5 == i
                        || data.m6 == i) {
                    holder.mText.get(i - 1).setText(String.valueOf(mNumberFormatter.format(i)));
                } else {
                    holder.mText.get(i - 1).setText(null);
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        ArrayList<TextView> mText = new ArrayList<TextView>();

        TextView mDate;
    }

}

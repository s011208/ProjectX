
package com.bj4.yhh.projectx.lot;

import java.util.ArrayList;

import com.bj4.yhh.projectx.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddOrMinusAdapter extends BaseAdapter implements DataLoadTask.Callback {

    private final int TOTAL_NUMBER_COUNT;

    private final int AWARDS_NUMBER_COUNT;

    private final int GAME_TYPE;

    private Context mContext;

    private LayoutInflater mInflater;

    private final float mTableTextSize;

    private final int mTableDateWidth;

    private final int mTableNumberWidth;

    private final ArrayList<LotteryData> mData = new ArrayList<LotteryData>();

    private int mComparedValue = 0;

    public static final int COMPARED_TO_PREVIOUS = 0;

    public static final int COMPARED_TO_NEXT = 1;

    private int mComparedDirection = COMPARED_TO_PREVIOUS;

    public AddOrMinusAdapter(Context context, final int gameType) {
        GAME_TYPE = gameType;
        TOTAL_NUMBER_COUNT = LotteryData.getTotalNumber(GAME_TYPE);
        AWARDS_NUMBER_COUNT = LotteryData.getAwardNumber(GAME_TYPE);
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources r = context.getResources();
        mTableTextSize = r.getDimension(R.dimen.add_or_minus_table_text_size);
        mTableDateWidth = (int)r.getDimension(R.dimen.add_or_minus_table_date_width);
        mTableNumberWidth = (int)r.getDimension(R.dimen.add_or_minus_table_number_width);
        initData();
    }

    public void setComparedValue(int comparedValue) {
        mComparedValue = comparedValue;
        super.notifyDataSetChanged();
    }

    public void setComparedDirection(int direction) {
        mComparedDirection = direction;
        super.notifyDataSetChanged();
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
            for (int i = 0; i < AWARDS_NUMBER_COUNT; i++) {
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
        LotteryData comparedData = null;
        try {
            switch (mComparedDirection) {
                case COMPARED_TO_PREVIOUS:
                    comparedData = getItem(position - 1);
                    break;
                case COMPARED_TO_NEXT:
                    comparedData = getItem(position + 1);
                    break;
            }
        } catch (Exception e) {
            comparedData = null;
        }
        holder.mDate.setText(data.mDate);
        if (container.getChildCount() > 1) {
            if (data.m1 != LotteryData.NOT_USED) {
                holder.mText.get(0).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m1)));
                boolean isHit = isHit(data.m1, comparedData, position);
                if (isHit) {
                    holder.mText.get(0).setTextColor(Color.RED);
                } else {
                    holder.mText.get(0).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(0).setText(null);
                holder.mText.get(0).setTextColor(Color.BLACK);
            }
        }
        if (container.getChildCount() > 2) {
            if (data.m2 != LotteryData.NOT_USED) {
                holder.mText.get(1).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m2)));
                boolean isHit = isHit(data.m2, comparedData, position);
                if (isHit) {
                    holder.mText.get(1).setTextColor(Color.RED);
                } else {
                    holder.mText.get(1).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(1).setTextColor(Color.BLACK);
                holder.mText.get(1).setText(null);
            }
        }
        if (container.getChildCount() > 3) {
            if (data.m3 != LotteryData.NOT_USED) {
                holder.mText.get(2).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m3)));
                boolean isHit = isHit(data.m3, comparedData, position);
                if (isHit) {
                    holder.mText.get(2).setTextColor(Color.RED);
                } else {
                    holder.mText.get(2).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(2).setTextColor(Color.BLACK);
                holder.mText.get(2).setText(null);
            }
        }
        if (container.getChildCount() > 4) {
            if (data.m4 != LotteryData.NOT_USED) {
                holder.mText.get(3).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m4)));
                boolean isHit = isHit(data.m4, comparedData, position);
                if (isHit) {
                    holder.mText.get(3).setTextColor(Color.RED);
                } else {
                    holder.mText.get(3).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(3).setTextColor(Color.BLACK);
                holder.mText.get(3).setText(null);
            }
        }
        if (container.getChildCount() > 5) {
            if (data.m5 != LotteryData.NOT_USED) {
                holder.mText.get(4).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m5)));
                boolean isHit = isHit(data.m5, comparedData, position);
                if (isHit) {
                    holder.mText.get(4).setTextColor(Color.RED);
                } else {
                    holder.mText.get(4).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(4).setTextColor(Color.BLACK);
                holder.mText.get(4).setText(null);
            }
        }
        if (container.getChildCount() > 6) {
            if (data.m6 != LotteryData.NOT_USED) {
                holder.mText.get(5).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m6)));
                boolean isHit = isHit(data.m6, comparedData, position);
                if (isHit) {
                    holder.mText.get(5).setTextColor(Color.RED);
                } else {
                    holder.mText.get(5).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(5).setTextColor(Color.BLACK);
                holder.mText.get(5).setText(null);
            }
        }
        if (container.getChildCount() > 7) {
            if (data.m7 != LotteryData.NOT_USED) {
                holder.mText.get(6).setText(String.valueOf(Utils.NUMBER_FORMATTER.format(data.m7)));
                boolean isHit = isHit(data.m7, comparedData, position);
                if (isHit) {
                    holder.mText.get(6).setTextColor(Color.RED);
                } else {
                    holder.mText.get(6).setTextColor(Color.BLACK);
                }
            } else {
                holder.mText.get(6).setTextColor(Color.BLACK);
                holder.mText.get(6).setText(null);
            }
        }
        return convertView;
    }

    private boolean isHit(int currentNumber, LotteryData comparedData, int currentPosition) {
        if (comparedData == null) {
            return false;
        }
        if (comparedData.m1 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m1 + mComparedValue) % TOTAL_NUMBER_COUNT) {
            return true;
        }
        if (comparedData.m2 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m2 + mComparedValue) % TOTAL_NUMBER_COUNT) {
            return true;
        }
        if (comparedData.m3 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m3 + mComparedValue) % TOTAL_NUMBER_COUNT) {
            return true;
        }
        if (comparedData.m4 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m4 + mComparedValue) % TOTAL_NUMBER_COUNT) {
            return true;
        }
        if (comparedData.m5 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m5 + mComparedValue) % TOTAL_NUMBER_COUNT) {
            return true;
        }
        if (comparedData.m6 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m6 + mComparedValue) % TOTAL_NUMBER_COUNT) {
            return true;
        }
        if (comparedData.m7 != LotteryData.NOT_USED
                && currentNumber == (comparedData.m7 + mComparedValue) % TOTAL_NUMBER_COUNT) {
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

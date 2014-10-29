
package com.bj4.yhh.projectx.lot.dialogs;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.lot.LotteryData;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AddNewDataDialog extends DialogFragment {
    public interface Callback {
        public void doPositive();
    }

    public static AddNewDataDialog newInstance(Callback cb, int gameType) {
        AddNewDataDialog fragment = new AddNewDataDialog();
        Bundle args = new Bundle();
        args.putInt("gameType", gameType);
        fragment.setArguments(args);
        fragment.setCallback(cb);
        return fragment;
    }

    private Callback mCallback;

    private void setCallback(Callback cb) {
        mCallback = cb;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int gameType = getArguments().getInt("gameType");
        return new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_input_add)
                .setTitle(getTitle(gameType)).setMessage(R.string.delete_confirm_dialog_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (mCallback != null) {
                            mCallback.doPositive();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
    }

    private String getTitle(int gameType) {
        String rtn = getActivity().getString(R.string.add_new_data) + " - ";
        switch (gameType) {
            case LotteryData.TYPE_HK6:
                rtn += getActivity().getString(R.string.hk6);
                break;
            case LotteryData.TYPE_539:
                rtn += getActivity().getString(R.string.lt539);
                break;
            case LotteryData.TYPE_WELI:
                rtn += getActivity().getString(R.string.weli);
                break;
            case LotteryData.TYPE_BLOT:
                rtn += getActivity().getString(R.string.blot);
                break;
        }
        return rtn;
    }
}

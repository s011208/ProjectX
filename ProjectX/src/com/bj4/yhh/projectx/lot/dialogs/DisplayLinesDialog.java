
package com.bj4.yhh.projectx.lot.dialogs;

import com.bj4.yhh.projectx.R;
import com.bj4.yhh.projectx.SharedPreferenceManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DisplayLinesDialog extends DialogFragment {
    public interface Callback {
        public void doPositive();
    }

    public static DisplayLinesDialog newInstance(Callback cb) {
        DisplayLinesDialog fragment = new DisplayLinesDialog();
        fragment.setCallback(cb);
        return fragment;
    }

    private Callback mCallback;

    private void setCallback(Callback cb) {
        mCallback = cb;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_input_add)
                .setTitle(getActivity().getString(R.string.display_lines))
                .setItems(
                        new CharSequence[] {
                                getActivity().getString(R.string.display_all_data), "50", "100",
                                "300", "500"
                        }, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferenceManager pref = SharedPreferenceManager
                                        .getInstance(getActivity());
                                switch (which) {
                                    case 0:
                                        pref.setDisplayLines(SharedPreferenceManager.DEFAULT_DISPLAY_LINES);
                                        break;
                                    case 1:
                                        pref.setDisplayLines(50);
                                        break;
                                    case 2:
                                        pref.setDisplayLines(100);
                                        break;
                                    case 3:
                                        pref.setDisplayLines(300);
                                        break;
                                    case 4:
                                        pref.setDisplayLines(500);
                                        break;
                                }
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
}

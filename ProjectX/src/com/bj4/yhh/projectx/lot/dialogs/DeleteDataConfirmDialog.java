
package com.bj4.yhh.projectx.lot.dialogs;

import com.bj4.yhh.projectx.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteDataConfirmDialog extends DialogFragment {
    public interface Callback {
        public void doPositive();
    }

    public static DeleteDataConfirmDialog newInstance(Callback cb) {
        DeleteDataConfirmDialog fragment = new DeleteDataConfirmDialog();
        fragment.setCallback(cb);
        return fragment;
    }

    private Callback mCallback;

    private void setCallback(Callback cb) {
        mCallback = cb;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_delete)
                .setTitle(R.string.delete_confirm_dialog_title)
                .setMessage(R.string.delete_confirm_dialog_message)
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
}

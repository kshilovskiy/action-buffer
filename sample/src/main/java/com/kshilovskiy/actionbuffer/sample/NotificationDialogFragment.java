package com.kshilovskiy.actionbuffer.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Dialog fragment with OK button and a message.
 */
public class NotificationDialogFragment extends DialogFragment {

    public static final String ARG_MESSAGE = "message";

    public static NotificationDialogFragment newInstance(String message) {
        NotificationDialogFragment frag = new NotificationDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(ARG_MESSAGE);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_notification_title)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
    }
}

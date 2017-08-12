package com.alex.mvptesting.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.alex.mvptesting.R;

public class MyDialogFragment extends DialogFragment {

    public static final String TAG = MyDialogFragment.class.getSimpleName();

    public static final int BUTTON_POSITIVE_CODE = -1;
    public static final int BUTTON_NEGATIVE_CODE = -2;

    public static final String BUNDLE_KEY_MESSAGE = "bundle_key_message";
    public static final String BUNDLE_KEY_POSITIVE_BUTTON = "bundle_key_positive_button";
    public static final String BUNDLE_KEY_NEGATIVE_BUTTON = "bundle_key_negative_button";

    private static OnClickListener onClickListener;

    private String message;
    private String positiveButtonText;
    private String negativeButtonText;

    public static MyDialogFragment newInstance(String message, String positiveButtonText,
                                               String negativeButtonText,
                                               OnClickListener onClickListener) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_MESSAGE, message);
        args.putString(BUNDLE_KEY_POSITIVE_BUTTON, positiveButtonText);
        args.putString(BUNDLE_KEY_NEGATIVE_BUTTON, negativeButtonText);
        fragment.setArguments(args);
        MyDialogFragment.onClickListener = onClickListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(BUNDLE_KEY_MESSAGE);
            positiveButtonText = getArguments().getString(BUNDLE_KEY_POSITIVE_BUTTON);
            negativeButtonText = getArguments().getString(BUNDLE_KEY_NEGATIVE_BUTTON);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogInterface.OnClickListener dialogInterfaceOnClickListener = (dialogInterface, i) -> {
            switch (i) {
                case BUTTON_NEGATIVE_CODE:
                    onClickListener.onNegativeButtonClick();
                    break;
                case BUTTON_POSITIVE_CODE:
                    onClickListener.onPositiveButtonClick();
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity(),
                R.style.AlertDialogStyle
        );
        builder.setMessage(message)
                .setNegativeButton(negativeButtonText, dialogInterfaceOnClickListener)
                .setPositiveButton(positiveButtonText, dialogInterfaceOnClickListener)
                .setCancelable(true);
        return builder.create();
    }

    public interface OnClickListener {

        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }
}
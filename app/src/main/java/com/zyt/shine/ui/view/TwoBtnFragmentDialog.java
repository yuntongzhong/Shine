package com.zyt.shine.ui.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by zyt on 2016/3/14.
 */
public class TwoBtnFragmentDialog extends DialogFragment {
    private String msg;
private String title;
private DialogInterface.OnClickListener listener;

    public TwoBtnFragmentDialog setListener(DialogInterface.OnClickListener listener) {
        this.listener=listener;
        return this;
    }
    public TwoBtnFragmentDialog setMsg(String msg){
        this.msg=msg;
        return this;
    }

    public TwoBtnFragmentDialog setTitle(String title){
        this.title=title;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg)
                .setPositiveButton("确定",
                        listener)
                .setNegativeButton("取消",
                        null);
        return builder.create();
    }
}

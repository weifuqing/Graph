package com.wfq.graph.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.wfq.graph.R;


/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class LoadingDialog extends Dialog{
    public LoadingDialog(@NonNull Context context) {
        this(context,0);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.loading_dialog);
        setCancelable(false);
        setContentView(R.layout.dialog_loading);
    }

}

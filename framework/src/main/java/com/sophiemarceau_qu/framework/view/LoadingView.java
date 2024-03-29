package com.sophiemarceau_qu.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sophiemarceau_qu.framework.R;
import com.sophiemarceau_qu.framework.manager.DialogManager;
import com.sophiemarceau_qu.framework.utils.AnimUtils;


public class LoadingView {
    private DialogView mLoadingView;
    private ImageView iv_loading;
    private TextView tv_loading_text;
    private ObjectAnimator mAnim;

    public LoadingView(Context mContext) {
        mLoadingView = DialogManager.getInstance().initView(mContext, R.layout.dialog_loding);
        iv_loading = mLoadingView.findViewById(R.id.iv_loading);
        tv_loading_text = mLoadingView.findViewById(R.id.tv_loading_text);
        mAnim = AnimUtils.rotation(iv_loading);
    }

    public void setLoadingText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tv_loading_text.setText(text);
        }
    }

    public void show() {
        mAnim.start();
        DialogManager.getInstance().show(mLoadingView);
    }

    public void show(String text) {
        mAnim.start();
        setLoadingText(text);
        DialogManager.getInstance().show(mLoadingView);
    }

    public void hide() {
        mAnim.pause();
        DialogManager.getInstance().hide(mLoadingView);
    }

    public void setCancelable(boolean flag) {
        mLoadingView.setCancelable(flag);
    }
}

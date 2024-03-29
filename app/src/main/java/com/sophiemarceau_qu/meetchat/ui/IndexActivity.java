package com.sophiemarceau_qu.meetchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sophiemarceau_qu.framework.base.BaseUIActivity;
import com.sophiemarceau_qu.framework.entity.Constants;
import com.sophiemarceau_qu.framework.utils.LogUtils;
import com.sophiemarceau_qu.framework.utils.SharePreferencesUtils;
import com.sophiemarceau_qu.meetchat.MainActivity;
import com.sophiemarceau_qu.meetchat.R;

public class IndexActivity extends BaseUIActivity {
    // TODO: 2019-11-07  启动页全屏
    // TODO: 2019-11-07 延迟进入主页
    // TODO: 2019-11-07  根据具体逻辑是进入主页还是引导页 还是登陆页
    // TODO: 2019-11-07 适配刘海屏
    private static final int SKIP_MAIN = 1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SKIP_MAIN:
                    startMain();
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 2 * 1000);
    }

    private void startMain() {
        Intent intent = new Intent();
        boolean isFirstApp = SharePreferencesUtils.getInstance().getBoolean(Constants.QXB_IS_FIRST_APP, true);
        LogUtils.e(""+isFirstApp);
        if (isFirstApp) {
            intent.setClass(this, GuideActivity.class);
            SharePreferencesUtils.getInstance().putBoolean(Constants.QXB_IS_FIRST_APP, false);
        } else {
            String token = SharePreferencesUtils.getInstance().getString(Constants.QXB_TOKEN, "");
            if (TextUtils.isEmpty(token)) {
                intent.setClass(this, LoginActivity.class);
            } else {
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}
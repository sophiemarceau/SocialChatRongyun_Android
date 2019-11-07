package com.sophiemarceau_qu.framework;

import android.content.Context;

import com.sophiemarceau_qu.framework.bmob.BmobManager;
import com.sophiemarceau_qu.framework.utils.LogUtils;
import com.sophiemarceau_qu.framework.utils.SharePreferencesUtils;

public class Framework {
    private volatile static Framework mFramework;

    private Framework() {
    }

    public static Framework getFramework() {
        if (mFramework == null) {
            synchronized (Framework.class) {
                if (mFramework == null) {
                    mFramework = new Framework();
                }
            }
        }
        return mFramework;
    }


    /**
     * 初始化框架 Model
     *
     * @param mContext
     */
    public void initFramework(Context mContext) {
        LogUtils.i("initFramework");
        SharePreferencesUtils.getInstance().initSp(mContext);
        BmobManager.getInstance().initBmob(mContext);
//        CloudManager.getInstance().initCloud(mContext);
//        LitePal.initialize(mContext);
//        MapManager.getInstance().initMap(mContext);
//        WindowHelper.getInstance().initWindow(mContext);
//        CrashReport.initCrashReport(mContext, "d51bdd38bd", BuildConfig.LOG_DEBUG);
//        ZXingLibrary.initDisplayOpinion(mContext);
//        NotificationHelper.getInstance().createChannel(mContext);
    }
}

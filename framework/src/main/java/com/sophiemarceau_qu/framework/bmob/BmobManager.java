package com.sophiemarceau_qu.framework.bmob;

import android.content.Context;

import com.sophiemarceau_qu.framework.utils.LogUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

public class BmobManager {
    private static final String BMOB_SDK_ID = "f8efae5debf319071b44339cf51153fc";
    private volatile static BmobManager mInstance = null;

    private BmobManager() {
    }

    public static BmobManager getInstance() {
        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager();
                }
            }
        }
        return mInstance;
    }

    public void initBmob(Context mContext) {
        Bmob.initialize(mContext, BMOB_SDK_ID);
    }

    public boolean isLogin() {
        return BmobUser.isLogin();
    }

    /**
     * 发送短信验证码
     *
     * @param phone    手机号码
     * @param listener 回调
     */
    public void requestSMS(String phone, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode(phone, "", listener);
    }

    /**
     * 通过手机号码注册或者登陆
     *
     * @param phone    手机号码
     * @param code     短信验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener) {
        LogUtils.e(phone);
        LogUtils.e(code);
        BmobUser.signOrLoginByMobilePhone(phone, code, listener);
    }
}

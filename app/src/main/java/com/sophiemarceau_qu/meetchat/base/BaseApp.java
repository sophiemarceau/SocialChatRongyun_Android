package com.sophiemarceau_qu.meetchat.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.sophiemarceau_qu.framework.Framework;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 冷启动 热启动 热启动 主要是优化冷启动
         * app内部
         * 1创建app对象/application对象
         * 2启动主线程（uithread）
         * 3创建应用入口 launcher
         * 4填充viewgroup中的view
         * 5.绘制View measure -> layout -> draw
         *
         *优化手段：
         *      * 1.视图优化
         *      *   1.设置主题透明
         *      *   2.设置启动图片
         *      * 2.代码优化
         *      *   1.优化Application
         *      *   2.布局的优化，不需要繁琐的布局
         *      *   3.阻塞UI线程的操作
         *      *   4.加载Bitmap/大图
         *      *   5.其他的一个占用主线程的操作
         *
         * 检测App Activity的启动时间
         *      * 1.Shell
         *      *   ActivityManager -> adb shell am start -S -W com.imooc.meet/com.imooc.meet.ui.IndexActivity
         *      *   ThisTime: 478ms 最后一个Activity的启动耗时
         *      *   TotalTime: 478ms 启动一连串Activity的总耗时
         *      *   WaitTime: 501ms 应用创建的时间 + TotalTime
         *      *   应用创建时间： WaitTime - TotalTime（501 - 478 = 23ms）
         *      * 2.Log
         *      *   Android 4.4 开始，ActivityManager增加了Log TAG = displayed
         *
         * Application的优化
         * 1.必要的组件在程序主页去初始化
         * 2.如果组件一定要在App中初始化，那么尽可能的延时
         * 3.非必要的组件，子线程中初始化
         */
        //只在主进程中初始化
        if (getApplicationInfo().packageName.equals(
                getCurProcessName(getApplicationContext()))) {
            //获取渠道
            //String flavor = FlavorHelper.getFlavor(this);
            //Toast.makeText(this, "flavor:" + flavor, Toast.LENGTH_SHORT).show();
            Framework.getFramework().initFramework(this);
        }
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess :
                activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}

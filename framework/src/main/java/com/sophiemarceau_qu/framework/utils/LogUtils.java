package com.sophiemarceau_qu.framework.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.sophiemarceau_qu.framework.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log日志打印，还可以记录日志File
 */
public class LogUtils {
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static void i(String text) {
        if (BuildConfig.LOG_DEBUG) {
            //每次调用都会判断是否为空 release
            if (!TextUtils.isEmpty(text)) {
                Log.i(BuildConfig.LOG_TAG, text);
//                writeToFile(text);
            }
        }
    }

    public static void e(String text) {
        if (BuildConfig.LOG_DEBUG) {
            //每次调用都会判断是否为空 release
            if (!TextUtils.isEmpty(text)) {
                Log.e(BuildConfig.LOG_TAG, text);
//                writeToFile(text);
            }
        }
    }

    private static void writeToFile(String text) {
        //文件路径
        String fileName = "/sdcard/meetchat/meetchat.log";
        // 时间 + 内容
        String log = mSimpleDateFormat.format(new Date()) + " " + text + "\n";
        //检查父路径
        File fileGroup = new File("/sdcard/meetchat/");
        if (!fileGroup.exists()) {
            fileGroup.mkdirs();
        }
        //开始写入
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(fileName, true);
            //编码问题 GBK 正确的存入中文
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream, Charset.forName("gbk")));
            bufferedWriter.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

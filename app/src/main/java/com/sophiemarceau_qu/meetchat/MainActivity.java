package com.sophiemarceau_qu.meetchat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sophiemarceau_qu.framework.utils.LogUtils;
import com.sophiemarceau_qu.framework.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LogUtils.i("hellword");
        LogUtils.e("sophiemarceau");

        LogUtils.i(TimeUtils.formatDuring(System.currentTimeMillis()));
    }
}

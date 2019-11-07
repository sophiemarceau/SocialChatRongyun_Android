package com.sophiemarceau_qu.meetchat;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sophiemarceau_qu.framework.base.BaseUIActivity;
import com.sophiemarceau_qu.framework.manager.MediaPlayerManager;
import com.sophiemarceau_qu.framework.utils.LogUtils;
import com.sophiemarceau_qu.framework.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseUIActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.e("sophiemarceau");

        LogUtils.i(TimeUtils.formatDuring(System.currentTimeMillis()));

        final MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.guide);
        mediaPlayerManager.startPlay(fileDescriptor);


        mediaPlayerManager.setOnProgressListener(new MediaPlayerManager.OnMusicProgressListener() {
            @Override
            public void OnProgress(int progress,int pos) {


            }
        });
    }
}

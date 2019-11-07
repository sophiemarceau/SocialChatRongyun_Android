package com.sophiemarceau_qu.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.sophiemarceau_qu.framework.utils.LogUtils;

import java.io.IOException;

public class MediaPlayerManager {
    public static final int MEDIA_STATUS_PLAY = 0;
    public static final int MEDIA_STATUS_PAUSE = 1;
    public static final int MEDIA_STATUS_STOP = 2;

    public static int MEDIA_STATUS = MEDIA_STATUS_STOP;
    private static final int H_PROGRESS = 1000;
    private MediaPlayer mMediaPlayer;
    private OnMusicProgressListener musicProgressListener;

    /**
     * 计算歌曲的进度
     * 开始播放的时候就开始循环计算时长
     * 将进度计算结果对外抛出
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case H_PROGRESS:
                    if (musicProgressListener != null) {
                        int currentPostion = getCurrentPosition();
                        int pos = (int) (((float) currentPostion) / ((float) getDuration()) * 100);
                        musicProgressListener.OnProgress(currentPostion, pos);
                        mHandler.sendEmptyMessageDelayed(H_PROGRESS, 1000);
                    }
            }
            return false;
        }
    });

    public MediaPlayerManager() {
        mMediaPlayer = new MediaPlayer();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startPlay(AssetFileDescriptor path) {

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            LogUtils.e("startplay");
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

    public void pausePlay() {
        if (isPlaying()) {
            mMediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            mHandler.removeMessages(H_PROGRESS);
        }

    }

    public void continuePlay() {
        mMediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage(H_PROGRESS);
    }

    public void stopPlay() {
        mMediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        mHandler.removeMessages(H_PROGRESS);
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public void looping(boolean isLooping) {
        mMediaPlayer.setLooping(isLooping);
    }

    public void seekTo(int ms) {
        mMediaPlayer.seekTo(ms);
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(listener);
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener(listener);
    }

    public void setOnProgressListener(OnMusicProgressListener listener) {
        musicProgressListener = listener;
    }


    public interface OnMusicProgressListener {
        void OnProgress(int progress, int pos);
    }
}

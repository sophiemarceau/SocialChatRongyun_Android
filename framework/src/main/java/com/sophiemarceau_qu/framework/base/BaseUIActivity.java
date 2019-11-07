package com.sophiemarceau_qu.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sophiemarceau_qu.framework.utils.SystemUI;

public class BaseUIActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUI.fixSystemUI(this);
    }
}

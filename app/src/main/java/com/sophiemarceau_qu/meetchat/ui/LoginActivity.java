package com.sophiemarceau_qu.meetchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sophiemarceau_qu.framework.base.BaseUIActivity;
import com.sophiemarceau_qu.framework.bmob.BmobManager;
import com.sophiemarceau_qu.framework.bmob.IMUser;
import com.sophiemarceau_qu.framework.entity.Constants;
import com.sophiemarceau_qu.framework.manager.DialogManager;
import com.sophiemarceau_qu.framework.utils.LogUtils;
import com.sophiemarceau_qu.framework.utils.SharePreferencesUtils;
import com.sophiemarceau_qu.framework.view.DialogView;
import com.sophiemarceau_qu.framework.view.LoadingView;
import com.sophiemarceau_qu.framework.view.TouchPictureV;
import com.sophiemarceau_qu.meetchat.MainActivity;
import com.sophiemarceau_qu.meetchat.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * /**
 * * 1.点击发送的按钮，弹出一个提示框，图片验证码，验证通过之后
 * * 2.!发送验证码，@同时按钮变成不可点击，@按钮开始倒计时，倒计时结束，@按钮可点击，@文字变成“发送”
 * * 3.通过手机号码和验证码进行登录
 * * 4.登录成功之后获取本地对象
 */


public class LoginActivity extends BaseUIActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etCode;
    private Button btnSendCode;
    private Button btnLogin;
    private TextView tvTestLogin;
    private TextView tvUserAgreement;

    private DialogView mCodeView;
    private TextView tv_test_login;
    private LoadingView mLoadingView;
    private TouchPictureV mPictureV;

    private static final int H_TIME = 1001;
    private static int TIME = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case H_TIME:
                    TIME--;
                    btnSendCode.setText(TIME + "s");
                    if (TIME > 0) {
                        mHandler.sendEmptyMessageDelayed(H_TIME, 1);
                    } else {
                        btnSendCode.setEnabled(true);
                        btnSendCode.setText(getString(R.string.text_login_send));
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        initDialogView();
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        btnSendCode = (Button) findViewById(R.id.btn_send_code);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvTestLogin = (TextView) findViewById(R.id.tv_test_login);
        tvUserAgreement = (TextView) findViewById(R.id.tv_user_agreement);
    }

    private void initDialogView() {
        mLoadingView = new LoadingView(this);
        mCodeView = DialogManager.getInstance().initView(this, R.layout.dialog_code_view);
        mPictureV = mCodeView.findViewById(R.id.mPictureV);
        mPictureV.setViewResultListener(new TouchPictureV.OnViewResultListener() {
            @Override
            public void onResult() {
                DialogManager.getInstance().hide(mCodeView);
                sendSMS();
            }
        });
    }

    /**
     * 发送短信验证码
     */
    private void sendSMS() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null), Toast.LENGTH_SHORT).show();
            return;
        }
        BmobManager.getInstance().requestSMS(phone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    btnSendCode.setEnabled(false);
                    mHandler.sendEmptyMessage(H_TIME);
                    Toast.makeText(LoginActivity.this, getString(R.string.text_user_resuest_succeed), Toast.LENGTH_SHORT).show();
                } else {
                    LogUtils.e("SMS:" + e.toString());
                    Toast.makeText(LoginActivity.this, getString(R.string.text_user_resuest_fail),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(){
        //1.判断手机号码和验证码不为空
        final String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, getString(R.string.text_login_code_null),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //显示LodingView
        mLoadingView.show(getString(R.string.text_login_now_login_text));
        BmobManager.getInstance().signOrLoginByMobilePhone(phone, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                if (e == null) {
                    mLoadingView.hide();
                    //登陆成功
                    startActivity(new Intent(
                            LoginActivity.this, MainActivity.class));
                    //把手机号码保存下来
                    SharePreferencesUtils.getInstance().putString(Constants.QXB_PHONE, phone);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "ERROR:" + e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_code:
                DialogManager.getInstance().show(mCodeView);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_test_login:
//                startActivity(new Intent(this, TestLoginActivity.class));
                break;
        }
    }
}

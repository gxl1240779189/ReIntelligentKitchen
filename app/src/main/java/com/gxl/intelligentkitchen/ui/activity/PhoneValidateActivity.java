package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.model.UserModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.helper.CountDownButtonHelper;
import com.gxl.intelligentkitchen.utils.StringUtils;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：手机验证页面
 */
public class PhoneValidateActivity extends Activity {
    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_get_check_pass)
    Button registerGetCheckPass;
    @Bind(R.id.register_checknum)
    EditText register_checknum;
    @Bind(R.id.register_layout)
    LinearLayout registerLayout;
    @Bind(R.id.register_btn)
    Button registerBtn;
    EventHandler eh;
    @Bind(R.id.register_phone)
    EditText registerPhone;

    private UserModel mUserModel = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.phonevalidate_activity_main);
        ButterKnife.bind(this);
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Intent intent = new Intent(PhoneValidateActivity.this, RegisterActivity.class);
                        intent.putExtra("phone", registerPhone.getText().toString());
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    } else if (event == SMSSDK.RESULT_ERROR) {
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @OnClick({R.id.register_back, R.id.register_get_check_pass, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_get_check_pass:
                if (StringUtils.isMobileNO(registerPhone.getText().toString())) {
                    mUserModel.isPhoneRegister(registerPhone.getText().toString(), new FoodModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            ToastUtils.showLong(PhoneValidateActivity.this, "当前手机号码已注册，请直接登录");
                            startActivity(new Intent(PhoneValidateActivity.this, LoginActivity.class));
                        }

                        @Override
                        public void getFailure() {
                            SMSSDK.getVerificationCode("86", registerPhone.getText().toString());
                            CountDownButtonHelper countDownButtonHelper = new CountDownButtonHelper(registerGetCheckPass, "获取验证码", 60, 1);
                            countDownButtonHelper.start();
                            countDownButtonHelper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
                                @Override
                                public void finish() {
                                    registerGetCheckPass.setEnabled(true);
                                }
                            });
                        }
                    });
                } else {
                    ToastUtils.showLong(PhoneValidateActivity.this, "手机号码格式不正确");
                }
                break;
            case R.id.register_btn:
                if (!TextUtils.isEmpty(register_checknum.getText().toString()) && !TextUtils.isEmpty(registerPhone.getText().toString())) {
                    SMSSDK.submitVerificationCode("86", registerPhone.getText().toString(), register_checknum.getText().toString());
                }
                break;
        }
    }
}

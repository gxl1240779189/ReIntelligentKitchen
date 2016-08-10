package com.gxl.intelligentkitchen.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.eventbus.UserEventBus;
import com.gxl.intelligentkitchen.model.UserModel;
import com.gxl.intelligentkitchen.presenter.UserFragmentPresenter;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class RegisterActivity extends Activity {

    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_phone)
    EditText registerPhone;
    @Bind(R.id.register_name)
    EditText registerName;
    @Bind(R.id.register_password)
    EditText registerPassword;
    @Bind(R.id.register_layout)
    LinearLayout registerLayout;
    @Bind(R.id.register_btn)
    Button registerBtn;
    @Bind(R.id.register_info)
    CheckBox registerInfo;

    private UserModel mUserModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_activity_main);
        ButterKnife.bind(this);
        mUserModel = new UserModel();
    }

    @OnClick({R.id.register_back, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_btn:
                if (!TextUtils.isEmpty(registerPhone.getText().toString()) && !TextUtils.isEmpty(registerName.getText().toString()) && !TextUtils.isEmpty(registerPassword.getText().toString())) {
                    User user = new User();
                    user.setName(registerName.getText().toString());
                    user.setPassword(registerPassword.getText().toString());
                    user.setNumber(registerPhone.getText().toString());
                    mUserModel.onRegister(user);
                    finish();
                } else {
                    ToastUtils.showLong(RegisterActivity.this, "请填写完整信息");
                }
                break;
        }
    }
}

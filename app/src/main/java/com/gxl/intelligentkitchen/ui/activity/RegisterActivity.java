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

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.model.UserModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends Activity {


    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_name)
    EditText registerName;
    @Bind(R.id.register_password)
    EditText registerPassword;
    @Bind(R.id.register_btn)
    Button registerBtn;

    private String mPhone;
    private UserModel mUserModel = new UserModel();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_activity_main);
        ButterKnife.bind(this);
        mPhone = getIntent().getStringExtra("phone");
    }

    @OnClick({R.id.register_back, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_btn:
                if (!TextUtils.isEmpty(registerName.getText().toString()) && !TextUtils.isEmpty(registerPassword.getText().toString())) {
                    User user = new User();
                    user.setName(registerName.getText().toString());
                    user.setPassword(registerPassword.getText().toString());
                    user.setNumber(mPhone);
                    mUserModel.onRegister(user, new FoodModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }

                        @Override
                        public void getFailure() {

                        }
                    });
                    finish();
                } else {
                    ToastUtils.showLong(RegisterActivity.this, "请填写完整信息");
                }
                break;
        }
    }
}

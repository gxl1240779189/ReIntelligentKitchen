package com.gxl.intelligentkitchen.model;

import android.widget.Toast;

import com.gxl.intelligentkitchen.application.BaseApplication;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.model.impl.UserModelImpl;
import com.gxl.intelligentkitchen.utils.SPUtils;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class UserModel implements UserModelImpl {

    private final String LOGINUSER = "loginuser";

    /**
     * 用户登录验证
     *
     * @param name
     * @param passoword
     * @param listener
     */
    @Override
    public void getUser(String name, String passoword, final FoodModelImpl.BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("Name", name);
        query.addWhereEqualTo("Password", passoword);
        query.setLimit(1);
        query.findObjects(BaseApplication.getmContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                if (object != null && object.size() != 0) {
                    SPUtils.put(BaseApplication.getmContext(), LOGINUSER, object.get(0));
                    listener.getSuccess(object.get(0));
                } else {
                    listener.getFailure();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 根据objectId获取User
     *
     * @param objectId
     * @param listener
     */
    @Override
    public void getUser(String objectId, final FoodModelImpl.BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId",objectId);
        query.setLimit(1);
        query.findObjects(BaseApplication.getmContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                if (object != null && object.size() != 0) {
                    SPUtils.put(BaseApplication.getmContext(), LOGINUSER, object.get(0));
                    listener.getSuccess(object.get(0));
                } else {
                    listener.getFailure();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 判断当前用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        if (SPUtils.contains(BaseApplication.getmContext(), LOGINUSER)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 注册功能
     * @param user
     */
    public void onRegister(User user) {
        user.save(BaseApplication.getmContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showLong(BaseApplication.getmContext(), "注册成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showLong(BaseApplication.getmContext(), "注册失败");
            }
        });
    }


}

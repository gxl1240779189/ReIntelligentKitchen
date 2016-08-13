package com.gxl.intelligentkitchen.eventbus;

import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.entity.UserLocal;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：用户登录的EventBus传递对象
 */
public class UserEventBus {
    public UserLocal getmUser() {
        return mUser;
    }

    public void setmUser(UserLocal mUser) {
        this.mUser = mUser;
    }

    private UserLocal mUser;

    public UserEventBus(UserLocal mUser) {
        this.mUser = mUser;
    }
}

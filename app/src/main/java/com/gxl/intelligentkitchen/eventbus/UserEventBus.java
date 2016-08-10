package com.gxl.intelligentkitchen.eventbus;

import com.gxl.intelligentkitchen.entity.User;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：用户登录的EventBus传递对象
 */
public class UserEventBus {
    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    private User mUser;

    public UserEventBus(User mUser) {
        this.mUser = mUser;
    }
}

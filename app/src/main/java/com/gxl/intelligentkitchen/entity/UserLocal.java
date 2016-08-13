package com.gxl.intelligentkitchen.entity;

import android.graphics.AvoidXfermode;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：本地数据库存储的登录对象
 */
@Table(name = "UserLocal")
public class UserLocal extends Model implements Serializable {

    //对象Id
    @Column(name = "objectId")
    String objectId;
    //姓名
    @Column(name = "Name")
    String Name;
    //头像
    @Column(name = "Photo")
    String Photo;

    //密码
    @Column(name = "Password")
    String Password;
    //手机号码
    @Column(name = "Number")
    String Number;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}

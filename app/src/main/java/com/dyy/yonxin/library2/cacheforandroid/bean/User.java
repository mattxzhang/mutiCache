package com.dyy.yonxin.library2.cacheforandroid.bean;

import java.io.Serializable;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public class User implements Serializable{
    private int userId;
    private String name;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

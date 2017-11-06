package com.dyy.yonxin.library2.cacheforandroid.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by 段钰莹 on 2017/11/3.
 */
@Entity
public class DBUserNoKey {
    private String name;
    private String password;
    @Generated(hash = 765217713)
    public DBUserNoKey(String name, String password) {
        this.name = name;
        this.password = password;
    }
    @Generated(hash = 624742347)
    public DBUserNoKey() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Keep
    public DBUserNoKey cloneDBUserNoKey(){
        DBUserNoKey dbUserNoKey = new DBUserNoKey();
        dbUserNoKey.setName(name);
        dbUserNoKey.setPassword(password);
        return dbUserNoKey;
    }
}

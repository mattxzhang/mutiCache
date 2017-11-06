package com.dyy.yonxin.library2.cacheforandroid.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by 段钰莹 on 2017/11/2.
 */
@Entity
public class DBUser {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    @Generated(hash = 1998396594)
    public DBUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 138933025)
    public DBUser() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Keep
    public DBUser cloneDBUser(){
        DBUser user = new DBUser();
        user.setName(name);
        return user;
    }
}

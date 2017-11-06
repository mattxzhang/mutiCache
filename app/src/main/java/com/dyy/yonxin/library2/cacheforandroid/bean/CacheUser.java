package com.dyy.yonxin.library2.cacheforandroid.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

@Entity
public class CacheUser {
    private int userId;
    private String userName;
    private long saveInDBTime;
    @Transient
    private List<String> friends;
    private String jsonFriends = "";
    @Generated(hash = 1070403483)
    public CacheUser(int userId, String userName, long saveInDBTime,
            String jsonFriends) {
        this.userId = userId;
        this.userName = userName;
        this.saveInDBTime = saveInDBTime;
        this.jsonFriends = jsonFriends;
    }

    @Generated(hash = 1788505076)
    public CacheUser() {
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getFriends() {
        return friends;
    }
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getJsonFriends() {
        return jsonFriends;
    }

    public void setJsonFriends(String jsonFriends) {
        this.jsonFriends = jsonFriends;
    }

    public long getSaveInDBTime() {
        return this.saveInDBTime;
    }

    public void setSaveInDBTime(long saveInDBTime) {
        this.saveInDBTime = saveInDBTime;
    }

    @Keep
    public String getAllString(){
        return "userId="+userId+"\n"
                +"userName="+userName+"\n"
                +"friends="+friends+"\n"
                +"jsonfriends="+jsonFriends+"\n";
    }
}

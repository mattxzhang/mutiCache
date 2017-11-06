package com.dyy.yonxin.library2.cacheforandroid.bean;

/**
 * Created by 段钰莹 on 2017/9/4.
 */

public class CacheBean {
    private String key;
    private String value;
    private long timeStamp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj instanceof CacheBean){
            CacheBean outerCacheBean = (CacheBean) obj;
            return key.equals(outerCacheBean.getKey());
        }
        return false;
    }
}

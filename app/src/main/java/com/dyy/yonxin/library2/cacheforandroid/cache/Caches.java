package com.dyy.yonxin.library2.cacheforandroid.cache;

/**
 * Created by 段钰莹 on 2017/11/7.
 */

abstract class Caches<T> implements ICacheWay<T>{
    ICacheWay<T> nextCache;
    long overDueTime = 10 * 1000;
    boolean needClean = true;
    protected boolean singleObj = false;
    @Override
    public void setNextCache(ICacheWay<T> nextCache) {
        this.nextCache = nextCache;
    }

    @Override
    public void setCacheSaveTime(long overDueTime) {
        this.overDueTime = overDueTime;
    }

    @Override
    public void setCleanOverDueTime(boolean needClean) {
        this.needClean = needClean;
    }

    @Override
    public void setObjSingle(boolean singleObj) {
        this.singleObj = singleObj;
    }
}

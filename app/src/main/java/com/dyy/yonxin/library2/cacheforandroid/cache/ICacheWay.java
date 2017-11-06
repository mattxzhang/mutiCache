package com.dyy.yonxin.library2.cacheforandroid.cache;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public interface ICacheWay<T> {
    void setNextCache(ICacheWay<T> nextCache);
    void setCacheSaveTime(long overDueTime);
    T getCache(T t);
    void clearCacheOverDueTime(T t);
    void saveObjInCache(T t);
    void clearCache(T t);
}

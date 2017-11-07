package com.dyy.yonxin.library2.cacheforandroid.cache;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheBean;
import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.GsonUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class ListCache<T> implements ICacheWay<T> {
    private ICacheWay<T> nextCache;
    private long overDueTime =  5 * 1000;//最佳为1/2的Share时间
    private boolean needClean = true;
    private static final String CACHE_USER = "cacheUser";
    private static List<CacheBean> caches = new ArrayList<>();

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

    private <T> String getCacheName(T t) {
        if (t instanceof CacheUser)
            return CACHE_USER;
        return "";
    }

    @Override
    public T getCache(T t) {
        clearCacheOverDueTime(t);
        T cacheObj = getCacheInList(t);

        if(cacheObj!=null){
            CacheUtil.cacheMsg = "从ListCache拿到数据\n";
            return cacheObj;
        }else if(nextCache!=null){
            cacheObj = nextCache.getCache(t);
            if(cacheObj!=null){
                saveObjInCache(cacheObj);
                return cacheObj;
            }
        }
        CacheUtil.cacheMsg = "没拿到数据\n";
        return null;
    }

    private T getCacheInList(T t) {
        CacheBean mCacheBean = new CacheBean();
        String mCacheName = getCacheName(t);
        mCacheBean.setKey(mCacheName);
        try {
            if (caches.contains(mCacheBean))
                return getCacheByListKey(mCacheName, t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T getCacheByListKey(String cacheName, T t) throws IllegalAccessException, InstantiationException {
        for (int i = 0; i < caches.size(); i++) {
            CacheBean mCacheBean = caches.get(i);
            if (caches.get(i).getKey().equals(cacheName)) {
                return GsonUtil.getObjectByJson(mCacheBean.getValue(), t);
            }
        }
        return t;
    }

    @Override
    public void clearCacheOverDueTime(T t) {
        if(!needClean)
            return;
        if (caches.size() == 0)
            return;
        for (int i = caches.size() - 1; i >= 0; i--) {
            CacheBean cacheBean = caches.get(i);
            if (TimeUtils.getTimeStampDif(cacheBean.getTimeStamp()) > overDueTime) {
                caches.remove(i);
            }
        }
    }

    @Override
    public void saveObjInCache(T t) {
        CacheBean mCacheBean = new CacheBean();
        mCacheBean.setKey(getCacheName(t));
        mCacheBean.setValue(GsonUtil.getJsonByObject(t));
        mCacheBean.setTimeStamp(TimeUtils.getNowTimeStamp());
        caches.add(mCacheBean);
    }

    @Override
    public void clearCache(T t) {
        if(caches.size() == 0)
            return;
        for (int i = caches.size() - 1; i >= 0; i--) {
            CacheBean cacheBean = caches.get(i);
            String cacheName = getCacheName(t);
            String keyName = cacheBean.getKey();
            if (cacheName.equals(keyName)) {
                caches.remove(i);
            }
        }
    }

}

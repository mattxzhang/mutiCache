package com.dyy.yonxin.library2.cacheforandroid.cache;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.GsonUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.ShareUtils;
import com.dyy.yonxin.library2.cacheforandroid.util.TimeUtils;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class ShareCache<T> implements ICacheWay<T> {
    private ICacheWay<T> nextCache;
    private long overDueTime = 10 * 1000;//最佳为1/2的DB时间
    private static final String CACHE_USER = "cacheUser";

    @Override
    public void setNextCache(ICacheWay<T> nextCache) {
        this.nextCache = nextCache;
    }

    @Override
    public void setCacheSaveTime(long overDueTime) {
        this.overDueTime = overDueTime;
    }

    private <T> String getCacheName(T t) {
        if (t instanceof CacheUser)
            return CACHE_USER;
        return "";
    }

    @Override
    public T getCache(T t) {
        clearCacheOverDueTime(t);
        T cacheObj = getCacheShare(t);
        if(cacheObj!=null){
            CacheUtil.cacheMsg = "从ShareCache拿到数据\n";
            return cacheObj;
        } else if(nextCache!=null){
            cacheObj = nextCache.getCache(t);
            if(cacheObj!=null)
                saveObjInCache(cacheObj);
            return cacheObj;
        }
        return null;
    }

    private T getCacheShare(T t) {
        String cacheName = getCacheName(t);
        long timestamp = ShareUtils.getInstance().resetShare(cacheName)
                .getLong("timestamp");
        if(timestamp!=0){
            String objJson = ShareUtils.getInstance().resetShare(cacheName)
                    .getString("objContent");
            return GsonUtil.getObjectByJson(objJson,t);
        }
        return null;
    }

    @Override
    public void clearCacheOverDueTime(T t) {
        String cacheName = getCacheName(t);
        long timestamp = ShareUtils.getInstance().resetShare(cacheName)
                .getLong("timestamp");
        if(TimeUtils.getTimeStampDif(timestamp)>overDueTime){
            ShareUtils.getInstance().resetShare(cacheName)
                    .set("timestamp",0L)
                    .commit();
        }

    }

    @Override
    public void saveObjInCache(T t) {
        String cacheName = getCacheName(t);
        String json = GsonUtil.getJsonByObject(t);
        ShareUtils.getInstance().resetShare(cacheName)
                .set("timestamp",TimeUtils.getNowTimeStamp())
                .set("objContent",json)
                .commit();
    }

    @Override
    public void clearCache(T t) {
        String cacheName = getCacheName(t);
        ShareUtils.getInstance().resetShare(cacheName)
                .set("timestamp",0L)
                .commit();
    }

}

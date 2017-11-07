package com.dyy.yonxin.library2.cacheforandroid.cache;

import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.GsonUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.ShareUtils;
import com.dyy.yonxin.library2.cacheforandroid.util.TimeUtils;

/**
 * Created by 段钰莹 on 2017/11/6.
 * 添加新类别时，需修改getCacheName,不同对象用不同share文件
 */

public abstract class ShareCache<T> extends Caches<T> {
    public abstract String getCacheName(T t);

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
        long timestamp = ShareUtils.resetShare(cacheName)
                .getLong("timestamp");
        if(timestamp!=0){
            String objJson = ShareUtils.resetShare(cacheName)
                    .getString("objContent");
            return GsonUtil.getObjectByJson(objJson,t);
        }
        return null;
    }

    @Override
    public void clearCacheOverDueTime(T t) {
        if(!needClean)
            return;
        String cacheName = getCacheName(t);
        long timestamp = ShareUtils.resetShare(cacheName)
                .getLong("timestamp");
        if(TimeUtils.getTimeStampDif(timestamp)>overDueTime){
            ShareUtils.resetShare(cacheName)
                    .set("timestamp",0L)
                    .commit();
        }

    }

    @Override
    public void saveObjInCache(T t) {
        String cacheName = getCacheName(t);
        String json = GsonUtil.getJsonByObject(t);
        ShareUtils.resetShare(cacheName)
                .set("timestamp",TimeUtils.getNowTimeStamp())
                .set("objContent",json)
                .commit();
    }

    @Override
    public void clearCache(T t) {
        String cacheName = getCacheName(t);
        ShareUtils.resetShare(cacheName)
                .set("timestamp",0L)
                .commit();
    }

}
